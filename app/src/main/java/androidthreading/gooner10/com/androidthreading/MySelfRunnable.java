package androidthreading.gooner10.com.androidthreading;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Runnable class
 */

class MySelfRunnable implements Runnable {

    private Context context;
    private static final long DOWNLOAD_TIME = 10 * 1000;
    private String s;

    public MySelfRunnable(MainActivity mainActivity, String s) {
        context = mainActivity;
        this.s = s;
    }

    @Override
    public void run() {
        long endTime = System.currentTimeMillis() + DOWNLOAD_TIME;
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "starting Download for " + s, e);
            }
        }

        // We cannot directly show toast since this runs on a separate thread,
        // hence we try to show toast from UI Thread
        ((MainActivity) context).runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(context, "Download Complete for " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}