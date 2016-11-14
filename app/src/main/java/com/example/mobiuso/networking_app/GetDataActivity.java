package com.example.mobiuso.networking_app;

/**
 * Created by mobiuso on 24/10/16.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

public class GetDataActivity extends Activity {


    private ProgressDialog pDialog;
    TextView my_text;
    public static final int progress_bar_type = 0;
    private static String file_url = Constants.CONTENTS_URL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_data);

        my_text = (TextView) findViewById(R.id.textView);

      if (isNetworkAvailable()) {
            new DownloadFileFromURL().execute(file_url);
        } else {
            Toast.makeText(this, getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
        }

    }
    private boolean isNetworkAvailable() {
        boolean available = false;

        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {

            available = true;
        }
        return available;
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage(getResources().getString(R.string.download));
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }



    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }


        @Override
        protected String doInBackground(String... f_url) {
            int count;
            StringBuffer sb = null;
            BufferedReader br = null;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();


                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                System.out.println(Environment.getExternalStorageDirectory().toString());

                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/contents.json");

                // code to download a json file

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

                // code to read a json file
                String ret = "";
            try {
                    InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory().toString() + "/contents.json");

                    if ( inputStream != null ) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (receiveString = bufferedReader.readLine()) != null ) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        ret = stringBuilder.toString();
                       // System.out.println(ret);
                        // code to read a json file

          JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(ret);
                String media_url =jsonObj.getString(Constants.KEY_MEDIA);

               String html_url= jsonObj.getString(Constants.KEY_HTML);

                System.out.println(media_url);
               System.out.println(html_url);
                   DownloadMedia(media_url);
                    DownloadZip(html_url);


            } catch (JSONException e) {
               e.printStackTrace();
            }

                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }
            return getResources().getString(R.string.message);

        }


        protected String DownloadMedia(String... f_url)
        {
            int mediacount;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();


                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/lab1201.mp4");

                // code to download a json file

                byte data[] = new byte[1024];

                long total = 0;

                while ((mediacount = input.read(data)) != -1) {
                    total += mediacount;

                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, mediacount);
                }

                output.close();
                input.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return getResources().getString(R.string.message);
        }

        protected String DownloadZip(String... f_url)
        {
            int mediacount;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();


                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/contents.zip");

                byte data[] = new byte[1024];

                long total = 0;

                while ((mediacount = input.read(data)) != -1) {
                    total += mediacount;

                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, mediacount);
                }

                output.close();
                input.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return getResources().getString(R.string.message);
        }


        protected void onProgressUpdate(String... progress) {

            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            dismissDialog(progress_bar_type);
            my_text.setText(file_url);
        }

    }
}
