//package reactiveProgramming.moviesinfoservice.exceptionHandler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.support.WebExchangeBindException;
//
//@ControllerAdvice
//@Slf4j
//public class GlobalErrorHandler {
//
//    @ExceptionHandler(WebExchangeBindException.class)
//    public ResponseEntity<String> handleRequestBodyError(WebExchangeBindException ex)
//    {
//        log.error("Exception Caught in handleRequestBodyError : {}",ex.getMessage(),ex);
//        ex.getBindingResult().getAllErrors().stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage);
//    }
//}
