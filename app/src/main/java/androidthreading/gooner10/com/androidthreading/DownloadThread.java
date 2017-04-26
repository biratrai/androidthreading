package androidthreading.gooner10.com.androidthreading;

import android.util.Log;

/**
 * DownloadThread that downloads the thread.
 */

public class DownloadThread extends Thread {
    public static final String TAG = DownloadThread.class.getSimpleName();
    private static final long DOWNLOAD_TIME = 10 * 1000;

    @Override
    public void run() {
        for (String fileName : FileList.files) {
            startDownload(fileName);
        }
    }

    private void startDownload(String fileName) {
        long endTime = System.currentTimeMillis() + DOWNLOAD_TIME;
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "startDownload: ", e);
            }
        }
        Log.i(TAG, fileName + " download complete.");
    }
}
