package demo.springboot.thread.exercise.list;

public class LinkedList<T> {
    private transient int size = 0;
    private transient LinkedNode<T> first = null;
    private transient LinkedNode<T> last = null;

    /**
     * 添加元素到双向链表头部
     */
    public void addFirst(T t) {
        LinkedNode<T> oldFirst = first;
        LinkedNode<T> newNode = new LinkedNode<T>(t, null, oldFirst);
        first = newNode;

        if (oldFirst == null) {
            last = newNode;
        } else {
            oldFirst.setPrev(newNode);
        }

        size++;
    }

    /**
     * 将双向链表转化为一元数组：從頭開始循環到尾部。
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (LinkedNode<T> node = first; node != null; node = node.getNext()) {
            result[i++] = node.getValue();
        }
        return result;
    }

    /**
     * 反轉
     */
    public void reverse() {
        if (first == null || last == null)
            return;

        LinkedNode<T> prev;
        LinkedNode<T> next;
        LinkedNode<T> newFirst = null;
        LinkedNode<T> newLast = null;
        for (LinkedNode<T> node = first; node != null; node = node.getPrev()) {
            prev = node.getPrev();
            next = node.getNext();

            if (node.getPrev() == null) {
                newLast = node;
            } else if (node.getNext() == null) {
                newFirst = node;
            }

            node.setNext(prev);
            node.setPrev(next);
        }

        first = newFirst;
        last = newLast;
    }
}