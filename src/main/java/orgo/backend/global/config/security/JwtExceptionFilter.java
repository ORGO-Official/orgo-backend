package orgo.backend.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import orgo.backend.global.error.ErrorCode;
import orgo.backend.global.error.exception.UserNotFoundException;
import orgo.backend.global.error.exception.OrgoJwtException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (OrgoJwtException | UserNotFoundException e) {
            setErrorResponse(response, e);
        }
    }

    public void setErrorResponse(HttpServletResponse response, Throwable e) throws IOException {
        ErrorCode errorCode = extractErrorCode(e);
        setResponseBody(response, errorCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getHttpStatus().value());
    }

    private void setResponseBody(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Object> body = new HashMap<>();
        fillBody(response, errorCode, body);
        mapper.writeValue(response.getOutputStream(), body);
    }

    private void fillBody(HttpServletResponse response, ErrorCode errorCode, Map<String, Object> body) {
        body.put("status", errorCode.getHttpStatus().value());
        body.put("code", errorCode.getCode());
        body.put("name", errorCode.name());
        body.put("message", errorCode.getMessage());
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");
    }

    private ErrorCode extractErrorCode(Throwable e) {
        if (e instanceof UserNotFoundException) {
            return ((UserNotFoundException) e).getErrorCode();
        }
        return ((OrgoJwtException) e).getErrorCode();
    }
}
