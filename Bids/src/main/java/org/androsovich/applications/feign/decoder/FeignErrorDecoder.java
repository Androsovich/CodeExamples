package org.androsovich.applications.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.androsovich.applications.exceptions.BadFeignRequestException;

import static org.androsovich.applications.constants.Constants.BAD_FEIGN_REQUEST;
import static org.androsovich.applications.constants.Constants.GENERIC_ERROR;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()){
            case 400:
                return new BadFeignRequestException(BAD_FEIGN_REQUEST);
            default:
                return new Exception(GENERIC_ERROR  + response.reason());
        }
    }
}
