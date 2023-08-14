package com.capstone.parismarketbackend.repository;

import com.capstone.parismarketbackend.model.dbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<dbUser, Long> {
    Optional<dbUser> findByLoginId(String loginId);
}
