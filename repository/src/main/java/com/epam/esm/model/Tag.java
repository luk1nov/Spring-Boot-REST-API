package com.epam.esm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public non-sealed class Tag extends AbstractEntity {
    private String name;

    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }
}
