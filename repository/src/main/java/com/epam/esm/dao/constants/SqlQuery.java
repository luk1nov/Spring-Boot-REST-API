package com.epam.esm.dao.constants;

public class SqlQuery {
    public static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    public static final String FIND_ALL_TAGS = "SELECT id, name FROM tag ORDER BY id";
    public static final String FIND_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    public static final String FIND_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";
    public static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?";
    public static final String UPDATE_TAG_BY_ID = "UPDATE tag SET name = ? WHERE id = ?";
    public static final String IS_USED_TAG = "SELECT COUNT(*) FROM tag_has_gift_certificate WHERE tag_id = ?";

    public static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SET_TAG_FOR_CERTIFICATE = "INSERT INTO gift_certificate_has_tag VALUES (?, ?)";
    public static final String FIND_ALL_CERTIFICATES = """
            SELECT *
            FROM gift_certificate
            LEFT JOIN gift_certificate_has_tag ON gift_certificate_has_tag.gift_certificate_id = gift_certificate.id
            LEFT JOIN tag ON tag.id = gift_certificate_has_tag.tag_id
            """;
    public static final String FIND_CERTIFICATE_BY_ID = FIND_ALL_CERTIFICATES + " WHERE gift_certificate.id = ?";

}
