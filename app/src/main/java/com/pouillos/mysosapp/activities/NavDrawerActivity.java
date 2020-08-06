package com.pouillos.mysosapp.activities;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import com.pouillos.mysosapp.R;

import com.pouillos.mysosapp.activities.action.ChronoCardiaqueActivity;
import com.pouillos.mysosapp.activities.action.LampeActivity;
import com.pouillos.mysosapp.activities.action.MorseActivity;
import com.pouillos.mysosapp.activities.afficher.AfficherListeContactActivity;
import com.pouillos.mysosapp.activities.afficher.AfficherSmsPersoActivity;
import com.pouillos.mysosapp.activities.afficher.AfficherUtilisateurEtapeUnActivity;
import com.pouillos.mysosapp.dao.ContactDao;
import com.pouillos.mysosapp.dao.DaoMaster;
import com.pouillos.mysosapp.dao.DaoSession;
import com.pouillos.mysosapp.dao.HistoriqueAppelDao;
import com.pouillos.mysosapp.dao.HistoriqueSmsDao;
import com.pouillos.mysosapp.dao.LettreMorseDao;
import com.pouillos.mysosapp.dao.ParametresDao;
import com.pouillos.mysosapp.dao.SmsAccidentDao;
import com.pouillos.mysosapp.dao.SmsEnlevementDao;
import com.pouillos.mysosapp.dao.TempoMorseDao;
import com.pouillos.mysosapp.dao.UtilisateurDao;
import com.pouillos.mysosapp.entities.Contact;
import com.pouillos.mysosapp.entities.HistoriqueSms;
import com.pouillos.mysosapp.entities.LettreMorse;
import com.pouillos.mysosapp.entities.Parametres;
import com.pouillos.mysosapp.entities.TempoMorse;
import com.pouillos.mysosapp.entities.Utilisateur;
import com.pouillos.mysosapp.fragments.DatePickerFragment;
import com.pouillos.mysosapp.utils.DateUtils;


