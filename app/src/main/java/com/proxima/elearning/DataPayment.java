package com.proxima.elearning;

public class DataPayment {
    String orderId,stndid,name,amount,date,txnid;

    public DataPayment(String orderId, String stndid, String name, String amount, String date, String txnid) {
        this.orderId = orderId;
        this.stndid = stndid;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.txnid = txnid;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStndid() {
        return stndid;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTxnid() {
        return txnid;
    }
}
