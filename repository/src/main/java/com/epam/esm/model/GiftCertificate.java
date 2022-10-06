package com.epam.esm.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "duration", nullable = false)
    private int duration;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "gift_certificate_has_tag"
            , joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tagList = new HashSet<>();

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
