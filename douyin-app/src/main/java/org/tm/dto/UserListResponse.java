package org.tm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class UserListResponse {

    @JsonUnwrapped
    private Response response;

    @JsonProperty("user_list")
    private List<UserDTO> userDTOList;

    public UserListResponse(Response response, List<UserDTO> userDTOList) {
        this.response = response;
        this.userDTOList = userDTOList;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<UserDTO> getUserDTOList() {
        return userDTOList;
    }

    public void setUserDTOList(List<UserDTO> userDTOList) {
        this.userDTOList = userDTOList;
    }
}
