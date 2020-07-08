package dev.bensalcie.practicletask.preferences;

public class User {
    private long customerid;
    private String customername,customeremail,cutomeraccount;

    public User(long customerid, String customername, String customeremail, String cutomeraccount) {
        this.customerid = customerid;
        this.customername = customername;
        this.customeremail = customeremail;
        this.cutomeraccount = cutomeraccount;
    }

    public long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomeremail() {
        return customeremail;
    }

    public void setCustomeremail(String customeremail) {
        this.customeremail = customeremail;
    }

    public String getCutomeraccount() {
        return cutomeraccount;
    }

    public void setCutomeraccount(String cutomeraccount) {
        this.cutomeraccount = cutomeraccount;
    }
}