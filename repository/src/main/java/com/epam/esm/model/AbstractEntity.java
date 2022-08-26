package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract sealed class AbstractEntity permits Tag, GiftCertificate {
    private long id;
}
