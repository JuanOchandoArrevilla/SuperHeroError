package com.ochando.superheroapierrores;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class InternetSuperHero {

    final static String baseURL = "https://akabab.github.io/superhero-api/api";
    final static String allData= "all.json";
    final static String ulrId = "id/";
    final static String ulrJson = ".json";

    public static URL getAllURL() throws MalformedURLException {
        Uri urlALL = Uri.withAppendedPath(Uri.parse(baseURL), allData).buildUpon().build();
        URL url = null;
        url = new URL(urlALL.toString());
        return  url;
    };

    public static URL getDataID(String id) throws MalformedURLException {
        Uri urlId = Uri.withAppendedPath(Uri.parse(baseURL), ulrId + id + ulrJson).buildUpon().build();
        URL url = null;
        url = new URL(urlId.toString());
        return url;
    }

    public static String openURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        InputStream entrada = connection.getInputStream();

        Scanner sc = new Scanner(entrada);
        sc.useDelimiter("\\A");

        try {
            boolean hasInput = sc.hasNext();
            if (hasInput) {
                return sc.next();
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }

    }


}
