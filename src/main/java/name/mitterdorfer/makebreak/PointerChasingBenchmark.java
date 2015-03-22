package name.mitterdorfer.makebreak;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Should be run with "-gc true"
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PointerChasingBenchmark {
    @Param({"1024", "2048", "4096", "8192", "16384", "32768"})
    public int problemSize;

    private int[] primitiveArray;
    private Integer[] objectArray;
    private List<Integer> linkedList;
    private List<Integer> arrayList;

    @Setup
    public void setUp() {
        primitiveArray = new int[problemSize];
        objectArray = new Integer[problemSize];
        linkedList = new LinkedList<>();
        arrayList = new ArrayList<>(problemSize);

        // initialize separately to minimize influence of other lists on memory layout. Every structure should
        // allocate space without influence on the memory layout of others
        for (int idx = 0; idx < problemSize; idx++) {
            // explicitly create a new Integer object without using Integer's cache to minimize the
            // risk of introducing additional non-linearity to memory layout.
            linkedList.add(new Integer(idx));
        }
        for (int idx = 0; idx < problemSize; idx++) {
            arrayList.add(new Integer(idx));
        }
        for (int idx = 0; idx < problemSize; idx++) {
            primitiveArray[idx] = idx;
        }
        for (int idx = 0; idx < problemSize; idx++) {
            objectArray[idx] = new Integer(idx);
        }
    }

    @Benchmark
    public int sumLinkedList() {
        int sum = 0;
        for (int val : linkedList) {
            sum += val;
        }
        return sum;
    }

    @Benchmark
    public int sumArrayList() {
        int sum = 0;
        for (int val : arrayList) {
            sum += val;
        }
        return sum;
    }

    @Benchmark
    public int sumPrimitiveArray() {
        int sum = 0;
        for (int val : primitiveArray) {
            sum += val;
        }
        return sum;
    }

    @Benchmark
    public int sumObjectArray() {
        int sum = 0;
        for (int val : objectArray) {
            sum += val;
        }
        return sum;
    }
}
