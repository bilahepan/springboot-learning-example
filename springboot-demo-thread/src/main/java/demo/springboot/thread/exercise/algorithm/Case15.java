package demo.springboot.thread.exercise.algorithm;

/**
 * 输入一个整数，输出该数二进制表示中 1 的个数。
 *
 *
 * 思路：
 * n&(n-1)
 * 该位运算去除 n 的位级表示中最低的那一位。
 *
 * n       : 10110100
 * n-1     : 10110011
 * n&(n-1) : 10110000
 * */
public class Case15 {

    public static void main(String[] args) {
        System.out.println(numberOf1(19));
    }

    public static  int numberOf1(int n) {
        int cnt = 0;
        while (n != 0) {
            cnt++;
            n &= (n - 1);
        }
        return cnt;
    }

//    public static int test2(int n)
//    {
//        int cnt = 0;
//
//
//    }
}
