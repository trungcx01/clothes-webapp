package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByUsernameContainingOrFullnameContainingOrEmailContaining(String username, String fullname, String email);

    //    Boolean existByUsername(String username);
//    Boolean existByEmail(String email);
    Optional<UserEntity> findByUsername(String username);

}
