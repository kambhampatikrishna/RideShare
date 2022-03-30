package com.example.demo.Repository;

import com.example.demo.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    public UserModel findByUserId(Long userId);
}
