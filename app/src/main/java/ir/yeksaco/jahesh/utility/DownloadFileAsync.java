package ir.yeksaco.jahesh.utility;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileAsync extends AsyncTask<String, String, String> {

    private static final String TAG = "DownloadFileAsync";
    private String fileUrl; // URL to the file you want to download
    private String localFilePath; // local path where to save the downloaded file
    private ProgressBar progressBar; // progress bar to show the progress

    public DownloadFileAsync(String fileUrl, String localFilePath, ProgressBar progressBar) {
        this.fileUrl = fileUrl;
        this.localFilePath = localFilePath;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null) {
            progressBar.setProgress(0);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        int count;
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lengthOfFile = connection.getContentLength();
            Log.d(TAG, "Length of file: " + lengthOfFile);

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(localFilePath);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                int progress = (int) (total * 100 / lengthOfFile);
                Log.i("jaheshTag", "progress :" + progress);
                publishProgress("" + progress);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            Log.e(TAG, "Error: " + e);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        if (progressBar != null) {
            progressBar.setProgress(Integer.parseInt(progress[0]));
        }
    }
}