package com.example.devicecontrollernew;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Toolbar toolbar;
    private DrawerLayout drawer;
    DatabaseReference databaseDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

//        FirebaseApp.initializeApp(this);
        //Database reference
        databaseDevice= FirebaseDatabase.getInstance().getReference("Device");


        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
  //      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer=findViewById(R.id.drawer_layout);
        setupToolbar();

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);


        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        if(savedInstanceState==null)
//        {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new InstructionsFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_Instructions);
//        }

    }
    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.device_dialogue, null);
                final EditText Name = alertLayout.findViewById(R.id.name);
                final EditText Number = alertLayout.findViewById(R.id.number);
//                final CheckBox cbToggle = alertLayout.findViewById(R.id.radiooptions);


                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Setup Buttons");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
   //             Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = Name.getText().toString().trim();
                        String number=Number.getText().toString().trim();
//                        b1.setText(label);
                        if(!TextUtils.isEmpty(name))
                        {
                            String id= databaseDevice.push().getKey();

                            Device device=new Device(id,name,number);
                            databaseDevice.child(id).setValue(device);

                            Toast.makeText(getBaseContext(), "Your device added successfully", Toast.LENGTH_LONG).show();
                        }
//                        else
//                        {
//                            Toast.makeText(this,"Please enter the name for the device", Toast.LENGTH_LONG).show();
//                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

                return true;
            case R.id.item2:
                FirebaseUser Current=FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(getApplicationContext()," "+Current.getUid(),Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:

            case R.id.item4:
                LOGOUT();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void LOGOUT()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_Aboutus:
                break;
            case R.id.nav_Instructions:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new InstructionsFragment()).commit();
                break;
            case R.id.nav_Website:
                String url="https://www.amazon.in/Febo-Solution-based-switch-Mobile/dp/B074DT34MB?tag=googinhydr18418-21&tag=googinkenshoo-21&ascsubtag=_k_Cj0KCQiAtvPjBRDPARIsAJfZz0ovYujAb_7h6GjWj0h_xROE89W_OQEe1aspmwpp1j_fXIuGsfOd1N8aAuagEALw_wcB_k_&gclid=Cj0KCQiAtvPjBRDPARIsAJfZz0ovYujAb_7h6GjWj0h_xROE89W_OQEe1aspmwpp1j_fXIuGsfOd1N8aAuagEALw_wcB";
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.nav_Help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HelpFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    public void Dialogue1(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.mainpage_dialogue, null);
        final EditText Label = alertLayout.findViewById(R.id.label);
        final EditText message = alertLayout.findViewById(R.id.message);
        final CheckBox cbToggle = alertLayout.findViewById(R.id.radiooptions);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Setup Buttons");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String label = Label.getText().toString();
                Button b1=(Button)findViewById(R.id.btn1);
                b1.setText(label);

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void Dialogue2(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.mainpage_dialogue, null);
        final EditText Label = alertLayout.findViewById(R.id.label);
        final EditText message = alertLayout.findViewById(R.id.message);
        final CheckBox cbToggle = alertLayout.findViewById(R.id.radiooptions);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Setup Buttons");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String label = Label.getText().toString();
                Button b1=(Button)findViewById(R.id.btn2);
                b1.setText(label);

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void Dialogue3(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.mainpage_dialogue, null);
        final EditText Label = alertLayout.findViewById(R.id.label);
        final EditText message = alertLayout.findViewById(R.id.message);
        final CheckBox cbToggle = alertLayout.findViewById(R.id.radiooptions);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Setup Buttons");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String label = Label.getText().toString();
                Button b1=(Button)findViewById(R.id.btn3);
                b1.setText(label);

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }
}
