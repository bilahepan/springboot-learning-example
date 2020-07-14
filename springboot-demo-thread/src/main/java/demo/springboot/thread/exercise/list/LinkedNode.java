package demo.springboot.thread.exercise.list;

public class LinkedNode<T> {
    private T value;
    private LinkedNode<T> prev;
    private LinkedNode<T> next;

    public LinkedNode(T value, LinkedNode<T> prev, LinkedNode<T> next) {
        super();
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LinkedNode<T> getPrev() {
        return prev;
    }

    public void setPrev(LinkedNode<T> prev) {
        this.prev = prev;
    }

    public LinkedNode<T> getNext() {
        return next;
    }

    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }
}
