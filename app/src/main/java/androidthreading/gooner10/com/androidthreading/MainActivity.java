package androidthreading.gooner10.com.androidthreading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button executorButton = (Button) findViewById(R.id.downloadButton);
        executorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
                startExecutor();
            }
        });

        Button executorServiceButton = (Button) findViewById(R.id.executorService);
        executorServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
                startExecutorService();
            }
        });

        Button scheduledExecutorButton = (Button) findViewById(R.id.scheduledExecutorService);
        scheduledExecutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
                startScheduledExecutorService();
            }
        });
    }

    private void startExecutorService() {

    }

    private void startScheduledExecutorService() {

    }

    private void startExecutor() {
        Executor executor = new DirectExecutor();
        executor.execute(new MyRunnable(this));
    }
}
