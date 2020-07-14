package demo.springboot.thread.exercise.count.case1;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

public class TestCase {
    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();
        System.out.println("length="+numbers.length);

        //for循环
        Instant start1 = Instant.now();
        Calculator calculator1 = new ForLoopCalculator();
        long result1 = calculator1.sumUp(numbers);
        Instant end1 = Instant.now();
        System.out.println("耗时1：" + Duration.between(start1, end1).toMillis() + "ms");
        System.out.println(result1);

        //线程并发
        Instant start2 = Instant.now();
        Calculator calculator2 = new ExecutorServiceCalculator();
        long result2 = calculator2.sumUp(numbers);
        Instant end2 = Instant.now();
        System.out.println("耗时2：" + Duration.between(start2, end2).toMillis() + "ms");
        System.out.println(result2);

        //ForkJoin
        Instant start3 = Instant.now();
        Calculator calculator3 = new ForkJoinCalculator();
        long result3 = calculator3.sumUp(numbers);
        Instant end3 = Instant.now();
        System.out.println("耗时3：" + Duration.between(start3, end3).toMillis() + "ms");

        System.out.println(result3);


    }
}
