package com.example.backendglasses.model.dto;

public class ApiResponse <T>{
    private T dataRes;
    private String token;
    private Double totalPrice;
    private String flag;

    public ApiResponse(T dataRes, String token, Double totalPrice) {
        this.dataRes = dataRes;
        this.token = token;
        this.totalPrice = totalPrice;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ApiResponse(T dataRes, String token, Double totalPrice, String flag) {
        this.dataRes = dataRes;
        this.token = token;
        this.totalPrice = totalPrice;
        this.flag = flag;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ApiResponse(T dataRes, String token) {
        this.dataRes = dataRes;
        this.token = token;
    }

    public ApiResponse() {
    }

    public T getDataRes() {
        return dataRes;
    }

    public void setDataRes(T dataRes) {
        this.dataRes = dataRes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "dataRes=" + dataRes +
                ", token='" + token + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
