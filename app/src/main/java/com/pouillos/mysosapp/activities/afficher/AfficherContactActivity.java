package com.pouillos.mysosapp.activities.afficher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.NavDrawerActivity;
import com.pouillos.mysosapp.entities.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AfficherContactActivity extends NavDrawerActivity  {

    @BindView(R.id.textName)
    TextInputEditText textName;
    @BindView(R.id.layoutName)
    TextInputLayout layoutName;

    @BindView(R.id.textPhone)
    TextInputEditText textPhone;
    @BindView(R.id.layoutPhone)
    TextInputLayout layoutPhone;

    @BindView(R.id.switchAccident)
    SwitchMaterial switchAccident;
    @BindView(R.id.switchEnlevement)
    SwitchMaterial switchEnlevement;



    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;


    Contact currentContact;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_contact);

        ButterKnife.bind(this);
        activeUser = findActiveUser();
        traiterIntent();


    }



    public void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("contactId")) {
            Long contactId = intent.getLongExtra("contactId", 0);
            currentContact = contactDao.load(contactId);

            //todo afficher toutes les données
            textName.setText(currentContact.getNom());
            textPhone.setText(currentContact.getTelephone());
            //switchAccident.setChecked(currentContact.i); etc...
        } else {
            currentContact = new Contact();
        }
    }

    @OnClick(R.id.fabSave)
    public void setfabSaveClick() {
        if (isFullRempli()) {
            currentContact.setNom(textName.getText().toString());
            currentContact.setTelephone(textPhone.getText().toString());
            //currentContact.set(textTown.getText().toString());
            //currentContact.setTelephone(textPhone.getText().toString());




            if (switchAccident.isChecked()) {
                currentContact.setIsContactAccident(true);
            }
            if (switchEnlevement.isChecked()) {
                currentContact.setIsContactEnlevement(true);
            }
            contactDao.insert(currentContact);

            ouvrirActiviteSuivante(AfficherContactActivity.this,AfficherListeContactActivity.class,true);
        }
    }

    private boolean isFullRempli() {
        boolean bool = true;
        if (!isFilled(textName)) {
            textName.setError("Obligatoire");
            bool = false;
        }

        if (!isFilled(textPhone)) {
            textPhone.setError("Obligatoire");
            bool = false;
        }
        if (!isValidTel(textPhone)) {
            textPhone.setError("Saisie Non Valide  (10 chiffres)");
            bool = false;
        }

        if (!switchAccident.isChecked() && !switchEnlevement.isChecked()) {
            Toast.makeText(this, "Veuillez selectionner au moins un type d'alerte", Toast.LENGTH_LONG).show();
            bool = false;
        }
        return bool;
    }



}
