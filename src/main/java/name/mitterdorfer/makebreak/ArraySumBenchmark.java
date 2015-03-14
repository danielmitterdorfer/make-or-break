package name.mitterdorfer.makebreak;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Loosely based on http://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-an-unsorted-array
 *
 * This benchmark demonstrates the effects of the branch prediction unit. Based on the benchmark parameter
 * <code>randomizationProbability</code> the benchmark will produce more or less branch mispredictions due to
 * random data in the array.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ArraySumBenchmark {
    private static final int MAX_ELEMENT_VALUE = 256;

    private int[] array;

    @Param({"8192", "16384", "32768", "65536"})
    public int problemSize;

    // every 0, 4, 2, 1 element is random
    @Param({"0.0", "0.25", "0.5", "1.0"})
    public double randomizationProbability;

    @Setup
    public void setUp() {
        array = new int[problemSize];

        Random random = new Random(System.currentTimeMillis());
        for (int idx = 0; idx < array.length; idx++) {
            int value = getLinearValue(idx);
            if (shouldBeRandomElement(idx)) {
                // use a random value to break regularity in the pattern and to trip branch prediction
                value = random.nextInt() % MAX_ELEMENT_VALUE;
            }
            array[idx] = value;
        }
    }

    private int getLinearValue(int idx) {
        double percentageOfListSize = (double) idx / (double) (problemSize - 1);
        return (int) Math.round(percentageOfListSize * MAX_ELEMENT_VALUE);
    }

    private boolean shouldBeRandomElement(int idx) {
        if (randomizationProbability == 0.0d) {
             return false;
        }
        int elementRange = (int) Math.round(1 / randomizationProbability);
        return idx % elementRange == 0;
    }

    @Benchmark
    public long benchmarkSum() {
        long sum = 0;
        for (int idx = 0; idx < array.length; idx++) {
            if (array[idx] >= 128) {
                sum += array[idx];
            }
        }
        return sum;
    }
}
