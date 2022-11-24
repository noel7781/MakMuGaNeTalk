package com.mugane.MakMuGaNeTalk.exception;

import com.mugane.MakMuGaNeTalk.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ErrorResponse extends BaseTimeEntity {

    private final int status;
    private final String error;
    private final String code;
    private final String message;

    @Builder
    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
