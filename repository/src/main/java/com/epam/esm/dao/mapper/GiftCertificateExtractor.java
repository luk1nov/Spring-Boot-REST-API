package com.epam.esm.dao.mapper;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class GiftCertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    private final GiftCertificateRowMapper mapper;

    @Autowired
    public GiftCertificateExtractor(GiftCertificateRowMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> certificates = new LinkedHashMap<>();
        while (rs.next()){
            Long key = rs.getLong("gift_certificate.id");
            certificates.putIfAbsent(key, mapper.mapRow(rs, rs.getRow()));
            Tag tag = new Tag(rs.getLong("tag.id"), rs.getString("tag.name"));
            if (Objects.nonNull(tag.getName())){
                certificates.get(key).addTag(tag);
            }
        }
        return new ArrayList<>(certificates.values());
    }
}
