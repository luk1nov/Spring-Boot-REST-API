package com.epam.esm.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrderDto {
    private long userId;
    private List<Integer> certificateIds;
}
