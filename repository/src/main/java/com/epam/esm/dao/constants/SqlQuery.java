package com.epam.esm.dao.constants;

public class SqlQuery {
    public static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    public static final String FIND_ALL_TAGS = "SELECT id, name FROM tag ORDER BY id";
    public static final String FIND_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
}
