package com.myblogbackend.blog.dtos;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeviceInfo {

    @NotBlank(message = "Device id cannot be blank")
    private String deviceId;

    @NotNull(message = "Device type cannot be null")
    private String deviceType;
}