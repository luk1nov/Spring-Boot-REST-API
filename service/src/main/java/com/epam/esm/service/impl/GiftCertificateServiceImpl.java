package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.EntityModifyingException;
import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.exceptions.InvalidDataProvidedException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;
    private final GiftCertificateValidator validator;
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateMapper giftCertificateMapper, TagMapper tagMapper, GiftCertificateValidator validator, GiftCertificateRepository giftCertificateRepository, TagService tagService) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagMapper = tagMapper;
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
            return giftCertificateMapper.entityToDto(
                    giftCertificateRepository.save(giftCertificateMapper.dtoToEntity(giftCertificateDto)));
        }
        throw new InvalidDataProvidedException("gift certificate is null");
    }

    @Override
    @Transactional
    public GiftCertificateDto findById(Long id) {
        if(Objects.nonNull(id)){
            return giftCertificateRepository.findById(id)
                    .map(giftCertificateMapper::entityToDto)
                    .orElseThrow(EntityNotFoundException::new);
        }
        throw new InvalidDataProvidedException("id is null");
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return giftCertificateRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(giftCertificateMapper::entityToDto)
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
    public GiftCertificateDto addTags(Long id, Set<TagDto> tags) {
        if(Objects.isNull(id) || Objects.isNull(tags)){
            throw new EntityModifyingException();
        }
        GiftCertificateDto giftCertificateDto = findById(id);
        if(validator.isValidTags(tags)){
            giftCertificateDto.setTags(tags);
            setTagsToCertificate(giftCertificateDto);
            return giftCertificateMapper.entityToDto(
                    giftCertificateRepository.save(giftCertificateMapper.dtoToEntity(giftCertificateDto))
            );
        }
        throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public GiftCertificateDto removeAllTags(Long id) {
        if(Objects.isNull(id)){
            throw new EntityModifyingException();
        }
        GiftCertificateDto giftCertificateDto = findById(id);
        giftCertificateDto.setTags(new HashSet<>());
        giftCertificateRepository.save(giftCertificateMapper.dtoToEntity(giftCertificateDto));
        return giftCertificateDto;
    }

    @Override
    public List<GiftCertificateDto> findCertificatesByTag(Long id) {
        TagDto tagDto = tagService.findById(id);
        return giftCertificateRepository.findGiftCertificatesByTagListIsContaining(tagMapper.dtoToEntity(tagDto))
                .stream()
                .map(giftCertificateMapper::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        if(Objects.nonNull(id) || Objects.nonNull(giftCertificateDto)){
            GiftCertificateDto existingGift = findById(id);
            validateGiftCertificate(giftCertificateDto);
            giftCertificateDto.setId(id);
            giftCertificateDto.setCreateDate(existingGift.getCreateDate());
            setTagsToCertificate(giftCertificateDto);
            return giftCertificateMapper.entityToDto(
                    giftCertificateRepository.save(giftCertificateMapper.dtoToEntity(giftCertificateDto))
            );
        }
        throw new EntityModifyingException();
    }

    @Override
    public boolean isCertificateExistsWithTag(Tag tag) {
        return Objects.nonNull(tag) && giftCertificateRepository.existsGiftCertificateByTagListContaining(tag);
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
