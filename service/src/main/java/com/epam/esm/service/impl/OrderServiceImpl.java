package com.epam.esm.service.impl;

import com.epam.esm.converters.OrderConverter;
import com.epam.esm.converters.impl.GiftCertificateConverterImpl;
import com.epam.esm.dto.RequestOrderDto;
import com.epam.esm.dto.ResponseOrderDto;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.mapper.UserMapperImpl;
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

    private final UserMapper userMapper = new UserMapperImpl();
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateConverterImpl giftCertificateConverter;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(UserService userService, GiftCertificateService giftCertificateService, GiftCertificateConverterImpl giftCertificateConverter, OrderRepository orderRepository, OrderConverter orderConverter) {
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateConverter = giftCertificateConverter;
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public ResponseOrderDto create(RequestOrderDto requestOrderDto) {
        User user = userMapper.dtoToUser(userService.findById(requestOrderDto.getUserId()));
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        for (long id : requestOrderDto.getCertificateIds()){
            giftCertificateList.add(giftCertificateConverter.dtoToEntity(giftCertificateService.findById(id)));
        }
        Order order = Order.builder()
                .user(user)
                .giftCertificateList(giftCertificateList)
                .build();
        return orderConverter.orderToResponseOderDto(orderRepository.save(order));
    }

    @Override
    public List<ResponseOrderDto> findAll() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(orderConverter::orderToResponseOderDto)
                .toList();
    }
}
