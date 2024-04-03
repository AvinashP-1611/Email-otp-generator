package com.example.Sunbase_project.services;

import com.example.Sunbase_project.dto.request.LoginRequest;
import com.example.Sunbase_project.dto.request.OtpRequest;
import com.example.Sunbase_project.dto.response.BaseResponseDto;
import com.example.Sunbase_project.entity.User;
import com.example.Sunbase_project.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public BaseResponseDto login(LoginRequest request) {
        BaseResponseDto responseDto = new BaseResponseDto();
        User user = (User) userRepository.findByPhone(request.getPhone());
        if(user == null){
            responseDto.setMessage("No user found");
            responseDto.setStatusCode("404");
        }
        else{
            String otp = generateOTP();
            user.setOtp(passwordEncoder.encode(otp));
            user.setOtpExpiresAt(LocalDateTime.now().plusMinutes(5));
            userRepository.save(user);
            sendEmail(user.getEmail(), "OTP For Login", otp);
            responseDto.setMessage("OTP SEND SUCCESSFULLY TO MAIL");
            responseDto.setData(user.getEmail()); // this will help for verify-otp
        }
        return responseDto;
    }
    public BaseResponseDto verifyOtp(OtpRequest otp) {
        BaseResponseDto responseDto = new BaseResponseDto();
        User user = (User) userRepository.findByEmail(otp.getEmail());
        if(passwordEncoder.matches(otp.getOtp(), user.getOtp())) {
            if(LocalDateTime.now().isBefore(user.getOtpExpiresAt())){
                responseDto.setMessage("Welcome to dashboard");
            }
            responseDto.setMessage("Otp expires");
            responseDto.setStatusCode("404");
        }
        else{
            responseDto.setMessage("OTP doesn't match");
        }
        return responseDto;
    }
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    public void sendEmail(String to, String subject, String otp) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("OTP : "+otp+"\n,   Note: This OTP expires within 5 minutes from the time of generation", true); // Use true to indicate HTML content
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
