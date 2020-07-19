package com.pouillos.mysosapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.afficher.AfficherListeContactActivity;
import com.pouillos.mysosapp.activities.afficher.AfficherUtilisateurEtapeUnActivity;
import com.pouillos.mysosapp.dao.ContactDao;
import com.pouillos.mysosapp.dao.SmsEnlevementDao;
import com.pouillos.mysosapp.entities.Contact;
import com.pouillos.mysosapp.entities.LettreMorse;
import com.pouillos.mysosapp.entities.Parametres;
import com.pouillos.mysosapp.entities.SmsAccident;
import com.pouillos.mysosapp.entities.SmsEnlevement;
import com.pouillos.mysosapp.entities.TempoMorse;
import com.pouillos.mysosapp.entities.Utilisateur;
import com.pouillos.mysosapp.enumeration.GroupeSanguin;
import com.pouillos.mysosapp.enumeration.SigneMorse;
import com.pouillos.mysosapp.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AccueilActivity extends NavDrawerActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textEnlevement)
    TextView textEnlevement;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.fabAppelNumeroEurope)
    ExtendedFloatingActionButton fabAppelNumeroEurope;
    @BindView(R.id.fabAppelNumeroPompier)
    ExtendedFloatingActionButton fabAppelNumeroPompier;
    @BindView(R.id.fabAppelNumeroPolice)
    ExtendedFloatingActionButton fabAppelNumeroPolice;
    @BindView(R.id.fabAppelNumeroSamu)
    ExtendedFloatingActionButton fabAppelNumeroSamu;
    @BindView(R.id.fabAppelNumeroAeronautique)
    ExtendedFloatingActionButton fabAppelNumeroAeronautique;
    @BindView(R.id.fabAppelNumeroMer)
    ExtendedFloatingActionButton fabAppelNumeroMer;
    @BindView(R.id.fabAppelNumeroSourd)
    ExtendedFloatingActionButton fabAppelNumeroSourd;

    @BindView(R.id.fabSmsAccident)
    ExtendedFloatingActionButton fabSmsAccident;
    @BindView(R.id.fabSmsEnlevement)
    ExtendedFloatingActionButton fabSmsEnlevement;
