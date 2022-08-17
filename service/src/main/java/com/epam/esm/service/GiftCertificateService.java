package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Set;

public interface GiftCertificateService {
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto findById(Long id);

    List<GiftCertificateDto> findAll();

    GiftCertificateDto deleteById(Long id);

    Set<TagDto> addTags(Long id, Set<TagDto> tags);

    GiftCertificateDto removeALlTags(Long id);

    List<GiftCertificateDto> findCertificatesByTag(Long id);

    GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto);
}
