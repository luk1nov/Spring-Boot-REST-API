package com.epam.esm.service.impl;

import com.epam.esm.dto.RequestOrderDto;
import com.epam.esm.dto.ResponseOrderDto;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final GiftCertificateMapper giftCertificateMapper;
    private final UserMapper userMapper;

    @Autowired
    public OrderServiceImpl(UserService userService, GiftCertificateService giftCertificateService, OrderRepository orderRepository, OrderMapper orderMapper, GiftCertificateMapper giftCertificateMapper, UserMapper userMapper) {
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.giftCertificateMapper = giftCertificateMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseOrderDto create(RequestOrderDto requestOrderDto) {
        User user = userMapper.dtoToEntity(userService.findById(requestOrderDto.getUserId()));
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        for (long id : requestOrderDto.getCertificateIds()){
            giftCertificateList.add(giftCertificateMapper.dtoToEntity(giftCertificateService.findById(id)));
        }
        Order order = Order.builder()
                .user(user)
                .giftCertificateList(giftCertificateList)
                .build();
        return orderMapper.orderToResponseDto(orderRepository.save(order));
    }

    @Override
    public List<ResponseOrderDto> findAll() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(orderMapper::orderToResponseDto)
                .toList();
    }
}
