package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"id", "cost", "orderDate", "user", "giftCertificates"})
public class ResponseOrderDto extends RepresentationModel<ResponseOrderDto> {
    private Long id;
    private BigDecimal cost;
    @JsonProperty("orderDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime orderDate;
    @JsonProperty("user")
    private UserDto userDto;
    @JsonProperty("giftCertificates")
    private List<GiftCertificateDto> giftCertificateDtoList;
}
