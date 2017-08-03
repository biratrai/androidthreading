package androidthreading.gooner10.com.androidthreading;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Executor with a new Thread in which runnable task is run
 */

public class SelfThreadExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable runnable) {
        new Thread(runnable).start();
    }
}
