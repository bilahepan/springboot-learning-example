package demo.springboot.thread.exercise.lru.case1;

public class TestCase {

    public static void main(String[] args) {

        LRUCache cache = new LRUCache(2);
        String key1 = "ka";
        String value1 = "va";
        cache.set(key1, value1);
        //
        String key2 = "kb";
        String value2 = "vb";
        cache.set(key2,value2);
        //
        String key3 = "kc";
        String value3 = "vc";
        cache.set(key3,value3);

        String res = cache.get(key1);
        System.out.println(res);

    }
}
