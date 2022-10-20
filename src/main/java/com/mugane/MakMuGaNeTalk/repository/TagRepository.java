package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByContent(String content);
}
