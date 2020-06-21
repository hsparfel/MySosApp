package com.pouillos.mysosapp.activities.afficher;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.AccueilActivity;
import com.pouillos.mysosapp.activities.NavDrawerActivity;
import com.pouillos.mysosapp.dao.SmsAccidentDao;
import com.pouillos.mysosapp.dao.SmsEnlevementDao;
import com.pouillos.mysosapp.entities.Contact;
import com.pouillos.mysosapp.entities.Parametres;
import com.pouillos.mysosapp.entities.SmsAccident;
import com.pouillos.mysosapp.entities.SmsEnlevement;
import com.pouillos.mysosapp.enumeration.TypeSms;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AfficherSmsPersoActivity extends NavDrawerActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.textSms)
    TextInputEditText textSms;
    @BindView(R.id.layoutSms)
    TextInputLayout layoutSms;

    List<Contact> listeContact;
    List<Contact> listeContactAccident;
    List<Contact> listeContactEnlevement;

    @BindView(R.id.selectContact)
    AutoCompleteTextView selectContact;
    @BindView(R.id.listContact)
    TextInputLayout listContact;

    Contact contactSelected;

    @BindView(R.id.chipAccident)
    Chip chipAccident;
    @BindView(R.id.chipEnlevement)
    Chip chipEnlevement;
    @BindView(R.id.chipGroupType)
    ChipGroup chipGroupType;

    boolean boolContactAccident;
    boolean boolContactEnlevement;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @BindView(R.id.checkboxAppliquerPartout)
    MaterialCheckBox checkboxAppliquerPartout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_sms_perso);


        ButterKnife.bind(this);
        activeUser = findActiveUser();
        if (activeUser != null) {
            listeContact = contactDao.loadAll();
            buildDropdownMenu(listeContact, AfficherSmsPersoActivity.this,selectContact);
        }
       // traiterIntent();

        //progressBar.setVisibility(View.VISIBLE);
       // AfficherUtilisateurEtapeTroisActivity.AsyncTaskRunnerBD runnerBD = new AfficherUtilisateurEtapeTroisActivity.AsyncTaskRunnerBD();
       // runnerBD.execute();
        //listeContact = Contact.Default.listAll();
        layoutSms.setVisibility(View.INVISIBLE);
        chipGroupType.setVisibility(View.INVISIBLE);
        checkboxAppliquerPartout.setVisibility(View.INVISIBLE);
        fabSave.hide();


        selectContact.setOnItemClickListener(this);

        listeContactAccident = new ArrayList<>();
        listeContactEnlevement = new ArrayList<>();
        for (Contact currentContact : listeContact) {
            if (currentContact.getIsContactAccident()) {
                listeContactAccident.add(currentContact);
            }
            if (currentContact.getIsContactEnlevement()) {
                listeContactEnlevement.add(currentContact);
            }
        }

        boolContactAccident = false;
        boolContactEnlevement = false;
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

    @OnClick(R.id.chipAccident)
    public void setchipAccidentClick() {
        boolContactAccident = true;
        boolContactEnlevement = false;
        if (boolContactAccident) {
            chipAccident.setClickable(false);
            chipEnlevement.setClickable(true);
            chipEnlevement.setChecked(false);
            textSms.setText(recupererSmsPerso(contactSelected,TypeSms.Accident));
            layoutSms.setVisibility(View.VISIBLE);
            checkboxAppliquerPartout.setVisibility(View.VISIBLE);
            fabSave.show();
        }
    }

    @OnClick(R.id.chipEnlevement)
    public void setChipEnlevementClick() {
        boolContactAccident = false;
        boolContactEnlevement = true;
        if (boolContactEnlevement) {
            chipEnlevement.setClickable(false);
            chipAccident.setClickable(true);
            chipAccident.setChecked(false);
            textSms.setText(recupererSmsPerso(contactSelected,TypeSms.Enlevement));
            layoutSms.setVisibility(View.VISIBLE);
            checkboxAppliquerPartout.setVisibility(View.VISIBLE);
            fabSave.show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        chipEnlevement.setChecked(false);
        chipAccident.setChecked(false);
        boolContactEnlevement=false;
        boolContactAccident=false;
        layoutSms.setVisibility(View.INVISIBLE);
        checkboxAppliquerPartout.setVisibility(View.INVISIBLE);
        checkboxAppliquerPartout.setChecked(false);


        chipGroupType.setVisibility(View.VISIBLE);
        chipAccident.setVisibility(View.VISIBLE);
        chipEnlevement.setVisibility(View.VISIBLE);
        contactSelected = listeContact.get(position);
        if (!contactSelected.getIsContactAccident()){
            chipAccident.setVisibility(View.INVISIBLE);
            chipEnlevement.setChecked(true);
            boolContactEnlevement = true;
            textSms.setText(recupererSmsPerso(contactSelected,TypeSms.Enlevement));
            layoutSms.setVisibility(View.VISIBLE);
            fabSave.show();
            checkboxAppliquerPartout.setVisibility(View.VISIBLE);
        } else if (!contactSelected.getIsContactEnlevement()){
            chipEnlevement.setVisibility(View.INVISIBLE);
            chipAccident.setChecked(true);
            boolContactAccident = true;
            textSms.setText(recupererSmsPerso(contactSelected,TypeSms.Accident));
            layoutSms.setVisibility(View.VISIBLE);
            fabSave.show();
            checkboxAppliquerPartout.setVisibility(View.VISIBLE);
        } else {
            //todo
        }
    }

    public String recupererSmsPerso(Contact contact, TypeSms typeSms) {
        String smsToReturn = "";
        //recup sms par defaut
        if (typeSms == typeSms.Accident) {
            Parametres parametres = recupererParametres();
            smsToReturn = parametres.getSmsAccident();
        } else if (typeSms == typeSms.Enlevement) {
            Parametres parametres = recupererParametres();
            smsToReturn = parametres.getSmsEnlevement();
        }
        //recherche si smsperso
        if (typeSms == typeSms.Accident) {
            List<SmsAccident> listSms = smsAccidentDao.queryBuilder()
                    .where(SmsAccidentDao.Properties.ContactId.eq(contact.getId()))
                    .list();
            if (listSms.size()>0) {
                smsToReturn = listSms.get(0).getMessage();
            }
        } else if (typeSms == typeSms.Enlevement) {
            List<SmsEnlevement> listSms = smsEnlevementDao.queryBuilder()
                    .where(SmsEnlevementDao.Properties.ContactId.eq(contact.getId()))
                    .list();
            if (listSms.size()>0) {
                smsToReturn = listSms.get(0).getMessage();
            }
        }
        return smsToReturn;
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

        if (contactSelected == null) {
            selectContact.setError("Obligatoire");
            bool = false;
        }
        return bool;
    }

    @OnClick(R.id.fabSave)
    public void setfabSaveClick() {

//todo

        if (isFullRempli()) {/*
            activeUser.setContact(contactSelected);
            activeUser.setInfos(textInfos.getText().toString());
            activeUser.setIsDonneurOrgane(chipDonneurOrgane.isChecked());
            utilisateurDao.update(activeUser);
            List<Contact> listContact = contactDao.loadAll();
            if (listContact.size()>0) {
                ouvrirActiviteSuivante(AfficherSmsPersoActivity.this,AccueilActivity.class,true);
            } else {
                ouvrirActiviteSuivante(AfficherSmsPersoActivity.this,AfficherListeContactActivity.class,true);

            }*/
                  }
    }




}
