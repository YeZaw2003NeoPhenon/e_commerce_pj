package com.example.cart_service.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{
    private boolean success;
    private String message;
    private T data;
    private URI uri;
    private ErrorResponse error;
    private Metadata metadata;

    public static <T> ApiResponse<T> success(T data){
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setMetadata(new Metadata());
        return response;
    }

    public static <T> ApiResponse<T> success(String message){
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> success(T data, String message, URI uri){
        ApiResponse<T> response = success(data);
        response.setMessage(message);
        response.setUri(uri);
        return response;
    }

    public static <T> ApiResponse<T> error(String message){
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setError(new ErrorResponse(message));
        response.setMetadata(new Metadata());
        return response;
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus httpStatus){
        ApiResponse<T> response = error(message);
        response.getError().setStatus(httpStatus.value());
        return response;
    }

    public static <T> ApiResponse<T> validationErrors(List<String> errors , HttpStatus httpStatus){
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setError(new ErrorResponse("Validation Errors",errors));
        response.getError().setStatus(httpStatus.value());
        response.setMetadata(new Metadata());
        return response;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public ErrorResponse getError() {
        return error;
    }

    public URI getUri() {
        return uri;
    }
    public ApiResponse(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static class ErrorResponse {

        private String message;
        private List<String> details;

        private Integer status;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public ErrorResponse(String message) {
            this.message = message;
        }

        public ErrorResponse(String message, List<String> details) {
            this.message = message;
            this.details = details;
        }
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<String> getDetails() {
            return details;
        }

        public void setDetails(List<String> details) {
            this.details = details;
        }
    }
    public static class Metadata{
        private long timestamp;
        private String key;

        public Metadata() {
            this.timestamp = System.currentTimeMillis();
            this.key = UUID.randomUUID().toString();
        }

        public Metadata(long timestamp, String key) {
            this.timestamp = timestamp;
            this.key = key;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}