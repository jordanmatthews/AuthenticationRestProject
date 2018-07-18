package com.server.AuthenticationRestProject.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.Set;

@JsonDeserialize(as = User.class)
public interface IUser {
    Integer getId();

    //default Set<Role> getRoles() {
        //return Collections.emptySet();
    //}

}
