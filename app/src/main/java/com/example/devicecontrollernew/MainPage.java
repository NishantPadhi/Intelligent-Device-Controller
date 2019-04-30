package com.example.devicecontrollernew;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.SEND_SMS;


public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FusedLocationProviderClient client;
    private DrawerLayout drawer;
    DatabaseReference databaseDevice;
    private FirebaseDatabase mFirebaseDatabase;
    String value_lat = null;
    String value_lng=null;
    String userid=null;

    FirebaseUser Current = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        FirebaseApp.initializeApp(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseDevice = mFirebaseDatabase.getReference("device");
        userid=Current.getUid();
      //  buildGoogleApiClient();
        Intent s=new Intent(MainPage.this, com.example.devicecontrollernew.Location.class);
        startService(s);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer = findViewById(R.id.drawer_layout);
     //   setupToolbar();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final Button b1 = findViewById(R.id.btn1);
        b1.setOnLongClickListener(new Button.OnLongClickListener() {


            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Reset...");
                alert.setMessage("Are you sure?");
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        b1.setText("Setup-1");
                    }
                });
                alert.show();
                return false;
            }
        });



        final Button b2 = findViewById(R.id.btn2);
        b2.setOnLongClickListener(new Button.OnLongClickListener() {


            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Reset...");
                alert.setMessage("Are you sure?");
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        b2.setText("Setup-2");
                    }
                });
                alert.show();
                return false;
            }
        });



        final Button b3 = findViewById(R.id.btn3);
        b3.setOnLongClickListener(new Button.OnLongClickListener() {


            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Reset...");
                alert.setMessage("Are you sure?");
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        b3.setText("Setup-3");
                    }
                });
                alert.show();
                return false;
            }
        });
    }
    private void requestMessagePermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{SEND_SMS},1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                requestPermission();
                client = LocationServices.getFusedLocationProviderClient(this);
                if (ActivityCompat.checkSelfPermission(MainPage.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return false;
                }
                client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null)
                        {
                                    value_lat=String.valueOf(location.getLatitude());
                                    value_lng=String.valueOf(location.getLongitude());
//                                //Toast.makeText(getApplicationContext(),location.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.device_dialogue, null);
                final EditText Name = alertLayout.findViewById(R.id.name);
                final  EditText Number = alertLayout.findViewById(R.id.number);
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
                        String number = Number.getText().toString().trim();
                         boolean d1=false,d2=false,d3=false;
//                        b1.setText(label);
                        if (!TextUtils.isEmpty(name)) {

//
                         //   userid = databaseDevice.push().getKey();
                            Device device = new Device(userid, name, number, Double.parseDouble(value_lat),Double.parseDouble(value_lng),d1,d2,d3);
                            databaseDevice.push().setValue("ID: " + id + "Name: " + name + "Number " + number + "Latitude: " + value_lat + "Longitude: " + value_lat+"Device1: "+d1+"Device2: "+d2+"Device3: "+d3);
                            databaseDevice.child(userid).setValue(device).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                 Log.e( "nosave",e+"");
                                }
                            });
//
                        }
