package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseOrderDto {
    private long id;
    private BigDecimal cost;
    private LocalDateTime orderDate;
    @JsonProperty("user")
    private UserDto userDto;
    @JsonProperty("gift-certificates")
    private List<GiftCertificateDto> giftCertificateDtoList;
}
