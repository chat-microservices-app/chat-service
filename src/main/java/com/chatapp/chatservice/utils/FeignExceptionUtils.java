package com.chatapp.chatservice.utils;

import com.chatapp.chatservice.enums.FeignProps;

import java.util.Map;

public class FeignExceptionUtils {


    public static Map<FeignProps, String> getPropsFromFeignError(String message) {

        if (!followsTheExpectedFormat(message)) {
            return Map.of(
                    FeignProps.MESSAGE, message,
                    FeignProps.STATUS_CODE, "500",
                    FeignProps.PATH, "unknown",
                    FeignProps.HTTP_METHOD, "unknown"
            );
        }

        // parse the exception message to get the status code
        // each word is separated by a space and wrapped in []
        String[] messageArray = message.split(" ");


        return Map.of(
                FeignProps.MESSAGE, getMessageFromFeignError(message),
                FeignProps.STATUS_CODE, getPropFromFeignError(messageArray, 0),
                FeignProps.PATH, getPropFromFeignError(messageArray, 4),
                FeignProps.HTTP_METHOD, getPropFromFeignError(messageArray, 2)
        );
    }


    // TODO: improve logic of this method
    protected static boolean followsTheExpectedFormat(String message) {
        return message.startsWith("[") && message.endsWith("]");
    }


    // each prop is wrapped in [], so remove them
    protected static String getPropFromFeignError(String[] message, int index) {

        return message[index]
                .replace("[", "")
                .replace("]", "");
    }


    // the message is the last prop; to find it get the last index of [ and ]
    protected static String getMessageFromFeignError(String message) {
        return message.substring(
                message.lastIndexOf("[") + 1,
                message.lastIndexOf("]"));
    }
}
