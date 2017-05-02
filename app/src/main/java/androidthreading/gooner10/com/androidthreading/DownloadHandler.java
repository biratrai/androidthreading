package androidthreading.gooner10.com.androidthreading;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * A custom Handler that handles the message passed from Threads Handler
 */

public class DownloadHandler extends Handler {
    private static final String TAG = DownloadHandler.class.getSimpleName();

    @Override
    public void handleMessage(Message msg) {
        downloadFile(msg.obj.toString());
    }

    private void downloadFile(String file) {
        long endTime = System.currentTimeMillis() + 10 * 1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                Log.d(TAG, "DownloadFile started for file " + file);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, file + " File downloaded");
    }
}
