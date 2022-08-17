package com.epam.esm.validators.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final String NAME_PATTERN = "^[a-zA-ZА-яЁё\\s]{2,45}$";
    private static final String DESCRIPTION_PATTERN = "^[a-zA-ZА-яЁё\\s]{2,225}$";
    private static final BigDecimal MIN_PRICE = BigDecimal.valueOf(1);
    private static final BigDecimal MAX_PRICE = BigDecimal.valueOf(1000);
    private static final int MIN_DURATION = 1;
    private static final int MAX_DURATION = 365;

    @Override
    public boolean isValidTagName(String name) {
        return Objects.nonNull(name) && name.matches(NAME_PATTERN);
    }

    @Override
    public boolean isValidTags(Set<TagDto> tags) {
        return !Objects.requireNonNull(tags).isEmpty() && tags.stream()
                .allMatch(i -> isValidTagName(i.getName()));
    }

    @Override
    public boolean isValidCertificateName(String name) {
        return Objects.nonNull(name) && name.matches(NAME_PATTERN);
    }

    @Override
    public boolean isValidCertificateDescription(String description) {
        return Objects.nonNull(description) && description.matches(DESCRIPTION_PATTERN);
    }

    @Override
    public boolean isValidCertificateDuration(int duration) {
        return duration >= MIN_DURATION && duration <= MAX_DURATION;
    }

    @Override
    public boolean isValidCertificatePrice(BigDecimal price) {
        return price.compareTo(MIN_PRICE) >= 0 && price.compareTo(MAX_PRICE) <= 0;
    }
}
