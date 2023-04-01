package com.myblogbackend.blog.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseEntityBuilder {
    private final Map<String, Object> map = new HashMap<>();

    private ResponseEntityBuilder() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        map.put("timestamp", timestamp);
        this.setCode(200);
    }

    public static ResponseEntityBuilder getBuilder() {
        return new ResponseEntityBuilder();
    }

    public void setCode(final int code) {
        map.put("code", code);
        setSuccess(code < 400);
    }


    public ResponseEntityBuilder setCode(final HttpStatus code) {
        this.setCode(code.value());
        return this;
    }

    public ResponseEntityBuilder setMessage(final String message) {
        map.put("message", message);
        return this;
    }

    public ResponseEntityBuilder set(final String key, final Object value) {
        map.put(key, value);
        return this;
    }

    public void setSuccess(final boolean isSuccess) {
        map.put("success", isSuccess);
    }

    public ResponseEntityBuilder setDetails(final Object details) {
        map.put("details", details);
        return this;
    }

    public ResponseEntity<?> build() {
        if (map.get("message") == null && map.get("code") != null)
            map.put("message", HttpStatus.valueOf((Integer) map.get("code")).getReasonPhrase());
        int code = (Integer) map.get("code");
        return ResponseEntity.status(HttpStatus.valueOf(code))
                .body((map));
    }
}