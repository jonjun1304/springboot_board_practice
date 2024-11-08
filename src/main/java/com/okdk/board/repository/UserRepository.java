package com.okdk.board.repository;

import com.okdk.board.entity.Board;
import com.okdk.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // 사용자 ID로 사용자 조회
    Optional<User> findByUserId(String userId);
}
