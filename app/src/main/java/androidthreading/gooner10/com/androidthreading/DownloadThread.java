package androidthreading.gooner10.com.androidthreading;

import android.os.Looper;

/**
 * Download Thread class
 */

public class DownloadThread extends Thread {
    public static final String TAG = DownloadThread.class.getSimpleName();
    public DownloadHandler downloadHandler;

    @Override
    public void run() {
        Looper.prepare(); // Creates looper and message-queue
        downloadHandler = new DownloadHandler();
        Looper.loop(); // Start looping over the message-queue
    }
}
