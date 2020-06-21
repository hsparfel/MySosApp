package com.pouillos.mysosapp.activities.afficher;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.AccueilActivity;
import com.pouillos.mysosapp.activities.NavDrawerActivity;
import com.pouillos.mysosapp.entities.Contact;
import com.pouillos.mysosapp.entities.Utilisateur;
import com.pouillos.mysosapp.enumeration.GroupeSanguin;
import com.pouillos.mysosapp.recycler.adapter.RecyclerAdapterContact;
import com.pouillos.mysosapp.utils.ItemClickSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AfficherListeContactActivity extends NavDrawerActivity implements RecyclerAdapterContact.Listener {



    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    private List<Contact> listContact;
    private List<Contact> listContactBD;
    //private Utilisateur currentUtilisateur;

    private RecyclerAdapterContact adapter;

    @BindView(R.id.listeContact)
    RecyclerView listeContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_liste_contact);

        ButterKnife.bind(this);
        activeUser = findActiveUser();
       // traiterIntent();
        listContactBD = contactDao.loadAll();
        if (listContactBD.size() == 0) {
            fabSave.hide();
        }
//todo remplir recyclerview

        configureRecyclerView();
        configureOnClickRecyclerView();
    }



    /*public void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("utilisateurId")) {
            Long utilisateurId = intent.getLongExtra("utilisateurId", 0);
            currentUtilisateur = utilisateurDao.load(utilisateurId);
        }
    }*/

    public void configureRecyclerView() {
        adapter = new RecyclerAdapterContact(listContactBD,this);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        listeContact.setAdapter(adapter);
        // 3.4 - Set layout manager to position the items
        //this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listeContact.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(listeContact, R.layout.recycler_list_contact)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                    }
                });
    }

    @Override
    public void onClickContactButton(int position) {
        Contact contact = adapter.getContact(position);
        Toast.makeText(AfficherListeContactActivity.this, "a faire ", Toast.LENGTH_SHORT).show();
        //contact.delete();


        //listContactBD.remove(position);
        //adapter.notifyItemRemoved(position);
    }
    
    @OnClick(R.id.fabAdd)
    public void setfabAddClick() {
        ouvrirActiviteSuivante(AfficherListeContactActivity.this,AfficherContactActivity.class,true);
    }

    @OnClick(R.id.fabSave)
    public void setFabSaveClick() {
        ouvrirActiviteSuivante(AfficherListeContactActivity.this, AccueilActivity.class,true);
    }


}
