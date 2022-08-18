package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.dao.constants.SqlQuery.*;

@Component
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Tag insert(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        tag.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(FIND_TAG_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream()
                .findFirst();
    }

    @Override
    public int update(Long id, Tag tag) {
        return jdbcTemplate.update(UPDATE_TAG_BY_ID, tag.getName(), id);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_TAG_BY_ID, id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(FIND_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream()
                .findFirst();
    }

    @Override
    public int isUsed(Long id) {
        return jdbcTemplate.queryForObject(IS_USED_TAG, Integer.class, id);
    }

    @Override
    public Tag findOrCreate(Tag tag) {
        return jdbcTemplate.query(FIND_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), tag.getName())
                .stream()
                .findFirst()
                .orElseGet(() -> insert(tag));
    }
}
