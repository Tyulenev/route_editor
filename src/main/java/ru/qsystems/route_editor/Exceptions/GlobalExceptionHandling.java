package ru.qsystems.route_editor.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler
    public ResponseEntity<RouteIncorrectData> handleException(
            Exception exception) {
        RouteIncorrectData data = new RouteIncorrectData();
        data.setInfo(exception.getMessage());
        if (exception instanceof RouteNotFoundException) {
            return new ResponseEntity<RouteIncorrectData>(data, HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<RouteIncorrectData>(data, HttpStatus.BAD_REQUEST);
    }
}
