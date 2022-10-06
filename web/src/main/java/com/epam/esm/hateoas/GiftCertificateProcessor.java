package com.epam.esm.hateoas;

import com.epam.esm.controllers.GiftCertificatesController;
import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateProcessor implements RepresentationModelProcessor<GiftCertificateDto>, HateoasBaseBuilder {

    private final TagProcessor tagProcessor;

    @Autowired
    public GiftCertificateProcessor(TagProcessor tagProcessor) {
        this.tagProcessor = tagProcessor;
    }

    @Override
    public GiftCertificateDto process(GiftCertificateDto giftCertificateDto) {
        setCommonLinks(GiftCertificatesController.class, giftCertificateDto, giftCertificateDto.getId(), true, SELF, DELETE, UPDATE);
        giftCertificateDto.getTags().forEach(tagProcessor::process);
        return giftCertificateDto;
    }
}
