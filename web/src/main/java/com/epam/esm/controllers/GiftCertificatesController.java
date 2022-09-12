package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasBuilder;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificatesController {

    private final GiftCertificateService service;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public GiftCertificatesController(GiftCertificateService service, HateoasBuilder hateoasBuilder) {
        this.service = service;
        this.hateoasBuilder = hateoasBuilder;
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
        List<GiftCertificateDto> certificates = service.findAll();
        certificates.forEach(hateoasBuilder::setLinks);
        return certificates;
    }

    @DeleteMapping("/{id}")
    public GiftCertificateDto delete(@PathVariable Long id){
        return service.deleteById(id);
    }

    @PutMapping("/add_tags/{id}")
    public GiftCertificateDto addTagsToCertificate(@PathVariable Long id, @RequestBody Set<TagDto> tags){
        return service.addTags(id, tags);
    }

    @DeleteMapping("/delete_tags/{id}")
    public GiftCertificateDto removeAllTags(@PathVariable Long id){
        return service.removeAllTags(id);
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
