package com.epam.esm.dao;

import com.epam.esm.model.Tag;

import java.util.Optional;

public interface TagDao extends BaseDao<Tag>{
    Optional<Tag> findByName(String name);

    int isUsed(Long id);

    Tag findOrCreate(Tag tag);
}
