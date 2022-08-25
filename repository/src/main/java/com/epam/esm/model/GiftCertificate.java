package com.epam.esm.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(name = "gift_certificate_has_tag"
            , joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tagList = new LinkedHashSet<>();

    @PrePersist
    public void prePersist(){
        createDate = LocalDateTime.now();
        lastUpdateDate = createDate;
    }

    @PreUpdate
    public void preUpdate(){
        lastUpdateDate = LocalDateTime.now();
    }

    public boolean addTag(Tag tag){
        return tagList.add(tag);
    }
}
