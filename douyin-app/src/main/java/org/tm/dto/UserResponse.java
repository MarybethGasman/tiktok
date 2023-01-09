package org.tm.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class UserResponse {
    @JsonUnwrapped
    Response response;

    UserDTO user;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
