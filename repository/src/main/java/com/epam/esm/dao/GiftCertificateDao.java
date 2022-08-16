package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.util.Set;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{
    Set<Tag> setTagsToCertificate(Long id, Set<Tag> addedTags);
}
