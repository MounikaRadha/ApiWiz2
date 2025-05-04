package com.RadhaMounika.ApiWiz.util;

import com.RadhaMounika.ApiWiz.exceptions.ClientSideException;
import com.RadhaMounika.ApiWiz.exceptions.ServerSideException;
import lombok.extern.log4j.Log4j2;

import java.net.http.HttpResponse;

@Log4j2
public class ErrorHandler {
    public static void handleError(HttpResponse response) {
        if (response.statusCode() > 500) {
            String message = String.format("server side exception occurred status code %s", response.statusCode());
            log.info(message);
            throw new ServerSideException(message);
        }
        if (response.statusCode() > 400) {
            String message = String.format("client side exception occurred status code %s", response.statusCode());
            log.info(message);
            throw new ClientSideException(message);
        }
    }

    public static boolean isErrored(HttpResponse<String> response) {
        if (response.statusCode() > 399) {
            return true;
        }
        return false;
    }

}
