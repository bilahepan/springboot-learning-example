package demo.springboot.thread.exercise.algorithm;

/**
 * 递归
 */
public class FibNum {
    public static void main(String[] args) {
        //数列：1，1，2，3，5，8......
        //输出15个斐波那契数列
        for (int i = 1; i <= 15; i++) {
            System.out.print(f(i) + "\t");
        }
    }

    public static int f(int n) {
        if (n == 1 || n == 2) {
            //返回终结条件
            return 1;
        } else {
            //元操作
            return f(n - 1) + f(n - 2);
        }
    }
}