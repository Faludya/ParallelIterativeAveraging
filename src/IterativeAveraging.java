import java.util.concurrent.ForkJoinPool;

public class IterativeAveraging {

    // Sequential implementation of iterative averaging
    public static double[] sequentialAveraging(double[] array, int iterations) {
        int n = array.length;

        // Cloning the array to avoid modifying the original input
        double[] current = array.clone();
        double[] next = new double[n];

        // Perform the averaging for the specified number of iterations
        for (int it = 0; it < iterations; it++) {
            // Loop over the array, skipping the first and last elements
            for (int i = 1; i < n - 1; i++) {
                // Calculate the average of the current element and its neighbors
                next[i] = (current[i - 1] + current[i] + current[i + 1]) / 3.0;
            }

            // Copy the updated values into the original array for the next iteration
            System.arraycopy(next, 0, current, 0, n);
        }

        // Return the final averaged array
        return current;
    }
}
