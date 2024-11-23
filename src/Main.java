public class Main {
    public static void main(String[] args) {
        double[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int iterations = 5;

        // Sequential implementation
        double[] sequentialResult = IterativeAveraging.sequentialAveraging(array, iterations);
        System.out.println("Sequential Result: " + java.util.Arrays.toString(sequentialResult));

        // Parallel Pairwise Averaging
        double[] pairwiseResult = PairwiseAveragingTask.parallelAveraging(array, iterations);
        System.out.println("Pairwise Parallel Result: " + java.util.Arrays.toString(pairwiseResult));

        // Parallel Successive Divisions
        double[] successiveResult = SuccessiveDivisionsTask.parallelAveraging(array, iterations);
        System.out.println("Successive Parallel Result: " + java.util.Arrays.toString(successiveResult));
    }
}
