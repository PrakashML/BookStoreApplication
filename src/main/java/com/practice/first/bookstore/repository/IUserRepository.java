package com.practice.first.bookstore.repository;

import com.practice.first.bookstore.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserRegistration, Long> {
    @Query("select a from UserRegistration a where a.emailId = :email")
    UserRegistration findByEmailId(@Param("email") String emailId);
    @Query("select a from UserRegistration a where a.emailId = :email and a.password = :password")
    UserRegistration findByEmailIdAndPassword(@Param("email") String emailId, @Param("password") String password);
}
