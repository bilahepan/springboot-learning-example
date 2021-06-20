package demo.springboot.pinyin;

import com.alibaba.fastjson.JSON;
import java.util.*;

/**
 * 自定义实现比较器
 * https://blog.csdn.net/BiLaHePan/article/details/75808377
 *
 */
public class PinyinOrderDemo {
    public static void main(String[] args) {
        List<UserVO> resList = new ArrayList<>();
        //
        List<UserVO> inputList = new ArrayList() {{
            add(new UserVO("1北峰"));
            add(new UserVO("-0北峰"));
            add(new UserVO("0北峰"));
            add(new UserVO("*北峰"));
            add(new UserVO("a北峰"));
            add(new UserVO("-ff北峰"));
            add(new UserVO("c 北峰"));
            add(new UserVO("哲理"));
            add(new UserVO("蔡蔡"));
            add(new UserVO("菜菜"));
            add(new UserVO("阿毛"));
            add(new UserVO("阿哈"));
            add(new UserVO("安宁"));
            add(new UserVO("北峰"));

        }};

        List<UserVO> pinyinNameList = new ArrayList<>();
        List<UserVO> notPinyinNameList = new ArrayList<>();

        //分开
        for (UserVO vo : inputList) {
            String name = vo.getName();
            //判断第一个字符是不是汉字,或a-z或A-Z
            if (Character.toString(name.toCharArray()[0]).matches("[\\u4E00-\\u9FA5a-zA-Z]+")) {
                pinyinNameList.add(vo);
            } else {
                notPinyinNameList.add(vo);
            }
        }

        //分别排序
        Collections.sort(pinyinNameList, new UserVOComparator());
        Collections.sort(notPinyinNameList, new UserVOComparator());

        //结果
        resList.addAll(pinyinNameList);
        resList.addAll(notPinyinNameList);

        resList.forEach((item) -> System.out.println(JSON.toJSONString(item)));


        //--正确顺序参考--//
        //{"name":"阿哈"}
        //{"name":"阿毛"}
        //{"name":"安宁"}
        //{"name":"北峰"}
        //{"name":"菜菜"}
        //{"name":"蔡蔡"}
        //{"name":"哲理"}
    }
}
