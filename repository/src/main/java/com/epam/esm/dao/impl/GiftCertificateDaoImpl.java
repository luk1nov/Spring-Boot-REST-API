package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.GiftCertificateExtractor;
import com.epam.esm.dao.mapper.GiftCertificateRowMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.dao.constants.SqlQuery.*;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final Logger logger = LogManager.getLogger();
    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;
    private final GiftCertificateRowMapper rowMapper;
    private final GiftCertificateExtractor extractor;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao, GiftCertificateRowMapper rowMapper, GiftCertificateExtractor extractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
        this.rowMapper = rowMapper;
        this.extractor = extractor;
    }

    @Override
    public GiftCertificate insert(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        logger.info(giftCertificate);
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setBigDecimal(3, giftCertificate.getPrice());
            ps.setInt(4, giftCertificate.getDuration());
            ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
            ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        giftCertificate.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_CERTIFICATES, extractor);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        List<GiftCertificate> certificates = jdbcTemplate.query(FIND_CERTIFICATE_BY_ID, extractor, id);
        return !Objects.requireNonNull(certificates).isEmpty() ? Optional.of(certificates.get(0)) : Optional.empty();
    }

    @Override
    public int update(Long id, GiftCertificate giftCertificate) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public Set<Tag> setTagsToCertificate(Long id, Set<Tag> addedTags) {
        addedTags = addedTags.stream().map(tagDao::findOrCreate).collect(Collectors.toSet());
        for (Tag tag : addedTags){
            jdbcTemplate.update(SET_TAG_FOR_CERTIFICATE, tag.getId(), id);
        }
        return addedTags;
    }
}
