package com.epam.esm.hateoas;

import com.epam.esm.controllers.OrderController;
import com.epam.esm.controllers.UsersController;
import com.epam.esm.dto.ResponseOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ResponseOrderProcessor implements RepresentationModelProcessor<ResponseOrderDto>, HateoasBaseBuilder {

    private final UserProcessor userProcessor;
    private final GiftCertificateProcessor giftCertificateProcessor;

    @Autowired
    public ResponseOrderProcessor(UserProcessor userProcessor, GiftCertificateProcessor giftCertificateProcessor) {
        this.userProcessor = userProcessor;
        this.giftCertificateProcessor = giftCertificateProcessor;
    }

    @Override
    public ResponseOrderDto process(ResponseOrderDto order) {
        order.getGiftCertificateDtoList().forEach(giftCertificateProcessor::process);
        setCommonLinks(OrderController.class, order, order.getId(), true, SELF);
        userProcessor.process(order.getUserDto());
        return order;
    }
}