import org.greenrobot.greendao.database.Database;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import icepick.Icepick;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //FOR DESIGN

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    protected DaoSession daoSession;

    //private DaoSession daoSession;
    protected UtilisateurDao utilisateurDao;
    protected LettreMorseDao lettreMorseDao;
    protected TempoMorseDao tempoMorseDao;
    protected ContactDao contactDao;
    protected SmsAccidentDao smsAccidentDao;
    protected SmsEnlevementDao smsEnlevementDao;

    protected ParametresDao parametresDao;
    protected HistoriqueSmsDao historiqueSmsDao;
    protected HistoriqueAppelDao historiqueAppelDao;

    protected Utilisateur activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //a redefinit à chq fois
        super.onCreate(savedInstanceState);
        //initialiser greenDAO
        initialiserDao();
        utilisateurDao = daoSession.getUtilisateurDao();
        lettreMorseDao = daoSession.getLettreMorseDao();
        tempoMorseDao = daoSession.getTempoMorseDao();
        contactDao = daoSession.getContactDao();
        smsAccidentDao = daoSession.getSmsAccidentDao();
        smsEnlevementDao = daoSession.getSmsEnlevementDao();
        parametresDao = daoSession.getParametresDao();
        historiqueSmsDao = daoSession.getHistoriqueSmsDao();
        historiqueAppelDao = daoSession.getHistoriqueAppelDao();
      /*  List<Utilisateur> listUserActif = Utilisateur.find(Utilisateur.class, "actif = ?", "1");
        if (listUserActif.size() != 0) {
            activeUser = listUserActif.get(0);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        //getMenuInflater().inflate(R.menu.menu_add_item_to_db, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        Intent myProfilActivity;

        switch (id) {
            case R.id.activity_main_drawer_home:
                ouvrirActiviteSuivante(NavDrawerActivity.this, AccueilActivity.class,true);
                break;
            case R.id.activity_main_drawer_lampe:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                ouvrirActiviteSuivante(NavDrawerActivity.this, LampeActivity.class,true);
                break;
            case R.id.activity_main_drawer_sms_perso:
               // Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherSmsPersoActivity.class);
                startActivity(myProfilActivity);
                break;
            case R.id.activity_main_drawer_account:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherUtilisateurEtapeUnActivity.class);
                startActivity(myProfilActivity);
                break;

            case R.id.activity_main_drawer_morse:
                myProfilActivity = new Intent(NavDrawerActivity.this, MorseActivity.class);
                startActivity(myProfilActivity);
                break;

            case R.id.activity_main_drawer_contacts:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherListeContactActivity.class);
                startActivity(myProfilActivity);
                break;

            case R.id.activity_main_drawer_dae:
                Intent i = getPackageManager().getLaunchIntentForPackage("com.mobilehealth.cardiac");
                if (i != null)
                {
                    startActivity(i);
                } else {
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.mobilehealth.cardiac" ) );
                    startActivity(intent);
                }
                break;

            case R.id.activity_main_drawer_chrono:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, ChronoCardiaqueActivity.class);
                startActivity(myProfilActivity);
                break;

            case R.id.activity_main_drawer_raz:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                raz();
                Toast.makeText(this, "RAZ done", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    protected void raz() {
        tempoMorseDao.deleteAll();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // Intent myProfilActivity = new Intent(NavDrawerActivity.this, ChercherContactActivity.class);
        //startActivity(myProfilActivity);
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            /*case R.id.menu_activity_main_params:
                Toast.makeText(this, "Il n'y a rien à paramétrer ici, passez votre chemin...", Toast.LENGTH_LONG).show();
                return true;*/
            case R.id.menu_activity_main_search:
                //Toast.makeText(this, "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", Toast.LENGTH_LONG).show();
              //  myProfilActivity = new Intent(NavDrawerActivity.this, ChercherContactActivity.class);
               // startActivity(myProfilActivity);
                return true;





            case R.id.rchEtablissement:
             //   myProfilActivity = new Intent(NavDrawerActivity.this, ChercherEtablissementActivity.class);
              //  startActivity(myProfilActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    public void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

    }

    // 2 - Configure Drawer Layout
    public void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    public void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, boolean bool) {
        Intent intent = new Intent(context, classe);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }
    public void revenirActivitePrecedente(String sharedName, String dataName, Long itemId) {
        SharedPreferences preferences=getSharedPreferences(sharedName,MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putLong(dataName,itemId);
        editor.commit();
        finish();
    }


    public void ouvrirActiviteSuivante(Context context, Class classe, String nomExtra, Long objetIdExtra, boolean bool) {
        Intent intent = new Intent(context, classe);
        intent.putExtra(nomExtra, objetIdExtra);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, String nomExtra, String objetExtra, String nomExtra2, Long objetIdExtra2, boolean bool) {
        Intent intent = new Intent(context, classe);
        intent.putExtra(nomExtra, objetExtra);
        intent.putExtra(nomExtra2, objetIdExtra2);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected Date ActualiserDate(Date date, String time){
        Date dateActualisee = date;
        int nbHour = Integer.parseInt(time.substring(0,2));
        int nbMinute = Integer.parseInt(time.substring(3));
        dateActualisee = DateUtils.ajouterHeure(dateActualisee,nbHour);
        dateActualisee = DateUtils.ajouterMinute(dateActualisee,nbMinute);
        return dateActualisee;
    }

    protected Utilisateur findActiveUser() {
        Utilisateur user = null;
        List<Utilisateur> listUtilisateur = utilisateurDao.loadAll();
        if (listUtilisateur.size() !=0){
            user = listUtilisateur.get(0);
        }
       return user;
    }

    protected <T> void buildDropdownMenu(List<T> listObj, Context context, AutoCompleteTextView textView) {
        List<String> listString = new ArrayList<>();
        String[] listDeroulante;
        listDeroulante = new String[listObj.size()];
        for (T object : listObj) {
            listString.add(object.toString());
        }
        listString.toArray(listDeroulante);
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.list_item, listDeroulante);
        textView.setAdapter(adapter);
    }

    @Override
    public Executor getMainExecutor() {
        return super.getMainExecutor();
    }



    /*protected <T extends SugarRecord> void deleteItem(Context context, T item, Class classe, boolean toConfirm) {
        if (toConfirm) {
            new MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.dialog_delete_title)
                    .setMessage(R.string.dialog_delete_message)
                    .setNegativeButton(R.string.dialog_delete_negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, R.string.dialog_delete_negative_toast, Toast.LENGTH_LONG).show();

                        }
                    })
                    .setPositiveButton(R.string.dialog_delete_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, R.string.dialog_delete_positive_toast, Toast.LENGTH_LONG).show();
                            item.delete();
                            supprimerNotification(item, context);

                            ouvrirActiviteSuivante(context, classe,true);

                        }
                    })
                    .show();
        } else {
            item.delete();
            supprimerNotification(item, context);
            ouvrirActiviteSuivante(context, classe,true);
        }



    }*/

    protected boolean isChecked(ChipGroup chipGroup) {
        boolean bool;
        if (chipGroup.getCheckedChipId() != -1) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }


    protected boolean isFilled(TextInputEditText textInputEditText){
        boolean bool;
        if (textInputEditText.length()>0) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    protected boolean isFilled(Object object){
        boolean bool;
        if (object!=null) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    protected boolean isValidTel(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && textView.getText().length() <10) {
            //textView.requestFocus();
            //textView.setError("Saisie Non Valide  (10 chiffres)");
            return false;
        } else {
            return true;
        }
    }

    protected boolean isValidZip(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && textView.getText().length() <5) {
            //textView.requestFocus();
            //textView.setError("Saisie Non Valide  (5 chiffres)");
            return false;
        } else {
            return true;
        }
    }

    protected boolean isEmailAdress(String email){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }
    protected boolean isValidEmail(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && !isEmailAdress(textView.getText().toString())) {
            //textView.requestFocus();
            //textView.setError("Saisie Non Valide (email)");
            return false;
        } else {
            return true;
        }
    }

    protected static float floatArrondi(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


    protected<T> void activerNotification(T object, Context context) {
      /*  if (object instanceof RdvContact) {
           startAlert((RdvContact) object, Echeance.OneHourAfter.toString(), context);
           startAlert((RdvContact) object, Echeance.OneDayAfter.toString(), context);
        } else if (object instanceof RdvAnalyse) {
            startAlert((RdvAnalyse) object, Echeance.OneHourAfter.toString(), context);
            startAlert((RdvAnalyse) object, Echeance.OneDayAfter.toString(), context);
        } else if (object instanceof RdvExamen) {
            startAlert((RdvExamen) object, Echeance.OneHourAfter.toString(), context);
            startAlert((RdvExamen) object, Echeance.OneDayAfter.toString(), context);
        } else if (object instanceof Prise) {
            startAlert((Prise) object, context);
        }*/

    }
    /*protected<T> void activerNotification(Class classe, Date dateRdv, T object, Context context) {
        if (classe == RdvContactNotificationBroadcastReceiver.class) {
            startAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Contact) object).toStringShort(), Echeance.OneHourAfter.toString(),context);
            startAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Contact) object).toStringShort(), Echeance.OneDayAfter.toString(),context);
        } else if (classe == RdvAnalyseNotificationBroadcastReceiver.class) {
            startAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Analyse) object).toString(), Echeance.OneHourAfter.toString(),context);
            startAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Analyse) object).toString(), Echeance.OneDayAfter.toString(),context);
        } else if (classe == RdvExamenNotificationBroadcastReceiver.class) {
            startAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Examen) object).toString(), Echeance.OneHourAfter.toString(),context);
            startAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Examen) object).toString(), Echeance.OneDayAfter.toString(),context);
        }

    }*/

    protected<T> void supprimerNotification(T object, Context context) {
      /*  if (object instanceof RdvContact) {
            List<AssociationAlarmRdv> listAssociationAlarmRdv = AssociationAlarmRdv.find(AssociationAlarmRdv.class,"rdv_contact = ?", ((RdvContact) object).getId().toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            for (AssociationAlarmRdv current : listAssociationAlarmRdv) {
                alarmRdv = AlarmRdv.findById(AlarmRdv.class,current.getAlarmRdv().getId());
                if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneHourAfter.toString())) {
                    cancelAlert((RdvContact) object, Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());
                } else if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneDayAfter.toString())) {
                    cancelAlert((RdvContact) object, Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
                }
            }
        } else if (object instanceof RdvAnalyse) {
            List<AssociationAlarmRdv> listAssociationAlarmRdv = AssociationAlarmRdv.find(AssociationAlarmRdv.class,"rdv_analyse = ?", ((RdvAnalyse) object).getId().toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            for (AssociationAlarmRdv current : listAssociationAlarmRdv) {
                alarmRdv = AlarmRdv.findById(AlarmRdv.class,current.getAlarmRdv().getId());
                if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneHourAfter.toString())) {
                    cancelAlert((RdvAnalyse) object, Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());
                } else if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneDayAfter.toString())) {
                    cancelAlert((RdvAnalyse) object, Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
                }
            }
        } else if (object instanceof RdvExamen) {
            List<AssociationAlarmRdv> listAssociationAlarmRdv = AssociationAlarmRdv.find(AssociationAlarmRdv.class,"rdv_examen = ?", ((RdvExamen) object).getId().toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            for (AssociationAlarmRdv current : listAssociationAlarmRdv) {
                alarmRdv = AlarmRdv.findById(AlarmRdv.class,current.getAlarmRdv().getId());
                if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneHourAfter.toString())) {
                    cancelAlert((RdvExamen) object, Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());
                } else if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneDayAfter.toString())) {
                    cancelAlert((RdvExamen) object, Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
                }
            }
        }*/
    }

    /*protected<T> void supprimerNotification(Class classe, Date dateRdv, T object, Context context) {
        if (classe == RdvContactNotificationBroadcastReceiver.class) {
            List<AlarmRdv> listAlarmRdv = AlarmRdv.find(AlarmRdv.class,"date_string = ? and detail = ? and echeance = ?",DateUtils.ajouterHeure(dateRdv,-1).toString(),((Contact) object).toStringShort(),Echeance.OneHourAfter.toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            if (listAlarmRdv.size()>0) {
                alarmRdv = listAlarmRdv.get(0);
            }
            cancelAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Contact) object).toStringShort(), Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());

            listAlarmRdv = AlarmRdv.find(AlarmRdv.class,"date_string = ? and detail = ? and echeance = ?",DateUtils.ajouterJourArrondi(dateRdv,-1,7).toString(),((Contact) object).toStringShort(),Echeance.OneDayAfter.toString());
            alarmRdv = new AlarmRdv();
            if (listAlarmRdv.size()>0) {
                alarmRdv = listAlarmRdv.get(0);
            }
            cancelAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Contact) object).toStringShort(), Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
        } else if (classe == RdvAnalyseNotificationBroadcastReceiver.class) {
            List<AlarmRdv> listAlarmRdv = AlarmRdv.find(AlarmRdv.class,"date_string = ? and detail = ? and echeance = ?",DateUtils.ajouterHeure(dateRdv,-1).toString(),((Analyse) object).toString(),Echeance.OneHourAfter.toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            if (listAlarmRdv.size()>0) {
                alarmRdv = listAlarmRdv.get(0);
            }
            cancelAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Analyse) object).toString(), Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());

            listAlarmRdv = AlarmRdv.find(AlarmRdv.class,"date_string = ? and detail = ? and echeance = ?",DateUtils.ajouterJourArrondi(dateRdv,-1,7).toString(),((Analyse) object).toString(),Echeance.OneDayAfter.toString());
            alarmRdv = new AlarmRdv();
            if (listAlarmRdv.size()>0) {
                alarmRdv = listAlarmRdv.get(0);
            }
            cancelAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Analyse) object).toString(), Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
        } else if (classe == RdvExamenNotificationBroadcastReceiver.class) {
            List<AlarmRdv> listAlarmRdv = AlarmRdv.find(AlarmRdv.class,"date_string = ? and detail = ? and echeance = ?",DateUtils.ajouterHeure(dateRdv,-1).toString(),((Examen) object).toString(),Echeance.OneHourAfter.toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            if (listAlarmRdv.size()>0) {
                alarmRdv = listAlarmRdv.get(0);
            }
            cancelAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Examen) object).toString(), Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());

            listAlarmRdv = AlarmRdv.find(AlarmRdv.class,"date_string = ? and detail = ? and echeance = ?",DateUtils.ajouterJourArrondi(dateRdv,-1,7).toString(),((Examen) object).toString(),Echeance.OneDayAfter.toString());
            alarmRdv = new AlarmRdv();
            if (listAlarmRdv.size()>0) {
                alarmRdv = listAlarmRdv.get(0);
            }
            cancelAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Examen) object).toString(), Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
        }
    }*/

    protected<T> void startAlert(T object, Context context) {
        //Class classe = object.getClass();
        Intent intent = null;

        //Prise prise = (Prise) object;

         //   intent = new Intent(this, PriseNotificationBroadcastReceiver.class);
         //   intent.putExtra("detail",prise.getMedicament().getDenominationShort()+ " - "+prise.getQteDose()+ " "+prise.getDose().getName());

        //intent.putExtra("echeance",echeance);
        Date dateJour = new Date();
        Long dateJourLong = dateJour.getTime();
        int requestCode =dateJourLong.intValue();
        Log.i("requestCode",""+requestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
      //  Date dateAlerte = prise.getDate();



      /*  alarmManager.set(AlarmManager.RTC_WAKEUP, dateAlerte.getTime(), pendingIntent);
        AlarmRdv alarmRdv = new AlarmRdv();
        alarmRdv.setClasse(object.getClass().getName());
        alarmRdv.setDate(dateAlerte);
        alarmRdv.setDateString(dateAlerte.toString());
        alarmRdv.setDetail(intent.getStringExtra("detail"));
        //alarmRdv.setEcheance(echeance);
        alarmRdv.setRequestCode(requestCode);
        alarmRdv.setId(alarmRdv.save());


        Toast.makeText(this, "Alarm set : " + prise.getDate().toString(), Toast.LENGTH_LONG).show();*/
    }

    protected<T> void startAlert(T object, String echeance, Context context) {
        //Class classe = object.getClass();
        Intent intent = null;

          /*  Rdv rdv = (Rdv) object;
            if (object instanceof RdvContact) {
                intent = new Intent(this, RdvContactNotificationBroadcastReceiver.class);
                intent.putExtra("detail",((RdvContact) object).getContact().toStringShort());
            } else if (object instanceof RdvAnalyse) {
                intent = new Intent(this, RdvAnalyseNotificationBroadcastReceiver.class);
                intent.putExtra("detail",((RdvAnalyse) object).getAnalyse().getName());
            } else if (object instanceof RdvExamen) {
                intent = new Intent(this, RdvExamenNotificationBroadcastReceiver.class);
                intent.putExtra("detail",((RdvExamen) object).getExamen().getName());
            }*/
            intent.putExtra("echeance",echeance);
            Date dateJour = new Date();
            Long dateJourLong = dateJour.getTime();
            int requestCode =dateJourLong.intValue();
            Log.i("requestCode",""+requestCode);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            Date dateAlerte = new Date();
        /*    if (echeance.equalsIgnoreCase(Echeance.OneHourAfter.toString())){
                dateAlerte = DateUtils.ajouterHeure(rdv.getDate(),-1);
            } else if (echeance.equalsIgnoreCase(Echeance.OneDayAfter.toString())){
                dateAlerte = DateUtils.ajouterJourArrondi(rdv.getDate(),-1,7);
            }*/


       /*     alarmManager.set(AlarmManager.RTC_WAKEUP, dateAlerte.getTime(), pendingIntent);
            AlarmRdv alarmRdv = new AlarmRdv();
            alarmRdv.setClasse(object.getClass().getName());
            alarmRdv.setDate(dateAlerte);
            alarmRdv.setDateString(dateAlerte.toString());
            alarmRdv.setDetail(intent.getStringExtra("detail"));
            alarmRdv.setEcheance(echeance);
            alarmRdv.setRequestCode(requestCode);
            alarmRdv.setId(alarmRdv.save());

            AssociationAlarmRdv associationAlarmRdv = new AssociationAlarmRdv();
            associationAlarmRdv.setAlarmRdv(alarmRdv);

            if (object instanceof RdvContact) {
                associationAlarmRdv.setRdvContact((RdvContact) object);
            } else if (object instanceof RdvAnalyse) {
                associationAlarmRdv.setRdvAnalyse((RdvAnalyse) object);
            } else if (object instanceof RdvExamen) {
                associationAlarmRdv.setRdvExamen((RdvExamen) object);
            }
            associationAlarmRdv.setId(associationAlarmRdv.save());
            Toast.makeText(this, "Alarm set : " + rdv.getDate().toString(), Toast.LENGTH_LONG).show();
*/

    }

    /*protected void startAlert(Class classe, Date dateRdv, String detail, String echeance, Context context) {
        Intent intent = new Intent(this, classe);
        intent.putExtra("detail",detail);
        intent.putExtra("echeance",echeance);
        Date dateJour = new Date();
        Long dateJourLong = dateJour.getTime();
        int requestCode =dateJourLong.intValue();
        Log.i("requestCode",""+requestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, dateRdv.getTime(), pendingIntent);
        AlarmRdv alarmRdv = new AlarmRdv();
        alarmRdv.setClasse(classe.getName());
        alarmRdv.setDate(dateRdv);
        alarmRdv.setDateString(dateRdv.toString());
        alarmRdv.setDetail(detail);
        alarmRdv.setEcheance(echeance);
        alarmRdv.setRequestCode(requestCode);
        alarmRdv.setId(alarmRdv.save());

        AssociationAlarmRdv associationAlarmRdv = new AssociationAlarmRdv();
        associationAlarmRdv.setAlarmRdv(alarmRdv);

        if (classe == RdvContactNotificationBroadcastReceiver.class) {
            // associationAlarmRdv.setRdvContact();
        } else if (classe == RdvAnalyseNotificationBroadcastReceiver.class) {

        } else if (classe == RdvExamenNotificationBroadcastReceiver.class) {

        }



        Toast.makeText(this, "Alarm set : " + dateRdv.toString(), Toast.LENGTH_LONG).show();
    }*/

    protected<T> void cancelAlert(T object, String echeance, Context context,int requestCode) {
        Intent intent = null;
     /*   Rdv rdv = (Rdv) object;
        if (object instanceof RdvContact) {
            intent = new Intent(this, RdvContactNotificationBroadcastReceiver.class);
            intent.putExtra("detail",((RdvContact) object).getContact().toStringShort());
        } else if (object instanceof RdvAnalyse) {
            intent = new Intent(this, RdvAnalyseNotificationBroadcastReceiver.class);
            intent.putExtra("detail",((RdvAnalyse) object).getAnalyse().getName());
        } else if (object instanceof RdvExamen) {
            intent = new Intent(this, RdvExamenNotificationBroadcastReceiver.class);
            intent.putExtra("detail",((RdvExamen) object).getExamen().getName());
        }
        intent.putExtra("echeance",echeance);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        List<AlarmRdv> listAlarmRdv = AlarmRdv.find(AlarmRdv.class,"request_code = ?", ""+requestCode);
        AlarmRdv alarmRdv = new AlarmRdv();
        if (listAlarmRdv.size()>0) {
            alarmRdv = listAlarmRdv.get(0);
            alarmRdv.delete();
        }

        List<AssociationAlarmRdv> listAssociationAlarmRdv = AssociationAlarmRdv.find(AssociationAlarmRdv.class,"alarm_rdv = ?", alarmRdv.getId().toString());
        if (listAssociationAlarmRdv.size()>0) {
            AssociationAlarmRdv associationAlarmRdv = listAssociationAlarmRdv.get(0);
            associationAlarmRdv.delete();
        }
        Toast.makeText(this, "Alarm deleted", Toast.LENGTH_LONG).show();*/
    }

    /*private void cancelAlert(Class classe, Date dateRdv, String detail, String echeance, Context context,int requestCode) {
        Intent intent = new Intent(this, classe);
        intent.putExtra("detail",detail);
        intent.putExtra("echeance",echeance);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        List<AlarmRdv> listAlarmRdv = AlarmRdv.find(AlarmRdv.class,"request_code = ?", ""+requestCode);
        if (listAlarmRdv.size()>0) {
            AlarmRdv alarmRdv = listAlarmRdv.get(0);
            alarmRdv.delete();
        }
        Toast.makeText(this, "Alarm deleted", Toast.LENGTH_LONG).show();
    }*/

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void showDatePickerDialog(View v,TextInputEditText textView, boolean hasDateMin, boolean hasDateMax,Date dateMin,Date dateMax) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "buttonDate");
        //Date dateToReturn;
        newFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if (hasDateMin) {
                    datePicker.setMinDate(dateMin.getTime());
                }
                if (hasDateMax) {
                    datePicker.setMaxDate(dateMax.getTime());
                }

                //datePicker.setMaxDate(new Date().getTime());
                String dateJour = ""+datePicker.getDayOfMonth();
                String dateMois = ""+(datePicker.getMonth()+1);
                String dateAnnee = ""+datePicker.getYear();
                if (datePicker.getDayOfMonth()<10) {
                    dateJour = "0"+dateJour;
                }
                if (datePicker.getMonth()+1<10) {
                    dateMois = "0"+dateMois;
                }
                String dateString = dateJour+"/"+dateMois+"/"+dateAnnee;

                textView.setText(dateString);
                //DateFormat df = new SimpleDateFormat(getResources().getString(R.string.format_date));
             //   try{

               //     dateToReturn = df.parse(dateString);

               // }catch(ParseException e){
               //     System.out.println(getResources().getString(R.string.error));
                //}
            }
        });
    }

    public Date convertStringToDate(String dateString) {
        DateFormat df = new SimpleDateFormat(getResources().getString(R.string.format_date));
        Date dateToReturn = new Date();
        try{
                dateToReturn = df.parse(dateString);
             }catch(ParseException e){
                 System.out.println(getResources().getString(R.string.error));
            }
        return dateToReturn;
    }



    public void initialiserDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "my_sos_app_db", null);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public Parametres recupererParametres() {
        List<Parametres> listParametres = parametresDao.loadAll();
        Parametres parametreToReturn = new Parametres();
        if (listParametres.size()>0) {
            parametreToReturn = listParametres.get(0);
        }
        return parametreToReturn;
    }

    public TempoMorse recupererTempoMorse() {
        List<TempoMorse> listTempoMorse = tempoMorseDao.loadAll();
        TempoMorse tempoMorseToReturn = new TempoMorse();
        if (listTempoMorse.size()>0) {
            tempoMorseToReturn = listTempoMorse.get(0);
        }
        return tempoMorseToReturn;
    }



    public void envoyerSms(Contact contact,String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault ();
            ArrayList<String> parts = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(contact.getTelephone(), null, parts, null, null);

            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
            HistoriqueSms historiqueSms = new HistoriqueSms();
            historiqueSms.setContactId(contact.getId());
            historiqueSms.setMessage(message);
            Date date = new Date();
            historiqueSms.setDate(date);
            historiqueSms.setDateString(DateUtils.ecrireDateHeure(date));

            historiqueSmsDao.insert(historiqueSms);



        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
         }

}
