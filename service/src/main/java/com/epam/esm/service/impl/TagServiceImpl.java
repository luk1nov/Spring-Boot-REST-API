package com.epam.esm.service.impl;

import com.epam.esm.converters.DtoToTagConverter;
import com.epam.esm.converters.TagToDtoConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;
    private DtoToTagConverter dtoToTagConverter;
    private TagToDtoConverter tagToDtoConverter;

    @Autowired
    public TagServiceImpl(TagDao tagDao, DtoToTagConverter dtoToTagConverter, TagToDtoConverter tagToDtoConverter) {
        this.tagDao = tagDao;
        this.dtoToTagConverter = dtoToTagConverter;
        this.tagToDtoConverter = tagToDtoConverter;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        return tagToDtoConverter.convert(tagDao.insert(dtoToTagConverter.convert(tagDto)));
    }

    @Override
    public List<TagDto> findAll() {
        return null;
    }
}
