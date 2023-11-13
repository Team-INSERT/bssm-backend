package com.insert.ogbsm.common;

import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;


@Component
public class CustomExceptionHandler {

    public void 함수를_실행할_시_다음의_에러코드를_반환한다(Function function, ErrorCode errorCode) {
        try {
            function.method();
        } catch(BsmException bsmException) {
            if (bsmException.getErrorCode() == errorCode) {
                return;
            }
            fail("BsmException을 발생시키나, errorCode가 다릅니다.");
        }

        fail("BsmExeption을 발생시키지 않습니다.");
    }

    public interface Function {
        void method();
    }
}
