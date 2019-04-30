package com.example.devicecontrollernew;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Location extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private static final String LOGSERVICE = "#######";
    private FirebaseDatabase mFirebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseDevice=mFirebaseDatabase.getReference("device");
    FirebaseUser Current = FirebaseAuth.getInstance().getCurrentUser();
    String userid=Current.getUid();
    final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE_BROADCAST_KEY="StopServiceBroadcastKey";
    final static int RQS_STOP_SERVICE = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleApiClient();
        Log.i(LOGSERVICE, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOGSERVICE, "onStartCommand");

        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(LOGSERVICE, "onConnected" + bundle);

        @SuppressLint("MissingPermission") android.location.Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (l != null) {
            Log.i(LOGSERVICE, "lat " + l.getLatitude());
            Log.i(LOGSERVICE, "lng " + l.getLongitude());

        }

        startLocationUpdate();
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOGSERVICE, "onConnectionSuspended " + i);

    }
//    public class NotifyServiceReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context arg0, Intent arg1) {
//
//            int rqs = arg1.getIntExtra(STOP_SERVICE_BROADCAST_KEY, 0);
//
//            if (rqs == RQS_STOP_SERVICE){
//                stopSelf();
//                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
//                        .cancelAll();
//            }
//        }
//    }
    @Override
    public void onLocationChanged(final android.location.Location location) {
      //  Toast.makeText(this, "Changing", Toast.LENGTH_LONG).show();
        Log.i(LOGSERVICE, "lat " + location.getLatitude());
        Log.i(LOGSERVICE, "lng " + location.getLongitude());
       // LatLng mLocation = (new LatLng(location.getLatitude(), location.getLongitude()));
      //  EventBus.getDefault().post(mLocation);
        databaseDevice.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double lat=Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                double lon=Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                double clon=location.getLongitude();
                double clat=location.getLatitude();

                float[] results = new float[1];
                android.location.Location.distanceBetween(lat, lon, clat, clon, results);
                float distance = results[0];

                if(distance>100)
                {
//                    int id=0;
//                    Intent notificationIntent = new Intent(getApplicationContext(),MainPage.class);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
//                            notificationIntent, 0);
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
//                            .setContentTitle("GSM Controller")
//                            .setSmallIcon(R.mipmap.icon1)
//                            .setContentText("Your device is on").setAutoCancel(true)
//                            .setContentIntent(pendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH);
//                    Notification notification = builder.build();
//
//                    Uri path= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    builder.setSound(path);
//                  NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                    {
//                        String channelId="Gsm Controller";
//                        NotificationChannel chan = new NotificationChannel(channelId, "Test", NotificationManager.IMPORTANCE_NONE);
//                        chan.enableLights(false);
//                        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//                        notificationManager.createNotificationChannel(chan);
//                        builder.setChannelId(channelId);
//                    }
//                    notificationManager.notify(1,notification);
                    Intent notificationIntent = new Intent(getApplicationContext(),MainPage.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                            notificationIntent, 0);
                    String channelId="Gsm Controller";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelId)
                            .setSmallIcon(R.drawable.icon1)
                            .setContentTitle("GSM Controller")
                            .setContentText("Your device is on.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    // Create the NotificationChannel, but only on API 26+ because
                    // the NotificationChannel class is new and not in the support library
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = getString(R.string.channel_name);
                        String description = getString(R.string.channel_description);
                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
                        NotificationChannel channel = new NotificationChannel(channelId, name, importance);
                        channel.setDescription(description);
                        // Register the channel with the system; you can't change the importance
                        // or other notification behaviors after this
                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                    }
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOGSERVICE, "Stopped");

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOGSERVICE, "onConnectionFailed ");

    }
    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }
    private void startLocationUpdate() {
        initLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }
}
