package com.epam.esm.validators;

import com.epam.esm.dto.TagDto;

import java.math.BigDecimal;
import java.util.Set;

public interface GiftCertificateValidator {
    boolean isValidTagName(String name);

    boolean isValidTags(Set<TagDto> tags);

    boolean isValidCertificateName(String name);

    boolean isValidCertificateDescription(String description);

    boolean isValidCertificateDuration(int duration);

    boolean isValidCertificatePrice(BigDecimal price);

}
