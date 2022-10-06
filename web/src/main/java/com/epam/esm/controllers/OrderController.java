package com.epam.esm.controllers;

import com.epam.esm.dto.RequestOrderDto;
import com.epam.esm.dto.ResponseOrderDto;
import com.epam.esm.hateoas.ResponseOrderProcessor;
import com.epam.esm.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@ApiOperation("Orders API")
public class OrderController {

    private final OrderService orderService;
    private final ResponseOrderProcessor responseOrderProcessor;

    @Autowired
    public OrderController(OrderService orderService, ResponseOrderProcessor responseOrderProcessor) {
        this.orderService = orderService;
        this.responseOrderProcessor = responseOrderProcessor;
    }

    @PostMapping
    public ResponseOrderDto create(@RequestBody RequestOrderDto requestOrderDto){
        return orderService.create(requestOrderDto);
    }

    @GetMapping
    public List<ResponseOrderDto> findAll(){
        List<ResponseOrderDto> orders = orderService.findAll();
        orders.forEach(responseOrderProcessor::process);
        return orders;
    }

    @GetMapping("/{id}")
    public ResponseOrderDto findById(@PathVariable Long id){
        return orderService.findById(id);
    }

}
