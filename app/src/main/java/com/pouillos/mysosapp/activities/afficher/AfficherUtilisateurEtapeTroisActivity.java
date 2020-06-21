package com.pouillos.mysosapp.activities.afficher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.AccueilActivity;
import com.pouillos.mysosapp.activities.NavDrawerActivity;
import com.pouillos.mysosapp.entities.Contact;
import com.pouillos.mysosapp.entities.Utilisateur;
import com.pouillos.mysosapp.enumeration.GroupeSanguin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AfficherUtilisateurEtapeTroisActivity extends NavDrawerActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.textInfos)
    TextInputEditText textInfos;
    @BindView(R.id.layoutInfos)
    TextInputLayout layoutInfos;

    //Utilisateur currentUtilisateur;



    List<GroupeSanguin> listeGroupeSanguin;

    @BindView(R.id.selectGroupeSanguin)
    AutoCompleteTextView selectGroupeSanguin;
    @BindView(R.id.listGroupeSanguin)
    TextInputLayout listGroupeSanguin;

    GroupeSanguin groupeSanguinSelected;

    @BindView(R.id.chipDonneurOrgane)
    Chip chipDonneurOrgane;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_utilisateur_etape_trois);


        ButterKnife.bind(this);
        activeUser = findActiveUser();
        if (activeUser != null) {
            textInfos.setText(activeUser.getInfos());
            chipDonneurOrgane.setChecked(activeUser.getIsDonneurOrgane());
            selectGroupeSanguin.setText(activeUser.getGroupeSanguin().name);
            groupeSanguinSelected = activeUser.getGroupeSanguin();
        }
       // traiterIntent();

        //progressBar.setVisibility(View.VISIBLE);
       // AfficherUtilisateurEtapeTroisActivity.AsyncTaskRunnerBD runnerBD = new AfficherUtilisateurEtapeTroisActivity.AsyncTaskRunnerBD();
       // runnerBD.execute();
        listeGroupeSanguin = GroupeSanguin.Default.listAll();
        buildDropdownMenu(listeGroupeSanguin,AfficherUtilisateurEtapeTroisActivity.this,selectGroupeSanguin);

        selectGroupeSanguin.setOnItemClickListener(this);
    }

    /*private class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AfficherUtilisateurEtapeTroisActivity.this, R.string.text_DB_created, Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        groupeSanguinSelected = listeGroupeSanguin.get(position);
    }

    /*public void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("utilisateurId")) {
            Long utilisateurId = intent.getLongExtra("utilisateurId", 0);
            currentUtilisateur = utilisateurDao.load(utilisateurId);
        }
    }*/



    private boolean isFullRempli() {
        boolean bool = true;

        if (groupeSanguinSelected == null) {
            selectGroupeSanguin.setError("Obligatoire");
            bool = false;
        }
        return bool;
    }

    @OnClick(R.id.fabSave)
    public void setfabSaveClick() {



        if (isFullRempli()) {
            activeUser.setGroupeSanguin(groupeSanguinSelected);
            activeUser.setInfos(textInfos.getText().toString());
            activeUser.setIsDonneurOrgane(chipDonneurOrgane.isChecked());
            utilisateurDao.update(activeUser);
            List<Contact> listContact = contactDao.loadAll();
            if (listContact.size()>0) {
                ouvrirActiviteSuivante(AfficherUtilisateurEtapeTroisActivity.this,AccueilActivity.class,true);
            } else {
                ouvrirActiviteSuivante(AfficherUtilisateurEtapeTroisActivity.this,AfficherListeContactActivity.class,true);

            }
                  }
    }




}
