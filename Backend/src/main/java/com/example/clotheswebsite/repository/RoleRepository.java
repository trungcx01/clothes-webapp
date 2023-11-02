package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.Role;
import com.example.clotheswebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
