package name.mitterdorfer.makebreak;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark                                       Mode  Samples     Score  Score error   Units
 * n.m.m.PointerChasingBenchmark.sumArray         thrpt      200  2755,078        7,899  ops/us
 * n.m.m.PointerChasingBenchmark.sumArrayList     thrpt      200   781,525        8,888  ops/us
 * n.m.m.PointerChasingBenchmark.sumLinkedList    thrpt      200   219,538        1,969  ops/us
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PointerChasingBenchmark {
    private static final int PROBLEM_SIZE = 64 * 1024;

    private final int[] array = new int[PROBLEM_SIZE];
    private final List<Integer> linkedList = new LinkedList<>();
    private final List<Integer> arrayList = new ArrayList<>(PROBLEM_SIZE);

    @Setup
    public void setUp() {
        for (int idx = 0; idx < PROBLEM_SIZE; idx++) {
            linkedList.add(idx);
            arrayList.add(idx);
            array[idx] = idx;
        }
    }

    @Benchmark
    @OperationsPerInvocation(PROBLEM_SIZE)
    public long sumLinkedList() {
        long sum = 0;
        for (int val : linkedList) {
            sum += val;
        }
        return sum;
    }

    @Benchmark
    @OperationsPerInvocation(PROBLEM_SIZE)
    public long sumArrayList() {
        long sum = 0;
        for (int val : arrayList) {
            sum += val;
        }
        return sum;
    }

    @Benchmark
    @OperationsPerInvocation(PROBLEM_SIZE)
    public long sumArray() {
        long sum = 0;
        for (int val : array) {
            sum += val;
        }
        return sum;
    }
}
