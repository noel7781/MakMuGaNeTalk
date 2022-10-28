package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long id);
}
