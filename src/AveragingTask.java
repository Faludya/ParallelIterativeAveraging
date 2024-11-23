import java.util.concurrent.RecursiveAction;

public class AveragingTask extends RecursiveAction {
    private static final int THRESHOLD = 1000; // Adjust as needed
    private final double[] current, next;
    private final int start, end;

    public AveragingTask(double[] current, double[] next, int start, int end) {
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
            int mid = (start + end) / 2;
            invokeAll(new AveragingTask(current, next, start, mid),
                    new AveragingTask(current, next, mid, end));
        }
    }
}