//
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

                return true;
            case R.id.item5:
                requestPermission();
                client = LocationServices.getFusedLocationProviderClient(this);
                if (ActivityCompat.checkSelfPermission(MainPage.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return false;
                }
                client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null)
                        {
                            value_lat=String.valueOf(location.getLatitude());
                            value_lng=String.valueOf(location.getLongitude());
//                                //Toast.makeText(getApplicationContext(),location.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                inflater = getLayoutInflater();
                alertLayout = inflater.inflate(R.layout.device_dialogue, null);
                final EditText newname = alertLayout.findViewById(R.id.name);
                final EditText newnumber = alertLayout.findViewById(R.id.number);
//                final CheckBox cbToggle = alertLayout.findViewById(R.id.radiooptions);


                 alert = new AlertDialog.Builder(this);
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

                       final String name = newname.getText().toString().trim();
                       final String number = newnumber.getText().toString().trim();
//                        b1.setText(label);
                        if (!TextUtils.isEmpty(name)) {

                            if (userid != null) {
                                databaseDevice.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {
                                        double lat = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                                        double lon = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                                        boolean d1=Boolean.parseBoolean(dataSnapshot.child("d1").getValue().toString());
                                        boolean d2=Boolean.parseBoolean(dataSnapshot.child("d2").getValue().toString());
                                        boolean d3=Boolean.parseBoolean(dataSnapshot.child("d3").getValue().toString());
                                        Device device = new Device(userid, name, number, lat, lon,d1,d2,d3);
                                        databaseDevice.push().setValue("ID: " + id + "Name: " + name + "Number " + number + "Latitude: " + lat + "Longitude: " + lon+"Device1: "+d1+"Device2: "+d2+"Device3: "+d3);
                                        databaseDevice.child(userid).setValue(device).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("nosave", e + "");


                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
//
                            }
//
                        }
                    }
                });

//
                dialog = alert.create();
                dialog.show();

                return true;
            case R.id.item2:

                Toast.makeText(getApplicationContext(), userid, Toast.LENGTH_LONG).show();
                return true;
