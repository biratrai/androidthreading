package androidthreading.gooner10.com.androidthreading;

import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * Serial Executor that implements Executor interface and can run the tasks on different thread
 */

public class SerialExecutor implements Executor {
    private final Queue<Runnable> tasks = new ArrayDeque<>(); // Double ended queue holds all submitted tasks until they are processed
    private final Executor executor;
    private Runnable active;

    SerialExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public synchronized void execute(@NonNull final Runnable r) {
        tasks.offer(new Runnable() {
            public void run() {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            }
        });
        if (active == null) {
            scheduleNext();
        }
    }

    /**
     * Called when r.run() has finished it's task
     */
    protected synchronized void scheduleNext() {
        if ((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }
}
