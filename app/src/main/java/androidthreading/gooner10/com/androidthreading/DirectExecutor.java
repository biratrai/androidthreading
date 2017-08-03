package androidthreading.gooner10.com.androidthreading;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * An Executor class that directly runs the run in the calling thread
 */

public class DirectExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable runnable) {
        runnable.run();
    }
}
