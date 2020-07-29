package demo.springboot.thread.exercise.algorithm;


import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * 最小的K个数。
 * <p>
 * <p>
 * 其他大顶堆代码。
 *
 * @https://blog.csdn.net/hbtj_1216/article/details/72890920
 */
public class Case40 {
    /**
     * 复杂度：O(NlogK) + O(K)
     * 特别适合处理海量数据
     * 应该使用大顶堆来维护最小堆，而不能直接创建一个小顶堆并设置一个大小，企图让小顶堆中的元素都是最小元素。
     * <p>
     * 维护一个大小为 K 的最小堆过程如下：在添加一个元素之后，如果大顶堆的大小大于 K，那么需要将大顶堆的堆顶元素去除。
     */
    public ArrayList<Integer> kSmallerNum(int[] nums, int k) {
        if (k > nums.length || k <= 0) {
            return new ArrayList<>();
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int num : nums) {
            maxHeap.add(num);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        return new ArrayList<>(maxHeap);
    }
}



