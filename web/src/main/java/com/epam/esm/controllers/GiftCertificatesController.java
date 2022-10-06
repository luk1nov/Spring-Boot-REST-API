package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.GiftCertificateProcessor;
import com.epam.esm.service.GiftCertificateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Api(tags="Gift certificate controller")
@RestController
@RequestMapping("/api/certificates")
@Slf4j
public class GiftCertificatesController {

    private final GiftCertificateService service;
    private final GiftCertificateProcessor giftCertificateProcessor;

    @Autowired
    public GiftCertificatesController(GiftCertificateService service, GiftCertificateProcessor giftCertificateProcessor) {
        this.service = service;
        this.giftCertificateProcessor = giftCertificateProcessor;
    }

    @ApiOperation(value = "Create gift certificate", notes = "Create gift certificate. Optionally can have tags. Create date and last update date sets automatically.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto){
        return service.create(giftCertificateDto);
    }

    @ApiOperation(value = "Get info about gift certificate", notes = "Get information about gift certificate by specified ID.")
    @ApiImplicitParam(name = "id", value = "Gift Certificate ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<GiftCertificateDto> findAll(){
        List<GiftCertificateDto> gifts = service.findAll();
        gifts.forEach(giftCertificateProcessor::process);
        return gifts;
    }

    @DeleteMapping("/{id}")
    public GiftCertificateDto delete(@PathVariable Long id){
        return service.deleteById(id);
    }

    @PutMapping("/{id}/add_tags")
    public GiftCertificateDto addTagsToCertificate(@PathVariable Long id, @RequestBody Set<TagDto> tags){
        log.error(tags.toString());
        return service.addTags(id, tags);
    }

    @DeleteMapping("/{id}/delete_tags")
    public GiftCertificateDto removeAllTags(@PathVariable Long id){
        return service.removeAllTags(id);
    }

    @GetMapping("/search_by_tag/{id}")
    public List<GiftCertificateDto> findCertificatesByTag(@PathVariable Long id){
        log.debug("asd");
        List<GiftCertificateDto> gifts = service.findCertificatesByTag(id);
        gifts.forEach(giftCertificateProcessor::process);
        return gifts;
    }

    @PutMapping("/{id}")
    public GiftCertificateDto update(@PathVariable Long id, @RequestBody GiftCertificateDto giftCertificateDto){
        return service.update(id, giftCertificateDto);
    }
}
