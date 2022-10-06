package com.epam.esm.controllers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.TagProcessor;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsController {
    private final TagService tagService;
    private final TagProcessor tagProcessor;
    private final TagMapper tagMapper;

    @Autowired
    public TagsController(TagService tagService, TagProcessor tagProcessor, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagProcessor = tagProcessor;
        this.tagMapper = tagMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        TagDto tag = tagService.findById(id);
        return ResponseEntity.ok(tag);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto insert(@RequestBody TagDto tagDto){
        return tagService.create(tagDto);
    }

    @GetMapping
    public List<TagDto> findAll(){
        List<TagDto> tags = tagService.findAll();
        tags.forEach(tagProcessor::process);
        return tags;
    }

    @GetMapping("/pageable")
    public Page<TagDto> find(@RequestParam Integer page, @RequestParam Integer size){
        return tagService.findAllByPageable(page, size, Sort.by(Sort.Direction.ASC, "id"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TagDto deleteById(@PathVariable Long id){
        return tagService.delete(id);
    }

    @PutMapping("/{id}")
    public TagDto update(@PathVariable Long id, @RequestBody TagDto tagDto){
        return tagService.update(id, tagDto);
    }
}
