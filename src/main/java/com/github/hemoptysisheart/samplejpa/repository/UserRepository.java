package com.github.hemoptysisheart.samplejpa.repository;

import com.github.hemoptysisheart.samplejpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}