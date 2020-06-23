package com.pouillos.mysosapp.activities.afficher;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.NavDrawerActivity;
import com.pouillos.mysosapp.entities.Utilisateur;
import com.pouillos.mysosapp.utils.DateUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AfficherUtilisateurEtapeUnActivity extends NavDrawerActivity {

    @BindView(R.id.textName)
    TextInputEditText textName;
    @BindView(R.id.layoutName)
    TextInputLayout layoutName;

    @BindView(R.id.textFirstName)
    TextInputEditText textFirstName;
    @BindView(R.id.layoutFirstName)
    TextInputLayout layoutFirstName;

    @BindView(R.id.textBirthday)
    TextInputEditText textBirthday;
    @BindView(R.id.layoutBirthday)
    TextInputLayout layoutBirthday;

    Date dateOfBirth;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    //Utilisateur currentUtilisateur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_utilisateur_etape_un);

      //  utilisateurDao = daoSession.getUtilisateurDao();
// 6 - Configure all views
     //   this.configureToolBar();
     //   this.configureDrawerLayout();
     //   this.configureNavigationView();

        ButterKnife.bind(this);

        activeUser = findActiveUser();
        if (activeUser != null) {

// 6 - Configure all views
            this.configureToolBar();
          this.configureDrawerLayout();
            this.configureNavigationView();



            textName.setText(activeUser.getNom());
            textFirstName.setText(activeUser.getPrenom());
            textBirthday.setText(DateUtils.ecrireDate(activeUser.getDateNaissance()));
            dateOfBirth = activeUser.getDateNaissance();
        }


        //todo a faire dans les 3 etpes - afficher les valeurs si user existe en bd
        //todo suppr fabedit si inutile





        textBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(view, textBirthday,false,false,null,null);
                    textBirthday.clearFocus();
                    dateOfBirth = convertStringToDate(textBirthday.getText().toString());
                }

            }
        });

    }





    private boolean isFullRempli() {
        boolean bool = true;
        if (!isFilled(textName)) {
            textName.setError("Obligatoire");
            bool = false;
        }

        if (!isFilled(textFirstName)) {
            textFirstName.setError("Obligatoire");
            bool = false;
        }
        if (!isFilled(textBirthday)) {
            textBirthday.setError("Obligatoire");
            bool = false;
        }
        return bool;
    }

    @OnClick(R.id.fabSave)
    public void setfabSaveClick() {

        if (isFullRempli()) {
            Utilisateur utilisateur = new Utilisateur();
                    if (activeUser != null) {
                        utilisateur = activeUser;
                    }
                    //utilisateur = new Utilisateur();
                    utilisateur.setNom(textName.getText().toString());
                    utilisateur.setPrenom(textFirstName.getText().toString());
                    utilisateur.setDateNaissance(dateOfBirth);
                    utilisateur.setDateNaissanceString(DateUtils.ecrireDate(dateOfBirth));
            if (activeUser != null) {
                utilisateurDao.update(utilisateur);
            } else {
                utilisateurDao.insert(utilisateur);
            }

                  ouvrirActiviteSuivante(AfficherUtilisateurEtapeUnActivity.this,AfficherUtilisateurEtapeDeuxActivity.class,true);
                }
    }




}
