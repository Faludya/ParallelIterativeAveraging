import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class SuccessiveDivisionsTask extends RecursiveAction {
    private static final int THRESHOLD = 500; // Minimum size of a chunk
    private final double[] current, next;
    private final int start, end;

    public SuccessiveDivisionsTask(double[] current, double[] next, int start, int end) {
        this.current = current;
        this.next = next;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                next[i] = (current[i - 1] + current[i] + current[i + 1]) / 3.0;
            }
        } else {
            // Divide the array into three parts dynamically
            int mid1 = start + (end - start) / 3;
            int mid2 = start + 2 * (end - start) / 3;
            invokeAll(
                    new SuccessiveDivisionsTask(current, next, start, mid1),
                    new SuccessiveDivisionsTask(current, next, mid1, mid2),
                    new SuccessiveDivisionsTask(current, next, mid2, end)
            );
        }
    }

    public static double[] parallelAveraging(double[] array, int iterations) {
        int n = array.length;
        double[] current = array.clone();
        double[] next = new double[n];
        ForkJoinPool pool = new ForkJoinPool();

        for (int it = 0; it < iterations; it++) {
            pool.invoke(new SuccessiveDivisionsTask(current, next, 1, n - 1));
            System.arraycopy(next, 0, current, 0, n);
        }

        pool.shutdown();
        return current;
    }
}
