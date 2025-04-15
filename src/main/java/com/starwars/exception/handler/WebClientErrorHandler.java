package com.starwars.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.*;

import com.starwars.constants.Constants;
import com.starwars.exception.CustomizableException;

import reactor.core.publisher.Mono;

public class WebClientErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebClientErrorHandler.class);

    public static ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            logger.info(Constants.REQUEST_LOG_TEMPLATE, request.method(), request.url());
            return Mono.just(request);
        }).andThen(ExchangeFilterFunction.ofResponseProcessor(response -> {
            if (response.statusCode().isError()) {
                // Acceder a la request desde el response no es posible directamente
                // Pero podemos agregar el logging de status y capturar el body 
                return response.bodyToMono(String.class)
                        .defaultIfEmpty(Constants.DEFAULT_ERROR_BODY)
                        .flatMap(body -> {
                            logger.error(Constants.ERROR_LOG_TEMPLATE, response.statusCode(), body);
                            return Mono.error(new CustomizableException(String.format(Constants.ERROR_MESSAGE_TEMPLATE, response.statusCode(), body)));
                        });
            }
            return Mono.just(response);
        }));
    }
}
