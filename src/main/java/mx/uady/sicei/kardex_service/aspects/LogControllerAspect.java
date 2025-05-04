package mx.uady.sicei.kardex_service.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Component
public class LogControllerAspect {
    private final Gson gson = new Gson();
    private final HttpServletRequest request;

    public LogControllerAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Pointcut("@within(LogController)")
    public void logHttpRequestAnnotationPointcut() {
    }

    @Around("logHttpRequestAnnotationPointcut()")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        Object response = joinPoint.proceed();

        log.info("[{} {}] arguments={} response={}",
                requestMethod,
                requestURI,
                gson.toJson(joinPoint.getArgs()),
                gson.toJson(response));

        return response;
    }
}
