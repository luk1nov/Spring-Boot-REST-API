package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.*;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {
    private final TagMapper tagMapper;
    private final GiftCertificateValidator validator;
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagMapper tagMapper, GiftCertificateValidator validator, TagRepository tagRepository) {
        this.tagMapper = tagMapper;
        this.validator = validator;
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        if(Objects.isNull(tagDto)){
            throw new EntityCreationException();
        }
        if(validator.isValidTagName(tagDto.getName())){
            return tagMapper.entityToDto(
                    tagRepository.findByName(tagDto.getName()).orElseGet(() ->
                            tagRepository.save(tagMapper.dtoToEntity(tagDto))));
        }
        throw new InvalidDataProvidedException("invalid tag name - " + tagDto.getName());
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(tagMapper::entityToDto).toList();
    }

    @Override
    public TagDto findById(Long id) {
        if(Objects.nonNull(id)){
            return tagRepository.findById(id).map(tagMapper::entityToDto).orElseThrow(EntityNotFoundException::new);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public TagDto update(Long id, TagDto tagDto) {
        if(Objects.isNull(id) || Objects.isNull(tagDto)){
            throw new EntityModifyingException();
        }
        TagDto foundTag = findById(id);
        if(validator.isValidTagName(tagDto.getName())){
            foundTag.setName(tagDto.getName());
            tagRepository.save(tagMapper.dtoToEntity(foundTag));
            return foundTag;
        }
        throw new InvalidDataProvidedException("invalid tag name - " + tagDto.getName());
    }

    @Override
    public TagDto delete(Long id) {
        if(Objects.nonNull(id)){
            TagDto tag = findById(id);
            if (tagRepository.isTagUsed(tag.getId()) == 0){
                tagRepository.deleteById(id);
                return tag;
            }
            throw new EntityModifyingException("Tag is currently in use");
        }
        throw new InvalidDataProvidedException("Id is null");
    }

    public Page<TagDto> findAllByPageable(int page, int size, Sort sort){
        return tagRepository.findAll(PageRequest.of(page, size, sort)).map(tagMapper::entityToDto);
    }
}
