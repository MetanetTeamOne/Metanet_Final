package com.metanet.finalproject.pay.model.request;
import java.util.List;

public class SubscribePayload {
    public String billingKey; //필수
    public String itemName; //필수
    public long price; //필수
    public int taxFree;
    public String orderId; //필수
    public int quota;
    public int interest;
    public User userInfo;
    public List<Item> items;
    public String feedbackUrl;
    public String feedbackContentType;
    public SubscribeExtra extra;
    public String schedulerType; //정기결제 예약시
    public long executeAt; //정기결제 예약시
}
