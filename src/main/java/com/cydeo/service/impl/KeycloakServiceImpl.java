package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.KeycloakService;

import javax.ws.rs.core.Response;

public class KeycloakServiceImpl implements KeycloakService {




    @Override
    public Response userCreate(UserDTO dto) {
        return null;
    }

    @Override
    public void delete(String username) {

    }
}
