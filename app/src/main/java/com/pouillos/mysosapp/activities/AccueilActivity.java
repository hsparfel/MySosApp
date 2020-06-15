package com.pouillos.mysosapp.activities;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.facebook.stetho.Stetho;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.dao.DaoMaster;
import com.pouillos.mysosapp.dao.DaoSession;
import com.pouillos.mysosapp.dao.UtilisateurDao;
import com.pouillos.mysosapp.entities.Utilisateur;
import com.pouillos.mysosapp.enumeration.GroupeSanguin;
import com.pouillos.mysosapp.utils.DateUtils;

import org.greenrobot.greendao.database.Database;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;

public class AccueilActivity extends NavDrawerActivity {

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    private DaoSession daoSession;
    private UtilisateurDao utilisateurDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Stetho.initializeWithDefaults(this);

        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        ButterKnife.bind(this);

        //initialiser greenDAO
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "my_sos_app_db", null);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        utilisateurDao = daoSession.getUtilisateurDao();

        textView.setText(DateUtils.ecrireDateLettre(new Date()));

        progressBar.setVisibility(View.VISIBLE);
        AccueilActivity.AsyncTaskRunnerBD runnerBD = new AccueilActivity.AsyncTaskRunnerBD();
        runnerBD.execute();


    }

    private class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            remplirBDDefaut();
            publishProgress(50);
            remplirBDExemple();
            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AccueilActivity.this, R.string.text_DB_created, Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    private void remplirBDDefaut() {
        //todo
    }

    private void remplirBDExemple() {
        //todo
        remplirBDUtilisateur();

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
            utilisateur.setDateNaissance(DateUtils.ajouterAnnee(new Date(),-20));
            utilisateur.setIsDonneurOrgane(true);
            utilisateur.setTelephone("0101010101");
            utilisateur.setGroupeSanguin(GroupeSanguin.ABNegatif);
            utilisateurDao.insert(utilisateur);
            Log.d("DaoUtilisateur", "Inserted new utilisateur, ID: " + utilisateur.getId());
        }



    }

    public DaoSession getDaoSession() {
        return daoSession;
    }







}
