import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class DataProcessingSystem {
    private final BlockingQueue<Task> taskQueue;
    private final List<Result> results;
    private final ExecutorService workerPool;
    private final int numWorkers;
    private final String outputFile;

    public DataProcessingSystem(int numWorkers, String outputFile) {
        this.numWorkers = numWorkers;
        this.outputFile = outputFile;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.results = Collections.synchronizedList(new ArrayList<>());
        this.workerPool = Executors.newFixedThreadPool(numWorkers);
    }

    public void start() {
        // Start worker threads
        for (int i = 0; i < numWorkers; i++) {
            workerPool.execute(new Worker(this, i, taskQueue, results));
        }
    }

    public void addTask(Task task) {
        try {
            taskQueue.put(task);
            log("Added task: " + task.getId());
        } catch (Exception e) {
            logError("Error adding task: " + e.getMessage());
        }
    }

    public void shutdown() {
        workerPool.shutdown();
        try {
            if (!workerPool.awaitTermination(60, TimeUnit.SECONDS)) {
                workerPool.shutdownNow();
            }
            saveResultsToFile();
        } catch (InterruptedException e) {
            workerPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void saveResultsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            synchronized (results) {
                for (Result result : results) {
                    writer.println(result.toString());
                }
            }
            log("Results saved to " + outputFile);
        } catch (IOException e) {
            logError("Error saving results: " + e.getMessage());
        }
    }

    // Make these methods static since they don't depend on instance state
    public static void log(String message) {
        System.out.println("[System] " + message);
    }

    public static void logError(String error) {
        System.err.println("[ERROR] " + error);
    }

    private static class Worker implements Runnable {
        private final DataProcessingSystem parent;
        private final int workerId;
        private final BlockingQueue<Task> taskQueue;
        private final List<Result> results;

        public Worker(DataProcessingSystem parent, int workerId, BlockingQueue<Task> taskQueue, List<Result> results) {
            this.parent = parent;
            this.workerId = workerId;
            this.taskQueue = taskQueue;
            this.results = results;
        }

        @Override
        public void run() {
            log("Worker " + workerId + " started");
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Task task = taskQueue.poll(1, TimeUnit.SECONDS);
                    if (task == null) {
                        // No more tasks likely
                        break;
                    }
                    processTask(task);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                log("Worker " + workerId + " shutting down");
            }
        }

        private void processTask(Task task) {
            try {
                log("Worker " + workerId + " processing task " + task.getId());
                // Simulate processing delay
                Thread.sleep((long) (Math.random() * 1000));
                
                Result result = new Result(task.getId(), "Processed by worker " + workerId);
                
                synchronized (results) {
                    results.add(result);
                }
                
                log("Worker " + workerId + " completed task " + task.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logError("Worker " + workerId + " error processing task " + task.getId() + ": " + e.getMessage());
            }
        }

        private void log(String message) {
            System.out.println("[Worker " + workerId + "] " + message);
        }

        private void logError(String error) {
            System.err.println("[Worker " + workerId + " ERROR] " + error);
        }
    }

    // Simple Task and Result classes
    public static class Task {
        private final String id;
        private final String data;

        public Task(String id, String data) {
            this.id = id;
            this.data = data;
        }

        public String getId() { return id; }
        public String getData() { return data; }
    }

    public static class Result {
        private final String taskId;
        private final String processedData;

        public Result(String taskId, String processedData) {
            this.taskId = taskId;
            this.processedData = processedData;
        }

        @Override
        public String toString() {
            return "Task " + taskId + ": " + processedData;
        }
    }

    public static void main(String[] args) {
        DataProcessingSystem system = new DataProcessingSystem(4, "results.txt");
        
        // Add some sample tasks
        for (int i = 0; i < 10; i++) {
            system.addTask(new Task("T" + i, "Data " + i));
        }
        
        system.start();
        
        // Allow time for processing
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        system.shutdown();
    }
}