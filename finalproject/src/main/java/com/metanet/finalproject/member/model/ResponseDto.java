package com.metanet.finalproject.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDto<T> {
    private int code;
    private String msg;
    private T data;
}
