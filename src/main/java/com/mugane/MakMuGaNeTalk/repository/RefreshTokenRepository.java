package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.RefreshToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long id);

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findRefreshTokensByUserId(Long id);


    void deleteAllByUserId(Long id);

}
