package vn.hoidanit.jobhunter.domain;

import java.util.List;

public class RestResponse<T> {
    private int statusCode;
    private String errorMessage;
    private List<String> errors;

    //message có thể là String hoặc arrayList
    private Object message;
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrors(List<String> errors) { // Thêm setter cho errors
        this.errors = errors;
    }
    public List<String> getErrors() {
        return errors;
    }
}
