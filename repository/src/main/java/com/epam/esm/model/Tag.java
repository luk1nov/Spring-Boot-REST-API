package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tagList")
    private Set<GiftCertificate> giftCertificates;

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
