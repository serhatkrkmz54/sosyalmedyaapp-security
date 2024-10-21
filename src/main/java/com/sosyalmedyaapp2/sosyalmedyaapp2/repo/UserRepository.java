package com.sosyalmedyaapp2.sosyalmedyaapp2.repo;

import com.sosyalmedyaapp2.sosyalmedyaapp2.model.Post;
import com.sosyalmedyaapp2.sosyalmedyaapp2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.posts FROM  User u WHERE u.email = :email")
    List<Post> tumPostlariGorEmail(@Param("email") String email);

    @Query("SELECT u.posts FROM User u WHERE u.id = :userId")
    List<Post> tumPostlariGorID(@Param("userId") Long userId);
}
