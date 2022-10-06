package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TagService extends BaseService<TagDto>{

    TagDto create(TagDto tagDto);

    TagDto update(Long id, TagDto tagDto);

    TagDto delete(Long id);

    Page<TagDto> findAllByPageable(int page, int size, Sort sort);
}
