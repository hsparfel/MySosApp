package com.pouillos.mysosapp.activities.action;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.AccueilActivity;
import com.pouillos.mysosapp.activities.NavDrawerActivity;
import com.pouillos.mysosapp.activities.afficher.AfficherListeContactActivity;
import com.pouillos.mysosapp.activities.afficher.AfficherUtilisateurEtapeDeuxActivity;
import com.pouillos.mysosapp.activities.afficher.AfficherUtilisateurEtapeUnActivity;
import com.pouillos.mysosapp.entities.Contact;
import com.pouillos.mysosapp.entities.LettreMorse;
import com.pouillos.mysosapp.entities.Parametres;
import com.pouillos.mysosapp.entities.TempoMorse;
import com.pouillos.mysosapp.entities.Utilisateur;
import com.pouillos.mysosapp.utils.DateUtils;

import java.text.Normalizer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class MorseActivity extends NavDrawerActivity {

    @BindView(R.id.textMessage)
    TextInputEditText textMessage;
    @BindView(R.id.layoutMessage)
    TextInputLayout layoutMessage;


    @BindView(R.id.fabSos)
    FloatingActionButton fabSos;

    @BindView(R.id.fabMessage)
    FloatingActionButton fabMessage;

    TempoMorse tempoMorse;
    List<LettreMorse> listLettreMorse;
    Map<String,String> mapLettreMorse;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    private final int CAMERA_REQUEST_CODE=2;
    boolean hasCameraFlash = false;
    private boolean isFlashOn=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_morse);

// 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        ButterKnife.bind(this);

        tempoMorse = recupererTempoMorse();
        listLettreMorse = lettreMorseDao.loadAll();
        mapLettreMorse = new HashMap<>();

        for (LettreMorse lettreMorse : listLettreMorse) {
            String traduction = "";
            if (lettreMorse.getPremierCaractere() != null) {
                traduction += lettreMorse.getPremierCaractere();
            }
            if (lettreMorse.getDeuxiemeCaractere() != null) {
                traduction += lettreMorse.getDeuxiemeCaractere();
            }
            if (lettreMorse.getTroisiemeCaractere() != null) {
                traduction += lettreMorse.getTroisiemeCaractere();
            }
            if (lettreMorse.getQuatriemeCaractere() != null) {
                traduction += lettreMorse.getQuatriemeCaractere();
            }
            if (lettreMorse.getCinquiemeCaractere() != null) {
                traduction += lettreMorse.getCinquiemeCaractere();
            }
            if (lettreMorse.getSixiemeCaractere() != null) {
                traduction += lettreMorse.getSixiemeCaractere();
            }
            traduction += "¤";
            mapLettreMorse.put(lettreMorse.getLettreAlphabet(),traduction);
        }
        mapLettreMorse.put(" ","§");

        hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        //MorseActivity.AsyncTaskRunnerBD runnerBD = new MorseActivity.AsyncTaskRunnerBD();
     //   runnerBD.execute();
    }

    private String verifierCompositionMessage(String message) {
        String messageCorrige = "";
       /* messageCorrige = message.replaceAll("À","A");
        messageCorrige = message.replaceAll("Á","A");
        messageCorrige = message.replaceAll("Â","A");
        messageCorrige = message.replaceAll("Ã","A");
        messageCorrige = message.replaceAll("Ä","A");
        messageCorrige = message.replaceAll("È","E");
        messageCorrige = message.replaceAll("É","E");
        messageCorrige = message.replaceAll("Ê","E");
        messageCorrige = message.replaceAll("Ë","E");
        messageCorrige = message.replaceAll("Ì","I");
        messageCorrige = message.replaceAll("Í","I");
        messageCorrige = message.replaceAll("Î","I");
        messageCorrige = message.replaceAll("Ï","I");
        messageCorrige = message.replaceAll("Ò","O");
        messageCorrige = message.replaceAll("Ó","O");
        messageCorrige = message.replaceAll("Ô","O");
        messageCorrige = message.replaceAll("Ö","O");
        messageCorrige = message.replaceAll("Ù","U");
        messageCorrige = message.replaceAll("Ú","U");
        messageCorrige = message.replaceAll("Û","U");
        messageCorrige = message.replaceAll("Ü","U");
        messageCorrige = message.replaceAll("Ç","C");*/
        messageCorrige = Normalizer.normalize(message, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
        return messageCorrige;
    }

    private void envoyerMessageMorse(String message) {
        String messageMorse = "{";
        messageMorse += message.toUpperCase();
        messageMorse += "}";
        messageMorse = verifierCompositionMessage(messageMorse);
        int nbCaractere = messageMorse.length();
        String currentCaractere;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            // We Dont have permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);

        }else {
            String messageTraduit = "";
            for (int i=0;i<nbCaractere;i++){
                if (i<nbCaractere-1) {
                    currentCaractere = messageMorse.substring(i,i+1);
                } else {
                    currentCaractere = messageMorse.substring(i);
                }
                if (mapLettreMorse.get(currentCaractere) != null) {
                    messageTraduit += mapLettreMorse.get(currentCaractere);
                } else {
                    messageTraduit += mapLettreMorse.get(" ");
                }

            }
            int nbCaract = messageTraduit.length();
            for (int i=0;i<nbCaract;i++) {
                if (i<nbCaract-1) {
                    currentCaractere = messageTraduit.substring(i,i+1);
                } else {
                    currentCaractere = messageTraduit.substring(i);
                }
                switch(currentCaractere) {
                    case ".":
                        try {
                            flashLightOn();
                            Thread.sleep(tempoMorse.getPoint());
                            flashLightOff();
                            Thread.sleep(tempoMorse.getIntervalSigne());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "-":
                        try {
                            flashLightOn();
                            Thread.sleep(tempoMorse.getTiret());
                            flashLightOff();
                            Thread.sleep(tempoMorse.getIntervalSigne());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "¤":
                        try {
                            Thread.sleep(tempoMorse.getIntervalLettre());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "§":
                        try {
                            Thread.sleep(tempoMorse.getIntervalMot());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
        }
    }

    private class AsyncTaskRunnerMorse extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String...strings) {

            publishProgress(0);
            publishProgress(20);
            envoyerMessageMorse(strings[0]);
            publishProgress(90);
            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    private boolean isFullRempli() {
        boolean bool = true;
        if (!isFilled(textMessage)) {
            textMessage.setError("Obligatoire");
            bool = false;
        }
        return bool;
    }

    @OnClick(R.id.fabMessage)
    public void setFabMessageClick() {
        if (isFullRempli()) {
            progressBar.setVisibility(View.VISIBLE);
            MorseActivity.AsyncTaskRunnerMorse runnerMorse = new MorseActivity.AsyncTaskRunnerMorse();
            runnerMorse.execute(textMessage.getText().toString());
        }
    }

    @OnClick(R.id.fabSos)
    public void setFabSosClick() {
        progressBar.setVisibility(View.VISIBLE);
        MorseActivity.AsyncTaskRunnerMorse runnerMorse = new MorseActivity.AsyncTaskRunnerMorse();
        runnerMorse.execute("sos");

    }
}
