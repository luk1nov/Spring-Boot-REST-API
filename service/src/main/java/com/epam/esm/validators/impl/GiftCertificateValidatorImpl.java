package com.epam.esm.validators.impl;

import com.epam.esm.validators.GiftCertificateValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final String NAME_PATTERN = "^[a-zA-Z\\s]{2,45}$";

    @Override
    public boolean isValidTagName(String name) {
        return Objects.nonNull(name) && name.matches(NAME_PATTERN);
    }
}
