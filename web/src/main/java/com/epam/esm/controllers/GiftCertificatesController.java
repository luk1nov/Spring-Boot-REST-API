package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/certificates")
public class GiftCertificatesController {

    private final GiftCertificateService service;

    @Autowired
    public GiftCertificatesController(GiftCertificateService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto){
        return service.create(giftCertificateDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<GiftCertificateDto> findAll(){
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public GiftCertificateDto delete(@PathVariable Long id){
        return service.deleteById(id);
    }

    @PutMapping("/add_tags/{id}")
    public Set<TagDto> addTagsToCertificate(@PathVariable Long id, @RequestBody Set<TagDto> tags){
        return service.addTags(id, tags);
    }

    @DeleteMapping("/delete_tags/{id}")
    public GiftCertificateDto removeAllTags(@PathVariable Long id){
        return service.removeALlTags(id);
    }

    @GetMapping("/search_by_tag/{id}")
    public List<GiftCertificateDto> findCertificatesByTag(@PathVariable Long id){
        return service.findCertificatesByTag(id);
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto update(@PathVariable Long id, @RequestBody GiftCertificateDto giftCertificateDto){
        return service.update(id, giftCertificateDto);
    }
}
