package com.epam.esm.hateoas;

import com.epam.esm.controllers.UsersController;
import com.epam.esm.dto.UserDto;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserProcessor implements RepresentationModelProcessor<UserDto>, HateoasBaseBuilder{

    @Override
    public UserDto process(UserDto userDto) {
        setCommonLinks(UsersController.class, userDto, userDto.getId(), true, SELF);
        return userDto;
    }
}
