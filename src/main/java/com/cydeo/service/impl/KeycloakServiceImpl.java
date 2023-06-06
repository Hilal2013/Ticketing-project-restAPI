package com.cydeo.service.impl;

import com.cydeo.config.KeycloakProperties;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.KeycloakService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Arrays.asList;
import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

@Service
public class KeycloakServiceImpl implements KeycloakService {


    private final KeycloakProperties keycloakProperties;
//why we are injecting because we want to use variables
    public KeycloakServiceImpl(KeycloakProperties keycloakProperties) {

        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public Response userCreate(UserDTO userDTO) {


        CredentialRepresentation credential = new CredentialRepresentation();
        //I created one instance from that class
        credential.setType(CredentialRepresentation.PASSWORD);
//whatever we are doing in keycloak
        credential.setTemporary(false);
//if temporary is true you need to reset your password
        credential.setValue(userDTO.getPassWord());
//inthe postman in the body create userDTO//userDTO is coming from postman
        //im capturing the password and assigning the user password in the keycloak
        UserRepresentation keycloakUser = new UserRepresentation();
        //We created another instance from that class
        keycloakUser.setUsername(userDTO.getUserName());
        //whatever we see keycloak
        keycloakUser.setFirstName(userDTO.getFirstName());
        keycloakUser.setLastName(userDTO.getLastName());
        keycloakUser.setEmail(userDTO.getUserName());
        keycloakUser.setCredentials(asList(credential));
        keycloakUser.setEmailVerified(true);
        keycloakUser.setEnabled(true);

        //if you wanna do any action from the springboot first we need to create instance
        Keycloak keycloak = getKeycloakInstance();//method bottom

        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        //there is one instance from one class called keycloak properties inside there is variable
        // and im trying to access it via getter=>//cydeo-dev
        UsersResource usersResource = realmResource.users();

        // Create Keycloak user
        Response result = usersResource.create(keycloakUser);
//basically admin dependency has o ne claas called UserResource insde the user resource there is a one method that create
        //is gonna create user
        String userId = getCreatedId(result);
        //whenever keycloak create user ->create unique id
        ClientRepresentation appClient = realmResource.clients()
                .findByClientId(keycloakProperties.getClientId()).get(0);

        RoleRepresentation userClientRole = realmResource.clients().get(appClient.getId())
                .roles().get(userDTO.getRole().getDescription()).toRepresentation();
//i created user// user has role it checks all of them//whenever matches it assigns to the user
        // if doesnt //throw error//first you need to create role in the keycloak
        realmResource.users().get(userId).roles().clientLevel(appClient.getId())
                .add(List.of(userClientRole));


        keycloak.close();//close the instance
        return result;
    }

    @Override
    public void delete(String userName) {

        Keycloak keycloak = getKeycloakInstance();

        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());//master
        UsersResource usersResource = realmResource.users();

        List<UserRepresentation> userRepresentations = usersResource.search(userName);
        String uid = userRepresentations.get(0).getId();
        usersResource.delete(uid);

        keycloak.close();
    }

    private Keycloak getKeycloakInstance(){
        //im giving those information to keycloak
        return Keycloak.getInstance(keycloakProperties.getAuthServerUrl(),
                keycloakProperties.getMasterRealm(), keycloakProperties.getMasterUser()
                , keycloakProperties.getMasterUserPswd(), keycloakProperties.getMasterClient());
    }
}