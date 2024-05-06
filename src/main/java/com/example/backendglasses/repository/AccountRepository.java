package com.example.backendglasses.repository;

import com.example.backendglasses.model.Role;
import com.example.backendglasses.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNameAccount(String accountName);
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByNameAccount(String nameAccount);
    User findUserByRole(Role role);

    @Query(value = "select * from user where user.role_id = 2", nativeQuery = true)
    List<User> findAllAccountUser();
}
