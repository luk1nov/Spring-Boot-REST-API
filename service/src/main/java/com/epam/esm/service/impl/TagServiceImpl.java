package com.epam.esm.service.impl;

import com.epam.esm.converters.DtoToTagConverter;
import com.epam.esm.converters.TagToDtoConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.*;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {
    private final DtoToTagConverter dtoToTagConverter;
    private final TagToDtoConverter tagToDtoConverter;
    private final GiftCertificateValidator validator;
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(DtoToTagConverter dtoToTagConverter, TagToDtoConverter tagToDtoConverter, GiftCertificateValidator validator, TagRepository tagRepository) {
        this.dtoToTagConverter = dtoToTagConverter;
        this.tagToDtoConverter = tagToDtoConverter;
        this.validator = validator;
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        if(Objects.isNull(tagDto)){
            throw new EntityCreationException();
        }
        if(validator.isValidTagName(tagDto.getName())){
            if (tagRepository.findByName(tagDto.getName()).isEmpty()){
                return tagToDtoConverter.convert(tagRepository.save(dtoToTagConverter.convert(tagDto)));
            }
            throw new EntityAlreadyExistsException();
        }
        throw new InvalidDataProvidedException("invalid tag name - " + tagDto.getName());
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream().map(tagToDtoConverter::convert).toList();
    }

    @Override
    public TagDto findById(Long id) {
        if(Objects.nonNull(id)){
            return tagRepository.findById(id).map(tagToDtoConverter::convert).orElseThrow(EntityNotFoundException::new);
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
            tagRepository.save(dtoToTagConverter.convert(foundTag));
            return foundTag;
        }
        throw new InvalidDataProvidedException("invalid tag name - " + tagDto.getName());
    }

    @Override
    public TagDto delete(Long id) {
        if(Objects.nonNull(id)){
            TagDto tag = findById(id);
            tagRepository.deleteById(id);
            return tag;
        }
        throw new EntityModifyingException();
    }
}
