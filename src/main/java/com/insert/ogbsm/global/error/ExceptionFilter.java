package com.insert.ogbsm.global.error;

import com.insert.ogbsm.global.error.exception.BsmException;
import com.insert.ogbsm.global.error.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BsmException e) {
            writeErrorCode(response, e.getErrorCode());
        } catch (ExpiredJwtException e) {
            writeErrorCode(response, ErrorCode.EXPIRED_JWT);
        } catch (JwtException e) {
            writeErrorCode(response, ErrorCode.INVALID_TOKEN);
        } catch (EntityNotFoundException e) {
            writeErrorCode(response, ErrorCode.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            writeErrorCode(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void writeErrorCode(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage()
        );

        response.setStatus(errorResponse.getStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(errorResponse.toString());
    }
}
