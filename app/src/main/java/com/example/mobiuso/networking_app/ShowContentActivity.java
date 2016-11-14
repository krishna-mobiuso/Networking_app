package com.example.mobiuso.networking_app;

/**
 * Created by mobiuso on 24/10/16.
 */

import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by krishna m simzia on 10/14/2016.
 */
public class ShowContentActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_content);

      // code to Unzip the file
        InputStream is;
        ZipInputStream zis;
        try {
            is = new FileInputStream(Constants.ZIP_URL);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                FileOutputStream fout = new FileOutputStream(Constants.UNZIP_URL);

                // reading and writing
                while ((count = zis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                    byte[] bytes = baos.toByteArray();
                    fout.write(bytes);

                    baos.reset();
                    baos.toString();

                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //code to display in webview

        WebView lWebView = (WebView)findViewById(R.id.webview);
        lWebView.loadUrl("file:///"+ Environment.getExternalStorageDirectory().getAbsolutePath() +"/Unzip.html");
    }

    }

