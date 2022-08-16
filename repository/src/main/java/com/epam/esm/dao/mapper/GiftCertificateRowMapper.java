package com.epam.esm.dao.mapper;

import com.epam.esm.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .duration(rs.getInt("duration"))
                .createDate(LocalDateTime.parse(rs.getString("create_date"), FORMATTER))
                .lastUpdateDate(LocalDateTime.parse(rs.getString("last_update_date"), FORMATTER))
                .build();
    }
}
