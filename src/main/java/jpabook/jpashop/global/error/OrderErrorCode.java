package jpabook.jpashop.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    CANNOT_CANCEL_ORDER(HttpStatus.BAD_REQUEST, "주문을 취소할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
