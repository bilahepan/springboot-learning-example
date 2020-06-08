package demo.springboot.slsx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XlsxProcess {
    private static String filePath = "./note.text";

    /**
     * 吴海杰:
     * 200606-473319015241209+吴海杰+15174510388
     * <p>
     * 蓝冰雪儿:
     * 200606-232486667380904+罗晗+15172782392+12.8
     * <p>
     * 靓丽人生:
     * 200606-235523441482858+王艳东+15035013718+12.8
     * <p>
     * 平常心:
     * 200606-529457153163998+张海英+18522939350+12.8
     * <p>
     * 哈哈哈:
     * 200606-529457153163998哈哈哈18522939350
     */
    public static void main(String[] args) {
        String filePath = "./note.text";
        String xlsxFilePath = "./result.xlsx";
        //
        ArrayList<String> textList = convertToArrayList(filePath);
        textList.forEach(item -> System.out.println(item));
        //
        ArrayList<LineVO> lineVOList = new ArrayList<>();
        textList.forEach(item -> lineVOList.add(line2VO(item)));
        //
        List<List<String>> dataList = convertToExcelData(lineVOList);
        ExcelUtils.writeExcel(dataList, xlsxFilePath);
    }

    private static List<List<String>> convertToExcelData(ArrayList<LineVO> lineVOList) {
        List<List<String>> dataList = new ArrayList<>();
        List<String> titleItemList = new ArrayList() {{
            add("序号");
            add("订单号");
            add("姓名");
            add("手机号");
            add("金额");
        }};
        dataList.add(titleItemList);
        //
        for (int i = 0; i < lineVOList.size(); i++) {
            int index = i + 1;
            List<String> lineItemList = new ArrayList<>();
            lineItemList.add(index + "");
            lineItemList.add(lineVOList.get(i).getOrderNum());
            lineItemList.add(lineVOList.get(i).getUserName());
            lineItemList.add(lineVOList.get(i).getPhoneNum());
            lineItemList.add(lineVOList.get(i).getPrice());
            //
            dataList.add(lineItemList);
        }
        return dataList;
    }


    //LineVO(String index, String orderNum, String userName, String phoneNum, String price)
    private static LineVO line2VO(String lineText) {
        String regex1 = "(\\d+-\\d+\\+*)([\\u4e00-\\u9fa5]+\\+*)(\\d{11}\\+*)(\\d*.*)";
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(lineText);
        //
        matcher.find();
        String index = "-1";
        try {
            matcher.group();
        } catch (Exception e) {
            System.err.println("错误行=" + lineText);
            throw new IllegalArgumentException();
        }
        String orderNum = matcher.group(1).replace("+", "");
        String userName = matcher.group(2).replace("+", "");
        String phoneNum = matcher.group(3).replace("+", "");

        String price;
        String temPrice = matcher.group(4);
        if (temPrice.length() < 1 || "".equals(temPrice)) {
            price = "-1";
        } else {
            price = temPrice;
        }
        //
        return new LineVO(index, orderNum, userName, phoneNum, price);
    }


    private static ArrayList<String> convertToArrayList(String filePath) {
        ArrayList<String> textList = new ArrayList<>();
        File file = new File(filePath);
        try {
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String s = null;
            while ((s = br.readLine()) != null) {
                //使用readLine方法，一次读一行
                //去除空行
                if (s.trim().length() < 1) {
                    continue;
                }
                if (s.contains(":") || s.contains("：")) {
                    continue;
                }
                //200607-646042420341598+徐伟＋15298308898
                //替换空格
                s = s.replaceAll(" ", "");
                //替换误写的 ＋
                s = s.replaceAll("＋", "+");
                textList.add(s.trim());
            }
            //textList.forEach(item -> System.out.println(item));
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textList;
    }
}

