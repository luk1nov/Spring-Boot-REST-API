package com.epam.esm.controllers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagService tagService;

    @Autowired
    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id){
        return tagService.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto insert(@RequestBody TagDto tagDto){
        return tagService.create(tagDto);
    }

    @GetMapping
    public List<TagDto> findAll(){
        return tagService.findAll();
    }

}
