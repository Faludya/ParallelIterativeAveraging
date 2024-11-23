import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class PairwiseAveragingTask extends RecursiveAction {
    private static final int THRESHOLD = 1000; // Minimum size of the array to split
    private final double[] current, next;
    private final int start, end;

    // Constructor for the task
    public PairwiseAveragingTask(double[] current, double[] next, int start, int end) {
        this.current = current;
        this.next = next;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        // If the section size is below the threshold, process sequentially
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                next[i] = (current[i - 1] + current[i] + current[i + 1]) / 3.0;
            }
        } else {
            // Split the task into two smaller tasks
            int mid = (start + end) / 2;
            invokeAll(
                    new PairwiseAveragingTask(current, next, start, mid),
                    new PairwiseAveragingTask(current, next, mid, end)
            );
        }
    }

    // Public method to perform the averaging using Fork/Join
    public static double[] parallelAveraging(double[] array, int iterations) {
        int n = array.length;
        double[] current = array.clone();
        double[] next = new double[n];
        ForkJoinPool pool = new ForkJoinPool(); // Create the ForkJoinPool

        for (int it = 0; it < iterations; it++) {
            // Submit the task to the pool
            pool.invoke(new PairwiseAveragingTask(current, next, 1, n - 1));
            System.arraycopy(next, 0, current, 0, n); // Copy results
        }

        pool.shutdown(); // Clean up the pool
        return current;
    }
}
