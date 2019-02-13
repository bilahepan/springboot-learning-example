package demo.springboot.pulsar.sdk.consumer;

import demo.springboot.pulsar.sdk.AESBase64Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/23 下午12:51
 */
public final class ConsumerData implements Serializable {
    public static void main(String[] args) {
        try {
            String secretKey = "qk9acfmnkndwmmpykd9ryca8cxduyuf9".substring(8, 24);
            ArrayList<String> list = new ArrayList<>();

            list.add("8LX3/ZcCutXxKbBpGSu0/KbENXFLtJCU+BS+QzGu7Z+07ohEha++/3RHyu9CG++FoaRL+WHsgPraRhVswIqbeDWzRPIXdK+5GPcQgVTmP2J/Z+EVqw8YBZUnMGseP+So1I5atWbpcOFDb3haZf3Y7uN9Wij+j+U8ciZeMzG9azC+bAKYI2skkpn8dSYGjLo9o1R17PjSjnFgZWnyltwtZmZET1pdV9CVQax+oDWTyIGRy8mDrh9J8NJQBzYSEapI");
            list.forEach(item -> {
                try {
                    System.out.println(AESBase64Utils.decrypt(item, secretKey));
                } catch (Exception e) {
                    System.err.println(e);
                }

            });

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Integer protocol;

    private String pv;

    private Long t;

    private String data;

    private String sign;

    public Integer getProtocol() {
        return protocol;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public Long getT() {
        return t;
    }

    public void setT(Long t) {
        this.t = t;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}