//            case R.id.item3:
//                return true;

            case R.id.item4:
                LOGOUT();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
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
            case R.id.nav_home:
                Intent i= new Intent(this,MainPage.class);
                startActivity(i);
                break;
            case R.id.nav_Aboutus:
                break;
            case R.id.nav_Instructions:
                Button b1 = findViewById(R.id.btn1);
                b1.setVisibility(View.GONE);
                Button b2 = findViewById(R.id.btn2);
                b2.setVisibility(View.GONE);
                Button b3 = findViewById(R.id.btn3);
                b3.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new InstructionsFragment()).commit();

                break;
            case R.id.nav_Website:
                String url="https://www.amazon.in/Febo-Solution-based-switch-Mobile/dp/B074DT34MB?tag=googinhydr18418-21&tag=googinkenshoo-21&ascsubtag=_k_Cj0KCQiAtvPjBRDPARIsAJfZz0ovYujAb_7h6GjWj0h_xROE89W_OQEe1aspmwpp1j_fXIuGsfOd1N8aAuagEALw_wcB_k_&gclid=Cj0KCQiAtvPjBRDPARIsAJfZz0ovYujAb_7h6GjWj0h_xROE89W_OQEe1aspmwpp1j_fXIuGsfOd1N8aAuagEALw_wcB";
                i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.nav_Help:
                Button b4 = findViewById(R.id.btn1);
                b4.setVisibility(View.GONE);
                Button b5 = findViewById(R.id.btn2);
                b5.setVisibility(View.GONE);
                Button b6 = findViewById(R.id.btn3);
                b6.setVisibility(View.GONE);

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
        final RadioGroup cbToggle = alertLayout.findViewById(R.id.radiooptions);


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
                final String m=message.getText().toString().trim().toLowerCase();
            databaseDevice.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String number = dataSnapshot.child("number").getValue().toString();
                        boolean d1;
                        boolean d2=Boolean.parseBoolean(dataSnapshot.child("d2").getValue().toString());
                        boolean d3=Boolean.parseBoolean(dataSnapshot.child("d3").getValue().toString());
                        String id=dataSnapshot.child("id").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        // TODO : add code to send sms
                        requestMessagePermission();
                        if (ActivityCompat.checkSelfPermission(MainPage.this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                            Toast.makeText(getApplicationContext(),"Please grant permission first and then try again.", Toast.LENGTH_LONG).show();
                        }
                        try
                        {
                            String mess="";
                            if(m.compareTo("on")==0) {
                                mess = "R1";
                                d1=true;
                            }
                            else {
                                mess = "O1";
                                d1=false;
                            }
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(number, null,mess, null, null);
                            Device device = new Device(userid, name, number, Double.parseDouble(value_lat), Double.parseDouble(value_lng),d1,d2,d3);
                            databaseDevice.push().setValue("ID: " + id + "Name: " + name + "Number " + number + "Latitude: " + value_lat + "Longitude: " + value_lng+"Device1: "+d1+"Device2: "+d2+"Device3: "+d3);
                            databaseDevice.child(userid).setValue(device).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("nosave", e + "");


                                }
                            });
                            Toast.makeText(getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Kindly grant message permission first", Toast.LENGTH_SHORT).show();
                        }
                     //   Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//                String number= databaseDevice.child(userid);
                if(label!="")
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
        final RadioGroup cbToggle = alertLayout.findViewById(R.id.radiooptions);


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
                String label = Label.getText().toString().trim();
                Button b1=(Button)findViewById(R.id.btn2);
                final String m=message.getText().toString();
                if(label!="")
                b1.setText(label);
                databaseDevice.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String number = dataSnapshot.child("number").getValue().toString();
                        boolean d1=Boolean.parseBoolean(dataSnapshot.child("d1").getValue().toString());
                        boolean d2=Boolean.parseBoolean(dataSnapshot.child("d2").getValue().toString());
                        boolean d3=Boolean.parseBoolean(dataSnapshot.child("d3").getValue().toString());
                        String id=dataSnapshot.child("id").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        // TODO : add code to send sms
                        requestMessagePermission();
                        if (ActivityCompat.checkSelfPermission(MainPage.this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                            Toast.makeText(getApplicationContext(),"Please grant permission first and then try again.", Toast.LENGTH_LONG).show();
                        }
                        try
                        {
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(number, null, m, null, null);
                            Device device = new Device(userid, name, number, Double.parseDouble(value_lat), Double.parseDouble(value_lng),d1,d2,d3);
                            databaseDevice.push().setValue("ID: " + id + "Name: " + name + "Number " + number + "Latitude: " + value_lat + "Longitude: " + value_lng+"Device1: "+d1+"Device2: "+d2+"Device3: "+d3);
                            databaseDevice.child(userid).setValue(device).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("nosave", e + "");


                                }
                            });
                            Toast.makeText(getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Kindly grant message permission first", Toast.LENGTH_SHORT).show();
                        }
                        //   Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
        final RadioGroup cbToggle = alertLayout.findViewById(R.id.radiooptions);


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
                final String m=message.getText().toString();
                Button b1=(Button)findViewById(R.id.btn3);
                if(label!="")
                b1.setText(label);
                databaseDevice.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String number = dataSnapshot.child("number").getValue().toString();
                        boolean d1=Boolean.parseBoolean(dataSnapshot.child("d1").getValue().toString());
                        boolean d2=Boolean.parseBoolean(dataSnapshot.child("d2").getValue().toString());
                        boolean d3=Boolean.parseBoolean(dataSnapshot.child("d3").getValue().toString());
                        String id=dataSnapshot.child("id").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        // TODO : add code to send sms
                        requestMessagePermission();
                        if (ActivityCompat.checkSelfPermission(MainPage.this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                            Toast.makeText(getApplicationContext(),"Please grant permission first and then try again.", Toast.LENGTH_LONG).show();
                        }
                        try
                        {
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(number, null, m, null, null);
                            Device device = new Device(userid, name, number, Double.parseDouble(value_lat), Double.parseDouble(value_lng),d1,d2,d3);
                            databaseDevice.push().setValue("ID: " + id + "Name: " + name + "Number " + number + "Latitude: " + value_lat + "Longitude: " + value_lng+"Device1: "+d1+"Device2: "+d2+"Device3: "+d3);
                            databaseDevice.child(userid).setValue(device).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("nosave", e + "");


                                }
                            });
                            Toast.makeText(getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Kindly grant message permission first", Toast.LENGTH_SHORT).show();
                        }
                        //   Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void draweropen(View view) {
       drawer.openDrawer(GravityCompat.START);
    }
}
