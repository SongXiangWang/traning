package tw.edu.itu.traning;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by kaden on 2016/5/17.
 */
public class APIUtility extends AsyncTask {
    String apiPath = "http://Server/MduAPIs/api/";
    private int statusCode = 0;
    private OnGetResultListener onGetResultListener;

    public interface OnGetResultListener {
        void onGetResult(int sCode, Object result);
    }

    public void SetOnGetResultListener(OnGetResultListener listener) {
        onGetResultListener = listener;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (onGetResultListener != null) onGetResultListener.onGetResult(statusCode,o);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        URL url = null;
        try {
            url = new URL(apiPath + params[1]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(params[0].toString());
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            Object result = null;
            try {
                statusCode = connection.getResponseCode();
                if (statusCode == 200) {
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    result = readStream(in);
                }
            } finally {
                connection.disconnect();
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("myApp",e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("myApp",e.getMessage());
        }
        return null;
    }

    private ByteArrayOutputStream readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo;
        } catch (IOException e) {
            return null;
        }
    }
}
