package com.epam.esm.service.impl;

import com.epam.esm.converters.DtoToTagConverter;
import com.epam.esm.converters.impl.GiftCertificateConverterImpl;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.EntityModifyingException;
import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.exceptions.InternalServerException;
import com.epam.esm.exceptions.InvalidDataProvidedException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private final GiftCertificateConverterImpl giftCertificateConverter;
    private final DtoToTagConverter dtoToTagConverter;
    private final GiftCertificateValidator validator;
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, GiftCertificateConverterImpl giftCertificateConverter, DtoToTagConverter dtoToTagConverter, GiftCertificateValidator validator, GiftCertificateRepository giftCertificateRepository, TagService tagService) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateConverter = giftCertificateConverter;
        this.dtoToTagConverter = dtoToTagConverter;
        this.validator = validator;
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        if(Objects.nonNull(giftCertificateDto)){
            validateGiftCertificate(giftCertificateDto);
            setTagsToCertificate(giftCertificateDto);
            return giftCertificateConverter.entityToDto(
                    giftCertificateRepository.save(giftCertificateConverter.dtoToEntity(giftCertificateDto)));
        }
        throw new InvalidDataProvidedException("gift certificate is null");
    }

    @Override
    @Transactional
    public GiftCertificateDto findById(Long id) {
        if(Objects.nonNull(id)){
            return giftCertificateRepository.findById(id)
                    .map(giftCertificateConverter::entityToDto)
                    .orElseThrow(EntityNotFoundException::new);
        }
        throw new InvalidDataProvidedException("id is null");
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return giftCertificateRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(giftCertificateConverter::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public GiftCertificateDto deleteById(Long id) {
        if(Objects.nonNull(id)){
            GiftCertificateDto giftCertificateDto = findById(id);
            giftCertificateRepository.deleteById(id);
            return giftCertificateDto;
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
        return giftCertificateDao.findCertificatesByTagId(id).stream().map(giftCertificateConverter::entityToDto).toList();
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        if(Objects.nonNull(id) || Objects.nonNull(giftCertificateDto)){
            validateGiftCertificate(giftCertificateDto);
            findById(id);
            giftCertificateDto.setLastUpdateDate(LocalDateTime.now());
            if(giftCertificateDao.update(id, giftCertificateConverter.dtoToEntity(giftCertificateDto)) > 0){
                return findById(id);
            }
        }
        throw new EntityModifyingException();
    }

    private void setTagsToCertificate(GiftCertificateDto certificateDto){
        Set<TagDto> tagSet = certificateDto.getTags();
        tagSet = tagSet.stream().map(tagService::create).collect(Collectors.toSet());
        certificateDto.setTags(tagSet);
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
        } else if (!giftCertificateDto.getTags().isEmpty() && !validator.isValidTags(giftCertificateDto.getTags())) {
            message = "invalid tags";
        }

        if(Objects.nonNull(message)){
            throw new InvalidDataProvidedException(message);
        }
    }
}
