package com.srable.corporation.scrabble;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Utils class for controllers related code.
 * Created by lubomir.viluda on 3/23/2019.
 */
public final class ControllerUtils {

    private ControllerUtils() {
    }

    /**
     * Create link on the path with arguments.
     * @param urlPath
     * @param arguments
     * @return
     */
    public static String makeLink(String urlPath, Object... arguments) {
        final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ':' + httpServletRequest.getServerPort();

        return baseUrl + String.format(urlPath, encodeArguments(arguments));
    }

    private static Object[] encodeArguments(Object... params) {
        if (params.length == 0) {
            return new String[0];
        }

        final Object[] encoded = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            encoded[i] = urlEncode(Objects.toString(params[i]));
        }
        return encoded;
    }

    private static String urlEncode(String value) {
        return UriUtils.encodePath(value, StandardCharsets.UTF_8.name()).replace("/", "%2F");
    }
}
