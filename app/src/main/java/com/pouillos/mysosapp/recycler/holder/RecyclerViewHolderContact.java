package com.pouillos.mysosapp.recycler.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.pouillos.mysosapp.R;
import com.pouillos.mysosapp.activities.NavDrawerActivity;
import com.pouillos.mysosapp.entities.Contact;
import com.pouillos.mysosapp.recycler.adapter.RecyclerAdapterContact;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolderContact extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.detail)
    TextView detail;

    @BindView(R.id.imageAccident)
    ImageView imageAccident;
    @BindView(R.id.imageEnlevement)
    ImageView imageEnlevement;

    private WeakReference<RecyclerAdapterContact.Listener> callbackWeakRef;

    public RecyclerViewHolderContact(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //ButterKnife.bind(this);
    }

    public void updateWithContact(Contact contact, RecyclerAdapterContact.Listener callback){
       // double distance = ChercherContactActivity.calculerDistance(latitude,longitude,contact.getLatitude(),contact.getLongitude());

//todo revoir l'affichage en limitan apres la virgule - FAIT OK
       // NumberFormat formatter = new DecimalFormat("#0.0");
       // System.out.println(formatter.format(4.0));

      //  this.detail.setText(contact.getRaisonSocial() + " - " + formatter.format(distance)+ " km");
        this.detail.setText(contact.getNom() + " - " + contact.getTelephone());
        this.detail.setOnClickListener(this);
        if (!contact.getIsContactAccident()) {
            imageAccident.setVisibility(View.INVISIBLE);
        }
        if (!contact.getIsContactEnlevement()) {
            imageEnlevement.setVisibility(View.INVISIBLE);
        }
        //4 - Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<RecyclerAdapterContact.Listener>(callback);
    }


    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        RecyclerAdapterContact.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickContactButton(getAdapterPosition());
    }
}
