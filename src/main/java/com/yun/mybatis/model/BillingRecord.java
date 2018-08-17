package com.yun.mybatis.model;

import java.util.Date;

public class BillingRecord {
    private Long id;

    private String orderNo;

    private String orderPar;

    private String transactionNo;

    private Date time;

    private Integer state;

    private Integer errorCode;

    private Integer totalPrice;

    private Integer productIdx;

    private Integer payType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderPar() {
        return orderPar;
    }

    public void setOrderPar(String orderPar) {
        this.orderPar = orderPar == null ? null : orderPar.trim();
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getProductIdx() {
        return productIdx;
    }

    public void setProductIdx(Integer productIdx) {
        this.productIdx = productIdx;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", orderPar=").append(orderPar);
        sb.append(", transactionNo=").append(transactionNo);
        sb.append(", time=").append(time);
        sb.append(", state=").append(state);
        sb.append(", errorCode=").append(errorCode);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", productIdx=").append(productIdx);
        sb.append(", payType=").append(payType);
        sb.append("]");
        return sb.toString();
    }
}