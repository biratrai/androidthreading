package androidthreading.gooner10.com.androidthreading;

import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceActivity extends AppCompatActivity {

    private static final String TAG = ExecutorServiceActivity.class.getSimpleName();
    private static final long DOWNLOAD_TIME = 10 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button runnableButton = (Button) findViewById(R.id.runnable_executor_service);
        runnableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExecutorServiceActivity.this, "Downloading with Runnable Executor Service", Toast.LENGTH_SHORT).show();
                startRunnableExecutorService("Runnable Executor Service");
                int id = Process.getThreadPriority(android.os.Process.myTid());
                long curentThread = Thread.currentThread().getId();
                Log.d(TAG, "onClick: " + id + " " + curentThread);
            }
        });

        Button callableButton = (Button) findViewById(R.id.callable_executor_service);
        callableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExecutorServiceActivity.this, "Downloading with Callable Executor Service", Toast.LENGTH_SHORT).show();
                startCallableExecutorService("Callable Executor Service");
            }
        });

        Button threadPoolButton = (Button) findViewById(R.id.threadpool_executor_service);
        threadPoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExecutorServiceActivity.this, "Downloading with ThreadPool Executor Service", Toast.LENGTH_SHORT).show();
                startThreadPoolExecutorService("ThreadPool Executor Service");
            }
        });
    }

    /**
     * The java.util.concurrent.ThreadPoolExecutor is an implementation of the ExecutorService interface.
     * The ThreadPoolExecutor executes the given task (Callable or Runnable) using one of its internally pooled threads.
     * The number of threads in the pool is determined by these variables:
     * - corePoolSize
     * - maximumPoolSize
     * <p>
     * If less than corePoolSize threads are created in the the thread pool when a task is delegated to the thread pool,
     * then a new thread is created, even if idle threads exist in the pool.
     * If the internal queue of tasks is full, and corePoolSize threads or more are running, but less than maximumPoolSize
     * threads are running, then a new thread is created to execute the task.
     *
     * @param s
     */
    private void startThreadPoolExecutorService(final String s) {
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 5000;

        ExecutorService threadPoolExecutor =
                new ThreadPoolExecutor(
                        corePoolSize,
                        maxPoolSize,
                        keepAliveTime,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>()
                );
        threadPoolExecutor.submit(getRunnable(s));
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        threadPoolExecutor.shutdown();
        System.out.println("Finished all threads");
    }

    private void startCallableExecutorService(final String s) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                simulateDownload(s);
                return "from future";
            }
        });
        try {
            showToast(future.get().toString());
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "startCallableExecutorService: ", e);
        }
        executorService.shutdown();
    }

    private void startRunnableExecutorService(final String s) {
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        executorService.submit(getRunnable(s));
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executorService.shutdown();
        System.out.println("Finished all threads");
    }

    @NonNull
    private Runnable getRunnable(final String s) {
        return new Runnable() {
            @Override
            public void run() {
                simulateDownload(s);
            }
        };
    }

    private void simulateDownload(String s) {
        long endTime = System.currentTimeMillis() + DOWNLOAD_TIME;
        while (System.currentTimeMillis() < endTime) {
            try {
                Log.d(TAG, "starting Download for " + s);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "run: " + e.getMessage());
            }
        }

        int id = Process.getThreadPriority(Process.myTid());
        long curentThread = Thread.currentThread().getId();
        Log.d(TAG, "onClick: " + id + " " + curentThread);
        // We can directly show toast since this runs on main UI Thread
        showToast("Executor Service");
    }

    private void showToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ExecutorServiceActivity.this, "Download Complete for " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
