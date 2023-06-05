package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {
    //This Responce is with javax //Response is that created user in the keycloak//you can access to that
    Response userCreate(UserDTO dto);
    void delete(String username);

}
