package com.epam.esm.service.impl;

import com.epam.esm.converters.DtoToTagConverter;
import com.epam.esm.converters.TagToDtoConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.*;
import com.epam.esm.service.TagService;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final DtoToTagConverter dtoToTagConverter;
    private final TagToDtoConverter tagToDtoConverter;
    private final GiftCertificateValidator validator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, DtoToTagConverter dtoToTagConverter, TagToDtoConverter tagToDtoConverter, GiftCertificateValidator validator) {
        this.tagDao = tagDao;
        this.dtoToTagConverter = dtoToTagConverter;
        this.tagToDtoConverter = tagToDtoConverter;
        this.validator = validator;
    }


    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        if(Objects.isNull(tagDto)){
            throw new EntityCreationException();
        }
        if(validator.isValidTagName(tagDto.getName())){
            if (tagDao.findByName(tagDto.getName()).isEmpty()){
                return tagToDtoConverter.convert(tagDao.insert(dtoToTagConverter.convert(tagDto)));
            }
            throw new EntityAlreadyExistsException();
        }
        throw new InvalidDataProvidedException("invalid tag name - " + tagDto.getName());
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream().map(tagToDtoConverter::convert).toList();
    }

    @Override
    @Transactional
    public TagDto findById(Long id) {
        if(Objects.nonNull(id)){
            return tagDao.findById(id).map(tagToDtoConverter::convert).orElseThrow(EntityNotFoundException::new);
        }
        throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public TagDto update(Long id, TagDto tagDto) {
        if(Objects.isNull(id) || Objects.isNull(tagDto)){
            throw new EntityModifyingException();
        }
        TagDto foundTag = findById(id);
        if(validator.isValidTagName(tagDto.getName())){
            if (tagDao.update(id, dtoToTagConverter.convert(tagDto)) > 0){
                foundTag.setName(tagDto.getName());
                return foundTag;
            }
            throw new EntityModifyingException();
        }
        throw new InvalidDataProvidedException("invalid tag name - " + tagDto.getName());
    }

    @Override
    @Transactional
    public TagDto delete(Long id) {
        if(Objects.nonNull(id)){
            TagDto tag = findById(id);
            if(tagDao.deleteById(id) > 0){
                return tag;
            }
        }
        throw new EntityModifyingException();
    }
}
