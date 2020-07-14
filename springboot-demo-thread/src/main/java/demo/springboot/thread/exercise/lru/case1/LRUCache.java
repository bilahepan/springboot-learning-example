package demo.springboot.thread.exercise.lru.case1;

import java.util.Hashtable;

public class LRUCache {

    private Hashtable<String, LinkedNode>
            cache = new Hashtable<String, LinkedNode>();
    //
    private int count;
    private int capacity;
    LinkedNode head, tail;
    //

    //初始化空链表，构建一个带头和带尾循环双向链表
    public LRUCache(int capacity) {
        this.count = 0;
        this.capacity = capacity;
        head = new LinkedNode();
        head.pre = null;
        //
        tail = new LinkedNode();
        tail.next = null;
        //
        head.next = tail;
        tail.pre = head;
    }


    /***/
    //获取值
    public String get(String key) {
        //获取元素
        //TODO 这样获取不准确吧，待验证
        LinkedNode node = cache.get(key);
        if (node == null) {
            return null;//不存在
        }
        //存在，进行移动到头部满足LRU
        this.moveToHead(node);
        return node.value;
    }

    /***/
    //设置kv值到缓存中
    public void set(String key, String value) {
        //TODO 这样获取不准确吧，待验证
        LinkedNode node = cache.get(key);
        //链表不存在
        if (node == null) {
            //新建链表
            LinkedNode newNode = new LinkedNode();
            newNode.key = key;
            newNode.value = value;
            //链表加入节点元素
            this.cache.put(key, newNode);
            this.addNode(newNode);
            //
            count++;
            //判断如果超容量，则尾部淘汰元素
            if (count > capacity) {
                LinkedNode tail = this.popTail();
                count--;
            }
        } else {
            //存在，更新节点的值
            node.value = value;
            //把节点移动到头部
            this.moveToHead(node);
        }
    }

    //删除链表的一个节点
    private void removeNode(LinkedNode node) {
        LinkedNode pre = node.pre;
        LinkedNode next = node.next;
        //该节点前面节点的next指向node的next
        pre.next = next;
        //该节点后面节点的pre指向node的pre
        next.pre = pre;
    }

    //添加一个节点，头插法，添加至头结点的紧邻的右侧位置
    private void addNode(LinkedNode node) {
        node.pre = head;
        node.next = head.next;
        //主要下面这个顺序不能乱了，要瞻前顾后
        head.next.pre = node;
        head.next = node;
    }


    //把存在的某个节点移动到头部（这里头部只head的next位置）
    private void moveToHead(LinkedNode node) {
        //移除node
        this.removeNode(node);
        //添加到头部
        this.addNode(node);
    }


    //移除尾部的元素并返回它，比如队列满了，就要移除尾部的一个元素
    private LinkedNode popTail() {
        LinkedNode res = tail.pre;
        this.removeNode(res);
        return res;
    }


}
