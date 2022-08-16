package com.epam.esm.service.impl;

import com.epam.esm.converters.CertificateToDtoConverter;
import com.epam.esm.converters.DtoToCertificateConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final DtoToCertificateConverter dtoToCertificateConverter;
    private final CertificateToDtoConverter certificateToDtoConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, DtoToCertificateConverter dtoToCertificateConverter, CertificateToDtoConverter certificateToDtoConverter) {
        this.giftCertificateDao = giftCertificateDao;
        this.dtoToCertificateConverter = dtoToCertificateConverter;
        this.certificateToDtoConverter = certificateToDtoConverter;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setCreateDate(LocalDateTime.now());
        giftCertificateDto.setLastUpdateDate(giftCertificateDto.getCreateDate());
        GiftCertificate created = giftCertificateDao.insert(dtoToCertificateConverter.convert(giftCertificateDto));
        giftCertificateDao.setTagsToCertificate(created.getId(), created.getTags());
        return certificateToDtoConverter.convert(created);
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return giftCertificateDao.findById(id)
                .map(certificateToDtoConverter::convert)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return giftCertificateDao.findAll().stream()
                .map(certificateToDtoConverter::convert)
                .toList();
    }
}
