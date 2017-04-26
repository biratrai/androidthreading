package androidthreading.gooner10.com.androidthreading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final long DOWNLOAD_TIME = 10 * 1000;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadButton = (Button) findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
                startRunnable();
            }
        });
    }

    /**
     * Method which creates a Runnable and a Thread.
     */
    private void startRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startDownload();
            }
        };
        Thread thread = new Thread(runnable);
        thread.setName("Download Thread");
        thread.start();
    }

    /**
     * Method that mimicks the download which usually takes long time.
     */
    private void startDownload() {
        long endTime = System.currentTimeMillis() + DOWNLOAD_TIME;
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "startDownload: ", e);
            }
        }
        showToast();
    }

    private void showToast() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Download Complete! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
