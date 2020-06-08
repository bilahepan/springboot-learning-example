package demo.springboot.slsx;

import java.io.Serializable;

public class LineVO implements Serializable {

    private String index = "-1";
    private String orderNum;
    private String userName;
    private String phoneNum;
    private String price = "-1";

    //

    public LineVO() {
    }

    public LineVO(String index, String orderNum, String userName, String phoneNum, String price) {
        this.index = index;
        this.orderNum = orderNum;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.price = price;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
