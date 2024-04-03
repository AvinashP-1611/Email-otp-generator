package com.example.Sunbase_project.repositories;

import com.example.Sunbase_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user u WHERE u.first_name LIKE %:firstname%", nativeQuery = true)
    List<User> findByName(String firstname);

    @Query(value = "SELECT * FROM user u WHERE u.city LIKE %:city%", nativeQuery = true)
    List<User> findByCity(String city);

    @Query(value = "SELECT * FROM user u WHERE u.email LIKE %:email%", nativeQuery = true)
    List<User> findByEmail(String email);

    @Query(value = "SELECT * FROM user u WHERE u.phone LIKE %:phone%", nativeQuery = true)
    List<User> findByPhone(String phone);
}
