package demo.springboot.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Comparator;
import java.util.Locale;

public class UserVOComparator implements Comparator<UserVO> {
    /**
     * 按名字首字母排序
     */
    @Override
    public int compare(UserVO one, UserVO another) {

        String oneNameFirstChar = getPinyinFirstChar(one.getName());
        String anotherNameFirstChar = getPinyinFirstChar(another.getName());
        System.out.println("one.getName()=" + one.getName() + " ;oneNameFirstChar=" + oneNameFirstChar);
        System.out.println("another.getName()=" + another.getName() + " ;anotherNameFirstChar=" + anotherNameFirstChar);
        //
        return oneNameFirstChar.compareTo(anotherNameFirstChar);
    }

    private static String getPinyinFirstChar(String chineseName) {
        try {
            //汉字的情况做处理
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            //设置大小写格式
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            //设置声调格式：
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            String firstName = chineseName.substring(0, 1);
            if (firstName.matches("[\\u4E00-\\u9FA5]")) {
                char firstNameChar = firstName.toCharArray()[0];
                String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(firstNameChar, defaultFormat);
                return pinyinStringArray[0].toLowerCase(Locale.ROOT).substring(0,1);
            } else {//不是汉字的情况
                return firstName;
            }
        } catch (BadHanyuPinyinOutputFormatCombination ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
