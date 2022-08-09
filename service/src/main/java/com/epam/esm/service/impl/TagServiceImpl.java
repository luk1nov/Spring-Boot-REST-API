package com.epam.esm.service.impl;

import com.epam.esm.converters.DtoToTagConverter;
import com.epam.esm.converters.TagToDtoConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public TagDto create(TagDto tagDto) {
        return tagToDtoConverter.convert(tagDao.insert(dtoToTagConverter.convert(tagDto)));
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream().map(tagToDtoConverter::convert).toList();
    }

    @Override
    public TagDto findById(Long id) {
        return tagDao.findById(id).map(tagToDtoConverter::convert).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public TagDto update() {
        return null;
    }
}
