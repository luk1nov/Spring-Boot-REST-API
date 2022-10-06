package com.epam.esm.repository;

import com.epam.esm.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query(value = "SELECT count(*) FROM gift_certificate_has_tag WHERE tag_id = ?",
            nativeQuery = true)
    int isTagUsed(Long id);
}
