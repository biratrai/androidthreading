package androidthreading.gooner10.com.androidthreading;

import android.os.Bundle;
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
    }

    private void startCallableExecutorService(final String s) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                long endTime = System.currentTimeMillis() + DOWNLOAD_TIME;
                while (System.currentTimeMillis() < endTime) {
                    try {
                        Log.d(TAG, "starting Download for " + s);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: " + e.getMessage());
                    }
                }
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
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + DOWNLOAD_TIME;
                while (System.currentTimeMillis() < endTime) {
                    try {
                        Log.d(TAG, "starting Download for " + s);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: " + e.getMessage());
                    }
                }
                // We can directly show toast since this runs on main UI Thread
                showToast("Executor Service");
            }
        });
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executorService.shutdown();
        // Wait until all threads are finish
//        executorService.awaitTermination();
        System.out.println("Finished all threads");
    }

    private void showToast(String s) {
        Toast.makeText(ExecutorServiceActivity.this, "Download Complete for " + s, Toast.LENGTH_SHORT).show();
    }

}
