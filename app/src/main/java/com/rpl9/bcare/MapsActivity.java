package com.rpl9.bcare;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private String API_KEY_SERVER = "AIzaSyBUXilhwtviRF4ElB7VTtHbRfKNHA_PwVk";
    private GoogleMap mGoogleMap;
    private ProgressDialog progressDialog;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private double mLatitude = 0;
    double mLongitude = 0;
    String[] permissions = new String[]{
            android.Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.ACCESS_FINE_LOCATION};

    private static final int REQUEST_MULTIPLE_PERMISSIONS = 117;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button btnFind = (Button) findViewById(R.id.btnbrate);
        boolean perms = checkPermissions();
        if (perms) {
            initMap();

            if (savedInstanceState != null) {
                mLatitude = savedInstanceState.getParcelable(KEY_LOCATION);
                mLongitude = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
            }
        }
        // cek google play services
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    LatLng surapati = new LatLng(-6.8992403,107.6238585);
                    LatLng tamansari = new  LatLng(-6.8955773,107.6070404);
                    LatLng Soehat = new LatLng(-6.9257409, 107.5753281);
                    LatLng cipgnti = new LatLng(-6.8968236,107.6012072);
                    LatLng sarijadi = new LatLng(-6.8806265,107.5765564);
                    LatLng cmbluit = new LatLng(-6.880782,107.6038975);
                    LatLng cgdung = new LatLng(-6.8919538,107.6313056);
                    LatLng dg = new LatLng(-6.8721151,107.6192886);



                    googleMap.addMarker(new MarkerOptions().position(surapati).title("Pada pukul 00.00 dini hari, tidak dianjurkan melewati jalan ini "));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(surapati));
                    googleMap.addMarker(new MarkerOptions().position(tamansari).title("Pada pukul 00.00 dini hari, tidak dianjurkan melewati jalan ini"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(tamansari));
                    googleMap.addMarker(new MarkerOptions().position(Soehat).title("Pada pukul 00.00 dini hari, tidak dianjurkan melewati jalan ini"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(Soehat));
                    googleMap.addMarker(new MarkerOptions().position(cipgnti).title("Pada pukul 00.00 dini hari, tidak dianjurkan melewati jalan ini"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(cipgnti));
                    googleMap.addMarker(new MarkerOptions().position(sarijadi).title("Pada pukul 00.00 dini hari, tidak dianjurkan melewati jalan ini"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sarijadi));
                    googleMap.addMarker(new MarkerOptions().position(dg).title("Pada pukul 00.00 dini hari, tidak dianjurkan melewati jalan ini"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(dg));
                    googleMap.addMarker(new MarkerOptions().position(cmbluit).title("Pada pukul 00.00 dini hari, tidak dianjurkan melewati jalan ini"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(cmbluit));
                    googleMap.addMarker(new MarkerOptions().position(cgdung).title("Pada pukul 00.00 dini hari, tidak dianjurkan melewati jalan ini"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(cgdung));


                    initMap();

                }
            });


            btnFind.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(MapsActivity.this, "Coming Soon..!!!", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    private void initMap() {
        if (mGoogleMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mGoogleMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
        }
    }


    //download json data from url
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        if (mGoogleMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mGoogleMap.getCameraPosition());
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
            outState.putParcelable(KEY_LOCATION, locationManager.getLastKnownLocation(provider));
            super.onSaveInstanceState(outState);
        }
    }

    //download Google Places
    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MapsActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Loading Peta");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            new ParserTask().execute(result);
        }

    }

    //parse the Google Places in JSON format
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            ParserJSONPlace parserJSONPlace = new ParserJSONPlace();

            try {
                jObject = new JSONObject(jsonData[0]);
                places = parserJSONPlace.parse(jObject);
                Log.d("coba", jsonData.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            // clear all map sebelumnya
            mGoogleMap.clear();

            for (int i = 0; i < list.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = list.get(i);

                double lat = Double.parseDouble(hmPlace.get("lat"));
                double lng = Double.parseDouble(hmPlace.get("lng"));

                String nama = hmPlace.get("place_name");
                String namaJln = hmPlace.get("vicinity");


                LatLng latLng = new LatLng(lat, lng);

                markerOptions.position(latLng);
                markerOptions.title(nama + " : " + namaJln);

                mGoogleMap.addMarker(markerOptions);

            }

            int totjal = list.size();
            Toast.makeText(MapsActivity.this, "Menemukan " + totjal + " jalan rawan", Toast.LENGTH_LONG).show();
        }

    }
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    finish();
                    Toast.makeText(MapsActivity.this, "Please Grant All Permission to Use All Feature", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}