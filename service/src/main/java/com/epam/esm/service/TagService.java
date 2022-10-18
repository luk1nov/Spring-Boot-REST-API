package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface TagService extends BaseService<TagDto>{
    TagDto create(TagDto tagDto);

    TagDto update(Long id, TagDto tagDto);

    TagDto delete(Long id);

    Page<TagDto> findAllByPageable(int page, int size, Sort sort);
}
