package demo.springboot.thread.exercise.skiplist;

public class Node {
    //插入的score排序数据
    public Integer value;
    public String text;
    //分别对应的四个方向的指针
    public Node up, down, left, right;

    //构造函数
    public Node(Integer value) {
        this.value = value;
        down = up = right = left = null;
    }

    public Node(Integer value, String text) {
        this.value = value;
        this.text = text;
    }
}
