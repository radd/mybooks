package io.github.radd.mybooks.utils.dto;

public class Response<T> {

    public final static String DONE = "done";
    public final static String ERROR = "error";

    private String status;
    private T data;

    public Response(){
        status = ERROR;
    }

    public Response(String status){
        this.status = status;
    }

    public Response(String status, T data){
        this.status = status;
        this.data = data;
    }

    public void set(T data) {
        this.data = data;
        status = DONE;
    }

    public void set(String status, T data) {
        this.data = data;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
