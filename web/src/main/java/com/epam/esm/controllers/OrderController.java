package com.epam.esm.controllers;

import com.epam.esm.dto.RequestOrderDto;
import com.epam.esm.dto.ResponseOrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseOrderDto create(@RequestBody RequestOrderDto requestOrderDto){
        return orderService.create(requestOrderDto);
    }

    @GetMapping
    public List<ResponseOrderDto> findAll(){
        return orderService.findAll();
    }

}
