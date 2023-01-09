package org.tm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    @JsonProperty("status_code")
    private Integer code;

    @JsonProperty("status_msg")
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
