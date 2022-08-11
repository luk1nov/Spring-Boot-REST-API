package com.epam.esm.dao.constants;

public class SqlQuery {
    public static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    public static final String FIND_ALL_TAGS = "SELECT id, name FROM tag ORDER BY id";
    public static final String FIND_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    public static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?";
    public static final String UPDATE_TAG_BY_ID = "UPDATE tag SET name = ? WHERE id = ?";
    public static final String IS_USED_TAG = "SELECT COUNT(*) FROM tag_has_gift_certificate WHERE tag_id = ?";
}
