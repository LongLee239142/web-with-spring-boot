package vn.hoidanit.jobhunter.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.hoidanit.jobhunter.domain.RestResponse;

import java.util.List;


@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<RestResponse<Object>> handleIdException(Exception e) {
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setErrorMessage(e.getMessage());
        res.setMessage("IdInvalidException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validateError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

        // Lấy danh sách tất cả lỗi
        List<String> errors = fieldErrors.stream().map(FieldError::getDefaultMessage).toList();

        if (errors.size() > 1) {
            res.setErrors(errors);  // Nếu có nhiều lỗi, lưu vào danh sách errors
        } else {
            res.setErrorMessage(errors.get(0)); // Nếu chỉ có 1 lỗi, lưu vào errorMessage
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }



}