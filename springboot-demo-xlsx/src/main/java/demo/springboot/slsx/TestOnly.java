package demo.springboot.slsx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestOnly {
    public static void main(String[] args) {
        //String s = "200606-473319015241209+吴海杰+15874533388";
        //String s = "200606-232486667380904+罗晗+15179784492+12.8";
        //String s = "200606-235523441482858+王艳东+15155013718+12.8";
        //String s = "200606-529457153163998+张海英+18129939350+12.8";
        String s = "200606-529457153163998哈哈哈18522939350";
        String regex1 = "(\\d+-\\d+\\+*)([\\u4e00-\\u9fa5]+\\+*)(\\d{11}\\+*)(\\d*.*)";
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(s);
        //
        matcher.find();
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));
        System.out.println(matcher.group(3));
        System.out.println(matcher.group(4));
    }
}
