package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {

    TagDto create(TagDto tagDto);

    List<TagDto> findAll();

    TagDto findById(Long id);

    TagDto update(Long id, TagDto tagDto);

    TagDto delete(Long id);
}
