package com.ochando.superheroapierrores;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText idData;
    TextView getUrl;
    TextView showURL;
    TextView errorMensaje;
    ProgressBar progress;

    public class QuerySuperHero extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {

            URL buscarUrl = urls[0];
            String buscarResultados = null;

            try {
                buscarResultados = InternetSuperHero.openURL(buscarUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buscarResultados;
        }

        protected void onPostExecute(String s) {
            progress.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showProgress();
                getUrl.setText(s);
            } else {
                showErrorMensaje();
            }
            super.onPostExecute(s);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.launch) {
            URL getDatos = null;
            try {
                if (idData.getText().toString().equals("")) {
                    errorMensaje.setVisibility(View.VISIBLE);

                } else {
                    getDatos = InternetSuperHero.getDataID(idData.getText().toString());
                    errorMensaje.setVisibility(View.INVISIBLE);
                    progress.setVisibility(View.VISIBLE);
                    new QuerySuperHero().execute(getDatos);
                    showURL.setText("la Url es :" +  getDatos);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (itemID == R.id.clear) {
            idData.setText("");
            getUrl.setText("");
            showURL.setText("");
            errorMensaje.setVisibility(View.INVISIBLE);

        }
        return  true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idData = (EditText) findViewById(R.id.idData);
        getUrl = (TextView) findViewById(R.id.show_all);
        showURL = (TextView) findViewById(R.id.showURL);
        errorMensaje = (TextView) findViewById(R.id.errorMensaje);
        progress = (ProgressBar) findViewById(R.id.progress);

    }

    public void showSuperHero(View vista) {

        URL getDatos = null;
        try {
            getDatos = InternetSuperHero.getAllURL();
            new QuerySuperHero().execute(getDatos);
            showURL.setText("la Url es :" +  getDatos);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void showProgress() {
        errorMensaje.setVisibility(View.INVISIBLE);
        getUrl.setVisibility(View.VISIBLE);

    }

    private void showErrorMensaje() {
        getUrl.setVisibility(View.INVISIBLE);
        errorMensaje.setVisibility(View.VISIBLE);
    }

}