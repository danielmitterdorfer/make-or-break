package name.mitterdorfer.makebreak;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Based on http://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-an-unsorted-array
 *
 * This benchmark demonstrates the effects of the branch prediction unit. The method <code>#benchmarkSumUnsorted()</code>
 * will produce lots of branch mispredictions due to random data in the array whereas <code>#benchmarkSumSorted()</code>
 * will behave much better because the branches are easy to predict due to sorted data:
 *
 * Benchmark                                        Mode  Samples     Score  Score error   Units
 * n.m.m.ArraySumBenchmark.benchmarkSumSorted      thrpt      200  1186,548        5,305  ops/us
 * n.m.m.ArraySumBenchmark.benchmarkSumUnsorted    thrpt      200   378,887        2,352  ops/us
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ArraySumBenchmark {
    private static final int PROBLEM_SIZE = 64 * 1024;

    private final int[] unsortedArray = new int[PROBLEM_SIZE];
    private final int[] sortedArray = new int[PROBLEM_SIZE];

    @Setup
    public void setUp() {
        Random random = new Random(System.currentTimeMillis());
        for (int idx = 0; idx < unsortedArray.length; idx++) {
            unsortedArray[idx] = random.nextInt() % 256;
        }
        // create a sorted copy
        System.arraycopy(unsortedArray, 0, sortedArray, 0, unsortedArray.length);
        Arrays.sort(sortedArray);
    }

    @Benchmark
    @OperationsPerInvocation(PROBLEM_SIZE)
    public long benchmarkSumUnsorted() {
        long sum = 0;
        for (int idx = 0; idx < unsortedArray.length; idx++) {
            if (unsortedArray[idx] >= 128) {
                sum += unsortedArray[idx];
            }
        }
        return sum;
    }

    @Benchmark
    @OperationsPerInvocation(PROBLEM_SIZE)
    public long benchmarkSumSorted() {
        long sum = 0;
        for (int idx = 0; idx < sortedArray.length; idx++) {
            if (sortedArray[idx] >= 128) {
                sum += sortedArray[idx];
            }
        }
        return sum;
    }
}
