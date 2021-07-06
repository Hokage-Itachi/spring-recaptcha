package com.example.recaptchaservice.annotation;

import com.example.recaptchaservice.exception.BadRequestException;
import com.example.recaptchaservice.service.RecaptchaService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class RecaptchaAnnotationProcessor {
    @Autowired
    private RecaptchaService recaptchaService;

    @Around(value = "@annotation(com.example.recaptchaservice.annotation.VerifyRecaptcha)")
    public void process(ProceedingJoinPoint joinPoint) throws Throwable {
        // get method annotated annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        VerifyRecaptcha annotation = method.getAnnotation(VerifyRecaptcha.class);
        // get annotation details
        String action = annotation.action();
        Float threshold = annotation.threshold();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String gRecaptchaResponse = request.getHeader("Recaptcha");
        if (gRecaptchaResponse == null || !recaptchaService.verify(gRecaptchaResponse, threshold, action)) {
            throw new BadRequestException("Recaptcha invalid");
        }
        joinPoint.proceed();
    }


}
