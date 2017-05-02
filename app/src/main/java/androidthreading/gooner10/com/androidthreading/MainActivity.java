package androidthreading.gooner10.com.androidthreading;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final long DOWNLOAD_TIME = 10 * 1000;
    private Button downloadButton;
    private DownloadThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createThread();

        downloadButton = (Button) findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageToHandler();
            }
        });
    }

    /**
     * Method instantiation
     */
    private void createThread() {
        thread = new DownloadThread();
        thread.setName("DownloadThread");
        thread.start();
    }

    /**
     * Method that sends message to handler via thread
     */
    private void sendMessageToHandler() {
        for (String file : FileList.files()) {
            Message message = Message.obtain(); // Get message instance from global pool for avoiding allocating new objects and for re-use
            message.obj = file;
            thread.downloadHandler.sendMessage(message); // Sending message to handler for adding to message-queue
            Log.d(TAG, "sendMessageToHandler for file " + file);
        }
    }
}
