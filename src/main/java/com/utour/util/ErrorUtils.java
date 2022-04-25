package com.utour.util;

import com.utour.common.Constants;

public class ErrorUtils {

    public static final String throwableInfo(Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder("["+ throwable.getClass().getSimpleName() +"] " + throwable.getMessage());

        for(StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            String steElementTypeName = stackTraceElement.getClassName();
            if(steElementTypeName.contains(Constants.ROOT_PACKAGE)) {
                stringBuilder
                        .append(System.lineSeparator())
                        .append(" - ").append(stackTraceElement.getFileName()).append(".").append(stackTraceElement.getMethodName()).append("("+ stackTraceElement.getLineNumber() +")");
            }
        }

        return stringBuilder.toString();
    }
}
