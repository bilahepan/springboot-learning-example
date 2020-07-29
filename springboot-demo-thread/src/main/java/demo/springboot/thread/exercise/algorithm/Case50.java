package demo.springboot.thread.exercise.algorithm;


import java.util.BitSet;

/**
 * 题目描述
 * 在一个字符串中找到第一个只出现一次的字符，并返回它的位置。
 * <p>
 * Input: abacc
 * Output: b
 * 解题思路
 * 最直观的解法是使用 HashMap 对出现次数进行统计，但是考虑到要统计的字符范围有限，因此可以使用整型数组代替 HashMap，
 * 从而将空间复杂度由 O(N) 降低为 O(1)。
 * <p>
 * <p>
 * 思路：
 * ASCII码表，最多256个字符。普通128个字符。
 * 桶插法。
 * https://baike.baidu.com/item/ASCII/309296?fr=aladdin
 */
public class Case50 {

    public static void main(String[] args) {
        String str = "aabbcceffffedrrrcechhhrrrdddsssaaifffillll109ttt9013";
        int index = firstNotRepeatingChar(str);
        System.out.println("index=" + index);
        System.out.println("char=" + str.charAt(index));
    }

    //方法1
    //桶插法
    public static int firstNotRepeatingChar(String str) {
        int[] cnts = new int[256];
        for (int i = 0; i < str.length(); i++) {
            //char 类型可以整数的ASCII码表示
            int asciiNum = str.charAt(i);
            cnts[asciiNum]++;
        }
        for (int i = 0; i < str.length(); i++) {
            if (cnts[str.charAt(i)] == 1) {
                return i;
            }
        }
        return -1;
    }


    //方法2
    /**
     * 以上实现的空间复杂度还不是最优的。考虑到只需要找到只出现一次的字符，那么需要统计的次数信息只有 0,1,更大，使用两个比特位就能存储这些信息。
     *
     * 而实际上BitSet内部核心是一个long的数组。
     * 由于一个long在java中占用8个字节，即64位。
     * */
    public int FirstNotRepeatingChar2(String str) {
        //java.util.BitSet
        BitSet bs1 = new BitSet(256);
        BitSet bs2 = new BitSet(256);
        //char[]
        for (char c : str.toCharArray()) {
            if (!bs1.get(c) && !bs2.get(c)) {
                // 0 0 -> 0 1
                bs1.set(c);
            } else if (bs1.get(c) && !bs2.get(c)) {
                // 0 1 -> 1 1
                bs2.set(c);
            }
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 0 1
            if (bs1.get(c) && !bs2.get(c)) {
                return i;
            }
        }
        return -1;
    }

}