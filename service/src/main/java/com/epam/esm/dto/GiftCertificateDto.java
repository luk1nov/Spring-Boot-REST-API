package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"id", "name", "description", "price", "duration", "createDate", "lastUpdateDate", "tags"})
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    @JsonIgnore
    private LocalDateTime createDate;
    @JsonIgnore
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;
}
