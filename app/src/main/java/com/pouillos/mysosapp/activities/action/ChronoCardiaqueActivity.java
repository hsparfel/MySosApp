package com.pouillos.mysosapp.activities.action;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.NavDrawerActivity;
import com.pouillos.mysosapp.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class ChronoCardiaqueActivity extends NavDrawerActivity {

    @BindView(R.id.textCompteur)
    TextInputEditText textCompteur;
    @BindView(R.id.layoutCompteur)
    TextInputLayout layoutCompteur;

    @BindView(R.id.textTimer)
    TextInputEditText textTimer;
    @BindView(R.id.layoutTimer)
    TextInputLayout layoutTimer;


    @BindView(R.id.fabStart)
    FloatingActionButton fabStart;
    @BindView(R.id.fabStop)
    FloatingActionButton fabStop;

    @BindView(R.id.imageCompression)
    ImageView imageCompression;
    @BindView(R.id.imageInsufflation)
    ImageView imageInsufflation;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_chrono_cardiaque);

// 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        ButterKnife.bind(this);

        imageCompression.setVisibility(View.GONE);
        imageInsufflation.setVisibility(View.GONE);
        fabStop.setVisibility(View.GONE);
        layoutCompteur.setVisibility(View.GONE);
        layoutTimer.setVisibility(View.GONE);
        //MorseActivity.AsyncTaskRunnerBD runnerBD = new MorseActivity.AsyncTaskRunnerBD();
     //   runnerBD.execute();
    }



    private class AsyncTaskRunnerTimer extends AsyncTask<Void, String, Void> {

        protected Void doInBackground(Void...voids) {
            Date date = new Date();
            date = DateUtils.ajouterJourArrondi(date,0,0);
            String time = DateUtils.ecrireHeureChrono(date);
            while (isRunning) {
                publishProgress(time);
                try {
                    Thread.sleep(1000);
                    date = DateUtils.ajouterSeconde(date,1);
                    time = DateUtils.ecrireHeureChrono(date);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
           // publishProgress(0);
           // publishProgress(20);
            //envoyerMessageMorse(strings[0]);
           // publishProgress(90);
          //  publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(ChronoCardiaqueActivity.this, "fini", Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(String... string) {
            //progressBar.setProgress(integer[0],true);
            textTimer.setText(string[0]);

        }
    }

    private class AsyncTaskRunnerMassage extends AsyncTask<Void, String, Void> {

        protected Void doInBackground(Void...voids) {
          //  Date date = new Date();
          //  date = DateUtils.ajouterJourArrondi(date,0,0);
          //  String time = DateUtils.ecrireHeureChrono(date);
            int compteur = 0;
            publishProgress(compteur+" / 30","","");

            while (isRunning) {
                try {
                    Thread.sleep(100);
                    publishProgress(compteur+" / 30","CompressionVisible","");
                   // imageCompression.setVisibility(View.VISIBLE);
                    Thread.sleep(500);
                    publishProgress(compteur+" / 30","CompressionGone","");
                  //  imageCompression.setVisibility(View.GONE);
                    compteur++;
                    publishProgress(compteur+" / 30","","");
                    if (compteur == 30) {
                        Thread.sleep(800);
                        publishProgress("0 / 2","","InsufflationVisible");
                        //imageInsufflation.setVisibility(View.VISIBLE);
                        Thread.sleep(1000);
                        //publishProgress("1 / 2");
                        publishProgress("1 / 2","","InsufflationGone");
                        //imageInsufflation.setVisibility(View.GONE);
                        Thread.sleep(800);
                        publishProgress("2 / 2","","InsufflationVisible");
                        //imageInsufflation.setVisibility(View.VISIBLE);
                        Thread.sleep(1000);
                        publishProgress("2 / 2","","InsufflationGone");
                        //imageInsufflation.setVisibility(View.GONE);
                        compteur = 0;
                        publishProgress(compteur+" / 30","","");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Void result) {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(ChronoCardiaqueActivity.this, "fini", Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(String... string) {
            //progressBar.setProgress(integer[0],true);
            textCompteur.setText(string[0]);
            if (string[1].equalsIgnoreCase("CompressionVisible")) {
                imageCompression.setVisibility(View.VISIBLE);
            } else if (string[1].equalsIgnoreCase("CompressionGone")) {
                imageCompression.setVisibility(View.GONE);
            } else if (string[2].equalsIgnoreCase("InsufflationVisible")) {
                imageInsufflation.setVisibility(View.VISIBLE);
            } else if (string[2].equalsIgnoreCase("InsufflationGone")) {
                imageInsufflation.setVisibility(View.GONE);
            }

        }
    }

    @OnClick(R.id.fabStart)
    public void setFabStartClick() {
        fabStart.setVisibility(View.GONE);
        fabStop.setVisibility(View.VISIBLE);
        layoutTimer.setVisibility(View.VISIBLE);
        layoutCompteur.setVisibility(View.VISIBLE);
        isRunning = true;
        AsyncTaskRunnerTimer runnerTimer = new AsyncTaskRunnerTimer();
        //runnerTimer.execute();
        runnerTimer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        AsyncTaskRunnerMassage runnerMassage = new AsyncTaskRunnerMassage();
       // runnerMassage.execute();
        runnerMassage.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    @OnClick(R.id.fabStop)
    public void setFabStopClick() {
        isRunning = false;
        fabStart.setVisibility(View.VISIBLE);
        fabStop.setVisibility(View.GONE);
        layoutTimer.setVisibility(View.GONE);
        layoutCompteur.setVisibility(View.GONE);
        //ChronoCardiaqueActivity.AsyncTaskRunnerMorse runnerMorse = new ChronoCardiaqueActivity.AsyncTaskRunnerMorse();
        //runnerMorse.execute("sos");
    }
}
