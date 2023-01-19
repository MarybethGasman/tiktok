package org.tm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.HashMap;

public class Response extends HashMap {

    private Response(Integer code, String msg) {
        this.put("status_code", code);
        this.put("status_msg", msg);
    }

    public static Response success() {
        return new Response(0, "操作成功");
    }

    public static Response fail() {
        return new Response(-1, "操作失败");
    }

    public static Response fail(Integer code, String msg) {
        return new Response(code, msg);
    }

}
