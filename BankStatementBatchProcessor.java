import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BankStatementBatchProcessor {

    // FIX: AtomicInteger is used because processedCount++ is not thread-safe
    private AtomicInteger processedCount = new AtomicInteger(0);

    public void process(List<StatementRecord> records) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (StatementRecord record : records) {

            executor.submit(() -> {

                processRecord(record);
                // processedCount++;// <-- inconsistent in production

                // FIX: incrementAndGet() performs atomic increment safely
                processedCount.incrementAndGet();
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);
    }

    public int getProcessedCount() {

        // FIX: get() safely returns atomic value
        return processedCount.get();
    }
}