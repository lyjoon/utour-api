package com.utour.aspect;

import com.utour.common.CommonComponent;
import com.utour.exception.InternalException;
import com.utour.util.ErrorUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect extends CommonComponent {

    /**
     * 에러 핸들링
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = "execution(public * com.utour.service..*Service.*(..))")
    public Object errorHandler(ProceedingJoinPoint proceedingJoinPoint){
        try {
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        } catch (InternalException internalException) {
            log.error("{}", ErrorUtils.throwableInfo(internalException));
            throw internalException;
        } catch (Throwable throwable) {
            log.error("{}", ErrorUtils.throwableInfo(throwable));
            throw new InternalException(throwable);
        }
    }
}
