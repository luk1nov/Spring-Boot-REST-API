package com.epam.esm.service.impl;

import com.epam.esm.converters.CertificateToDtoConverter;
import com.epam.esm.converters.DtoToCertificateConverter;
import com.epam.esm.converters.DtoToTagConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.*;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final DtoToCertificateConverter dtoToCertificateConverter;
    private final CertificateToDtoConverter certificateToDtoConverter;
    private final DtoToTagConverter dtoToTagConverter;
    private final GiftCertificateValidator validator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, DtoToCertificateConverter dtoToCertificateConverter, CertificateToDtoConverter certificateToDtoConverter, DtoToTagConverter dtoToTagConverter, GiftCertificateValidator validator) {
        this.giftCertificateDao = giftCertificateDao;
        this.dtoToCertificateConverter = dtoToCertificateConverter;
        this.certificateToDtoConverter = certificateToDtoConverter;
        this.dtoToTagConverter = dtoToTagConverter;
        this.validator = validator;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        if(Objects.nonNull(giftCertificateDto)){
            validateGiftCertificate(giftCertificateDto);
            giftCertificateDto.setCreateDate(LocalDateTime.now());
            giftCertificateDto.setLastUpdateDate(giftCertificateDto.getCreateDate());
            GiftCertificate created = giftCertificateDao.insert(dtoToCertificateConverter.convert(giftCertificateDto));
            giftCertificateDao.setTagsToCertificate(created.getId(), created.getTags());
            return certificateToDtoConverter.convert(created);
        }
        throw new EntityCreationException();

    }

    @Override
    @Transactional
    public GiftCertificateDto findById(Long id) {
        if(Objects.isNull(id)){
            throw new EntityNotFoundException();
        }
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

    @Override
    @Transactional
    public GiftCertificateDto deleteById(Long id) {
        if(Objects.isNull(id)){
            GiftCertificateDto giftCertificateDto = findById(id);
            if(giftCertificateDao.deleteById(id) > 0){
                return giftCertificateDto;
            }
        }
        throw new EntityModifyingException();
    }

    @Override
    @Transactional
    public Set<TagDto> addTags(Long id, Set<TagDto> tags) {
        if(Objects.isNull(id) || Objects.isNull(tags)){
            throw new EntityModifyingException();
        }
        GiftCertificateDto giftCertificateDto = findById(id);
        if(validator.isValidTags(tags)){
            tags.removeAll(giftCertificateDto.getTags());
            Set<Tag> tagSet = tags.stream().map(dtoToTagConverter::convert).collect(Collectors.toSet());
            giftCertificateDao.setTagsToCertificate(id, tagSet);

            return tags;
        }
        throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public GiftCertificateDto removeALlTags(Long id) {
        if(Objects.isNull(id)){
            throw new EntityModifyingException();
        }
        GiftCertificateDto giftCertificateDto = findById(id);
        if(!giftCertificateDto.getTags().isEmpty() && !giftCertificateDao.removeAllTags(id)){
            throw new InternalServerException();
        }
        giftCertificateDto.getTags().removeAll(giftCertificateDto.getTags());
        return giftCertificateDto;
    }

    @Override
    public List<GiftCertificateDto> findCertificatesByTag(Long id) {
        return giftCertificateDao.findCertificatesByTagId(id).stream().map(certificateToDtoConverter::convert).toList();
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        if(Objects.nonNull(id) || Objects.nonNull(giftCertificateDto)){
            validateGiftCertificate(giftCertificateDto);
            findById(id);
            giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
            if(giftCertificateDao.update(id, dtoToCertificateConverter.convert(giftCertificateDto)) > 0){
                return findById(id);
            }
        }
        throw new EntityModifyingException();
    }

    private void validateGiftCertificate(GiftCertificateDto giftCertificateDto){
        String message = null;
        if(!validator.isValidCertificateName(giftCertificateDto.getName())){
            message = "invalid name - " + giftCertificateDto.getName();
        } else if (!validator.isValidCertificateDescription(giftCertificateDto.getDescription())){
            message = "invalid description - " + giftCertificateDto.getDescription();
        } else if (!validator.isValidCertificateDuration(giftCertificateDto.getDuration())){
            message = "invalid duration - " + giftCertificateDto.getDescription();
        } else if (!validator.isValidCertificatePrice(giftCertificateDto.getPrice())) {
            message = "invalid price - " + giftCertificateDto.getDescription();
        }

        if(Objects.nonNull(message)){
            throw new InvalidDataProvidedException(message);
        }
    }
}
