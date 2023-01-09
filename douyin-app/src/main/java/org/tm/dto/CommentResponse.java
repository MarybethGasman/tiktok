package org.tm.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class CommentResponse {

    @JsonUnwrapped
    private Response response;

    private CommentDTO comment;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public CommentDTO getComment() {
        return comment;
    }

    public void setComment(CommentDTO comment) {
        this.comment = comment;
    }
}
