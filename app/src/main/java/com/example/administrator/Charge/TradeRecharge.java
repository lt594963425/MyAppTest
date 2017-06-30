package com.example.administrator.Charge;

/**
 * Created by admin on 2017/3/31.
 */

public class TradeRecharge {
          /*  "id": 2238,
            "tradeType": "1",
            "reason": "充值",
            "status": "0",
            "timestamp": 1490930810978,
            "amount": 100,
            "userid": 1614*/
    private int id;
    private String tradeType;
    private String reason;
    private String status;
    private Long timestamp;
    private Double amount;
    private int userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public String toString() {
        return "TradeRecharge{" +
                "id=" + id +
                ", tradeType='" + tradeType + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                ", userid=" + userid +
                '}';
    }
}
