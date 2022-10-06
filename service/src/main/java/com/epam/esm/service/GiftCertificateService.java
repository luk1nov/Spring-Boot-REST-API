package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Set;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto deleteById(Long id);

    GiftCertificateDto addTags(Long id, Set<TagDto> tags);

    GiftCertificateDto removeAllTags(Long id);

    List<GiftCertificateDto> findCertificatesByTag(Long id);

    GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto);

    boolean isCertificateExistsWithTag(Tag tag);
}