/*
    //private DaoSession daoSession;
    private UtilisateurDao utilisateurDao;
    private LettreMorseDao lettreMorseDao;
    private TempoMorseDao tempoMorseDao;
    private ContactDao contactDao;
    private ContactAccidentDao contactAccidentDao;
    private ContactEnlevementDao contactEnlevementDao;
    private ParametresDao parametresDao;*/

    private LocationManager locationManager;
   // private LocationListener locationListener;
    double actualLatitude;
    double actualLongitude;
    @BindView(R.id.textLatitude)
    TextInputEditText textLatitude;
    @BindView(R.id.layoutLatitude)
    TextInputLayout layoutLatitude;
    @BindView(R.id.textLongitude)
    TextInputEditText textLongitude;
    @BindView(R.id.layoutLongitude)
    TextInputLayout layoutLongitude;

    @BindView(R.id.textAddresseGeo)
    TextInputEditText textAddresseGeo;
    @BindView(R.id.layoutAddresseGeo)
    TextInputLayout layoutAddresseGeo;

    private boolean enlevementActif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Stetho.initializeWithDefaults(this);


        //todo ajouter verif utilisateur null


        //if (utilisateurDao.count() !=0) {
        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        ButterKnife.bind(this);

        activeUser = findActiveUser();
        enlevementActif=false;
        textView.setText(DateUtils.ecrireDateLettre(new Date()));

        progressBar.setVisibility(View.VISIBLE);
        AccueilActivity.AsyncTaskRunnerBD runnerBD = new AccueilActivity.AsyncTaskRunnerBD();
        runnerBD.execute();

        //  } else {
        //     ouvrirActiviteSuivante(AccueilActivity.this, AfficherUtilisateurEtapeUnActivity.class,true);
        // }

        //faut-il bouler pou actualiser ?


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
            actualLatitude = location.getLatitude();
            actualLongitude = location.getLongitude();
            textLatitude.setText("" + actualLatitude);
            textLongitude.setText("" + actualLongitude);
            convertirCoordonneesToAdresse();
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 10, new LocationListener() {

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }

                @Override
                public void onLocationChanged(Location location) {
                    Log.d("Location Changes", location.toString());
                    actualLatitude = location.getLatitude();
                    actualLongitude = location.getLongitude();
                    textLatitude.setText("" + actualLatitude);
                    textLongitude.setText("" + actualLongitude);
                    convertirCoordonneesToAdresse();
                    Log.d("GPS", "Latitude " + location.getLatitude() + " et longitude " + location.getLongitude());
                }
            });




        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }



        ////////////////////////////
        /*locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location Changes", location.toString());
                actualLatitude = location.getLatitude();
                actualLongitude = location.getLongitude();
                textLatitude.setText("" + actualLatitude);
                textLongitude.setText("" + actualLongitude);
                convertirCoordonneesToAdresse();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status Changed", String.valueOf(status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider Enabled", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider Disabled", provider);
            }
        };*/

     /*   locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/

        /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000,
                10, locationListener);*/
        //afficherPosition();
    }

    //final Looper looper = null;


    public void convertirCoordonneesToAdresse() {
        Geocoder coder = new Geocoder(AccueilActivity.this);
        try {
            List<Address> listAddress = coder.getFromLocation(actualLatitude,actualLongitude,1);
            if (listAddress.size()>0) {
                //textAddresseGeo.setText(listAddress.get(0).toString());
                textAddresseGeo.setText(listAddress.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            textAddresseGeo.setText("Erreur - non trouvé");
            e.printStackTrace();
        }

    }


    /*public void afficherPosition() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            locationManager.requestSingleUpdate(criteria, locationListener, looper);

            Location localisation = locationManager.getLastKnownLocation(provider.getName());
            if (localisation != null) {
                actualLatitude = localisation.getLatitude();
                actualLongitude = localisation.getLongitude();
            }

        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }*/




    @OnClick(R.id.fabSmsAccident)
    public void setfabSmsAccidentClick() {
        List<Contact> listContact = contactDao.queryBuilder()
                .where(ContactDao.Properties.IsContactAccident.eq(true))
                .list();
        String message = "Alerte Accident - "+activeUser.getPrenom()+" "+activeUser.getNom();
        String newLine = System.getProperty("line.separator");
        message += newLine;
        message += recupererParametres().getSmsAccident();
        message += newLine;
        message += textAddresseGeo.getText().toString();
        message += newLine;
        message += "Lat: "+ textLatitude.getText().toString();
        message += newLine;
        message += "Long: "+ textLongitude.getText().toString();
        message += newLine;
        message += DateUtils.ecrireDateHeure(new Date());

        for (Contact contact : listContact) {
            envoyerSms(contact,message);
        }
    }

    @OnClick(R.id.fabSmsEnlevement)
    public void setfabSmsEnlevementClick()  {
        enlevementActif= !enlevementActif;
        if (enlevementActif) {
            textEnlevement.setText("Alerte en cours");
            //todo le toas ne fct pas no plus à cause de la methode qui ne se termine pas je pense
            Toast.makeText(this, "activation enlevement", Toast.LENGTH_LONG).show();
            //todo ça ne marche pas la couleur - ajouter un champ qui indique que c'est actif ou non
            fabSmsEnlevement.getBackground().mutate().setTint(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        } else {
            textEnlevement.setText("Alerte stoppée");
            Toast.makeText(this, "desactivation enlevement", Toast.LENGTH_LONG).show();
            fabSmsEnlevement.getBackground().mutate().setTint(ContextCompat.getColor(this, R.color.colorAccent));
        }
        //todo revoir comment l'interrompre proprement - thread à part ?
        while (enlevementActif) {
            List<Contact> listContact = contactDao.queryBuilder()
                    .where(ContactDao.Properties.IsContactEnlevement.eq(true))
                    .list();
            String message = "Alerte Enlevement - " + activeUser.getPrenom() + " " + activeUser.getNom();
            String newLine = System.getProperty("line.separator");
            message += newLine;
            message += recupererParametres().getSmsEnlevement();
            message += newLine;
            message += textAddresseGeo.getText().toString();
            message += newLine;
            message += "Lat: "+ textLatitude.getText().toString();
            message += newLine;
            message += "Long: "+ textLongitude.getText().toString();
            message += newLine;
            message += DateUtils.ecrireDateHeure(new Date());

            for (Contact contact : listContact) {
                envoyerSms(contact, message);
            }
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





    private class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(10);
            remplirBDDefaut();
            publishProgress(50);
            remplirBDExemple();
            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AccueilActivity.this, R.string.text_DB_created, Toast.LENGTH_LONG).show();
            activeUser = findActiveUser();
            if (activeUser == null) {
                ouvrirActiviteSuivante(AccueilActivity.this,AfficherUtilisateurEtapeUnActivity.class,true);
            }
            List<Contact> listContact = contactDao.loadAll();
            if (listContact.size()== 0) {
                ouvrirActiviteSuivante(AccueilActivity.this, AfficherListeContactActivity.class,true);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    private void remplirBDDefaut() {
        //todo
        remplirBDLettreMorse();
        remplirBDTempoMorse();
        remplirBDParametres();

    }

    private void remplirBDParametres() {
        if (parametresDao.count() == 0) {
            Parametres parametres = new Parametres();
            parametres.setNumeroEurope("0603506694");
            parametres.setNumeroPolice("0603506694");
            parametres.setNumeroPompier("0603506694");
            parametres.setNumeroSamu("0603506694");
            parametres.setNumeroAeronautique("0603506694");
            parametres.setNumeroMer("0603506694");
            parametres.setNumeroSourd("0603506694");
            parametres.setSmsAccident("sms accident");
            parametres.setSmsEnlevement("sms enlevement");
            parametres.setSmsInformationAccidentDebut("sms information accident debut");
            parametres.setSmsInformationEnlevementDebut("sms information enlevement debut");
            parametres.setSmsInformationAccidentFin("sms information accident fin");
            parametres.setSmsInformationEnlevementFin("sms information enlevement fin");
            parametresDao.insert(parametres);

        }
    }

    private void remplirBDTempoMorse() {
        if (tempoMorseDao.count() == 0) {
            long duree = 220; //en milliseconde verifier
            TempoMorse tempoMorse = new TempoMorse();
            tempoMorse.setPoint(duree);
            tempoMorse.setTiret(duree*3);
            tempoMorse.setIntervalSigne(duree*2);
            tempoMorse.setIntervalLettre(duree*3);
            tempoMorse.setIntervalMot(duree*7);
            tempoMorseDao.insert(tempoMorse);
        }
    }

    private void remplirBDLettreMorse() {
        if (lettreMorseDao.count() == 0) {
            // A
            LettreMorse lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("A");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(null);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // B
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("B");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // C
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("C");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // D
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("D");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // E
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("E");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(null);
            lettreMorse.setTroisiemeCaractere(null);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // F
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("F");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // G
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("G");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // H
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("H");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // I
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("I");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(null);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // J
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("J");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // K
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("K");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // L
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("L");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // M
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("M");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(null);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // N
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("N");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(null);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // O
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("O");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // P
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("P");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // Q
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("Q");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // R
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("R");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // S
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("S");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // T
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("T");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(null);
            lettreMorse.setTroisiemeCaractere(null);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // U
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("U");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // V
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("V");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // W
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("W");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(null);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // X
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("X");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // Y
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("Y");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // Z
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("Z");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(null);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 0
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("0");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 1
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("1");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 2
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("2");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 3
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("3");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 4
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("4");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 5
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("5");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Point);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 6
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("6");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Point);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 7
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("7");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Point);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 8
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("8");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Point);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // 9
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("9");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Point);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // . point final
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet(".");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Point);
            lettreMorse.setSixiemeCaractere(SigneMorse.Tiret);
            lettreMorseDao.insert(lettreMorse);
            // , virgule
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet(",");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setSixiemeCaractere(SigneMorse.Tiret);
            lettreMorseDao.insert(lettreMorse);
            // ? point interrogation
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("?");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Point);
            lettreMorse.setSixiemeCaractere(SigneMorse.Point);
            lettreMorseDao.insert(lettreMorse);
            // { debut transmission
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("{");
            lettreMorse.setPremierCaractere(SigneMorse.Tiret);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Point);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Point);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
            // } fin transmission
            lettreMorse = new LettreMorse();
            lettreMorse.setLettreAlphabet("}");
            lettreMorse.setPremierCaractere(SigneMorse.Point);
            lettreMorse.setDeuxiemeCaractere(SigneMorse.Tiret);
            lettreMorse.setTroisiemeCaractere(SigneMorse.Point);
            lettreMorse.setQuatriemeCaractere(SigneMorse.Tiret);
            lettreMorse.setCinquiemeCaractere(SigneMorse.Point);
            lettreMorse.setSixiemeCaractere(null);
            lettreMorseDao.insert(lettreMorse);
        }
    }


    private void remplirBDExemple() {
        //todo
        remplirBDUtilisateur();
        remplirBDContact();
        remplirBDSmsAccident();
        remplirBDSmsEnlevement();

    }

    private void remplirBDSmsAccident() {
        if (smsAccidentDao.count() == 0) {
            List<Contact> listContact = contactDao.loadAll();
            for (Contact contact : listContact) {
                if (contact.getIsContactAccident()) {
                    SmsAccident smsAccident = new SmsAccident();
                    smsAccident.setMessage(recupererParametres().getSmsAccident());
                    smsAccident.setContactId(contact.getId());
                    smsAccidentDao.insert(smsAccident);
                }
            }
        }
    }
    private void remplirBDSmsEnlevement() {
        if (smsEnlevementDao.count() == 0) {
            List<Contact> listContact = contactDao.loadAll();
            for (Contact contact : listContact) {
                if (contact.getIsContactEnlevement()) {
                    SmsEnlevement smsEnlevement = new SmsEnlevement();
                    smsEnlevement.setMessage(recupererParametres().getSmsEnlevement());
                    smsEnlevement.setContactId(contact.getId());
                    smsEnlevementDao.insert(smsEnlevement);
                }
            }
        }
    }




    private void remplirBDContact() {
        //todo
        if (contactDao.count() == 0) {
            Contact contact = new Contact();
            contact.setNom("John");
            contact.setTelephone("0603506694");
            contact.setIsContactAccident(true);
            contact.setIsContactEnlevement(true);
            contactDao.insert(contact);
            contact = new Contact();
            contact.setNom("Bill");
            contact.setTelephone("0603506694");
            contact.setIsContactAccident(true);
            contactDao.insert(contact);
            contact = new Contact();
            contact.setNom("Alfred");
            contact.setTelephone("0603506694");
            contact.setIsContactEnlevement(true);
            contactDao.insert(contact);
        }
    }

    private void remplirBDUtilisateur() {
        //todo

        if (utilisateurDao.count() == 0) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom("LEPONGE");
            utilisateur.setPrenom("Bob");
            utilisateur.setAdresse("15 rue de la paix");
            utilisateur.setCp("75008");
            utilisateur.setVille("PARIS");
            Date date = DateUtils.ajouterAnnee(new Date(),-20);
            utilisateur.setDateNaissance(date);
            utilisateur.setDateNaissanceString(DateUtils.ecrireDate(date));

            utilisateur.setIsDonneurOrgane(true);
            utilisateur.setTelephone("0101010101");
            utilisateur.setGroupeSanguin(GroupeSanguin.ABNegatif);
            utilisateur.setInfos("cacahuete");
            utilisateurDao.insert(utilisateur);
            Log.d("DaoUtilisateur", "Inserted new utilisateur, ID: " + utilisateur.getId());
        } else {
            //utilisateurDao.deleteAll();
        }



    }

   /* public DaoSession getDaoSession() {
        return daoSession;
    }*/







}
