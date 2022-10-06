package com.epam.esm.service;

import com.epam.esm.dto.RequestOrderDto;
import com.epam.esm.dto.ResponseOrderDto;

import java.util.List;

public interface OrderService extends BaseService<ResponseOrderDto> {
    ResponseOrderDto create(RequestOrderDto requestOrderDto);
}
