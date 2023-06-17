package com.example.demo.access;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static com.example.demo.access.CheckAccessType.BR;
import static com.example.demo.access.CheckAccessType.PARTNER;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
@Component
@RequiredArgsConstructor
public class CheckAccessAspect {

    private final AccessService accessService;

    @Before("execution(* *(.., @CheckAccess (*), ..))")
    public void verifyUserAccess(final JoinPoint joinPoint) {
        if (joinPoint.getSignature() instanceof MethodSignature signature) {
            var method = signature.getMethod();
            Map<CheckAccessType, List<String>> keyParams = this.getParametersWithAnnotation(joinPoint, method);
            keyParams.forEach((key, value) -> {
                if (!value.isEmpty()) {
                    if (BR.equals(key)) {
                        accessService.verifyAccessToBusinessRelationships(value);
                    } else if (PARTNER.equals(key)) {
                        accessService.verifyAccessToPartners(value);
                    }
                }
            });
        }

    }

    @SuppressWarnings("unchecked")
    private Map<CheckAccessType, List<String>> getParametersWithAnnotation(JoinPoint joinPoint, Method method) {
        Map<CheckAccessType, List<String>> keyParams = this.initMap();
        Parameter[] params = method.getParameters();
        for (int i = 0; i < params.length; i++) {
            if (params[i].isAnnotationPresent(CheckAccess.class)) {
                if (joinPoint.getArgs()[i] instanceof List) {
                    keyParams.get(params[i].getAnnotation(CheckAccess.class).value()).addAll((ArrayList<String>) joinPoint.getArgs()[i]);
                } else {
                    keyParams.get(params[i].getAnnotation(CheckAccess.class).value()).add((String) joinPoint.getArgs()[i]);
                }
            }
        }
        return keyParams;
    }

    private Map<CheckAccessType, List<String>> initMap() {
        Map<CheckAccessType, List<String>> result = new EnumMap<>(CheckAccessType.class);
        result.put(BR, new ArrayList<>());
        result.put(PARTNER, new ArrayList<>());
        return result;
    }
}

