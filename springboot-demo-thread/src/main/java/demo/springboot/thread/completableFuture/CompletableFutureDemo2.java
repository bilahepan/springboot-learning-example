package demo.springboot.thread.completableFuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo2 {

    /**
     * 可以看到，thenAccept和thenRun都是无返回值的。
     * 如果说thenApply是不停的输入输出的进行生产，那么thenAccept和thenRun就是在进行消耗。它们是整个计算的最后两个阶段。
     * 同样是执行指定的动作，同样是消耗，二者也有区别：
     * <p>
     * thenAccept接收上一阶段的输出作为本阶段的输入
     * <p>
     * thenRun根本不关心前一阶段的输出，根本不不关心前一阶段的计算结果，因为它不需要输入参数
     * thenCombine整合两个计算结果
     * <p>
     * ---
     * CompletableFuture可以指定异步处理流程：
     * <p>
     * thenAccept()处理正常结果；
     * exceptional()处理异常结果；
     * thenApplyAsync()用于串行化另一个CompletableFuture；
     * anyOf()和allOf()用于并行化多个CompletableFuture。
     */
    public static void main(String[] args) throws Exception {
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://money.163.com/code/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);

        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
