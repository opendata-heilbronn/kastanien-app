package org.cc86.KastanienApp;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.Serializable;
import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener,GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,SeekBar.OnSeekBarChangeListener



{
    private boolean connected;

    TextView hw;
    MapView m;
    MapController mc;
    LocationClient mLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationClient = new LocationClient(this, this, this);
        //addTrees();
        ((SeekBar)findViewById(R.id.distanceSelector)).setOnSeekBarChangeListener(this);

        m = (MapView) findViewById(R.id.mmap);
        m.setBuiltInZoomControls(true);
        mc= (MapController) m.getController();
        mc.setZoom(5);
        m.setMultiTouchControls(true);

        // ((LocationManager)getSystemService(Context.LOCATION_SERVICE)).;


    }
    private Location coordsPos=null;




    @Override
    protected void onResume() {
        super.onResume();
        if(connected)
            addTrees();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button)
        {
           // addTrees();



        }
        else
        {

        }
    }

    public void addTrees() {

        int maxCounter = 0;
        //mmap.setInfoWindowAdapter(new TreePopup());
       // mmap.setOnInfoWindowClickListener(this);

        /*FeatureCollection fc = CastaneaReader.with(this).read(R.raw.kastanien);



        for (Feature feature : fc) {
            MultiPoint point = (MultiPoint)feature.getGeometry();
            LngLatAlt position = point.getCoordinates().get(0);
            String kastaniensorte = feature.getProperty("BAUMART");
            String kronendurchmesser=feature.getProperty("KRONE_DM"+"");
            String pflanzjahr=feature.getProperty("PFLANZJAHR")+"";
            String stammumfang=feature.getProperty("STAMMUMFAN")+"";
           /* String additionalInfo = "Kronendurchmesser: "+kronendurchmesser+"\n"+
                    "Stammumfang: "+stammumfang+"\n"+
                    "Pflanzjahr: "+pflanzjahr;*/
        /*
            TreeMeta tm = new TreeMeta(pflanzjahr,kronendurchmesser,stammumfang);
            LatLng metapos = new LatLng(position.getLatitude(), position.getLongitude());
            metadata.put(metapos,tm);
            mmap.addMarker(new MarkerOptions().position(metapos).title(kastaniensorte).snippet("  "));

            if (maxCounter++ > 20) {
                break;
            }
        }*/
        int[] parseListOSM = {R.raw.castanea,R.raw.aesculus};
        for(int parsefiles:parseListOSM)
        {
            maxCounter = 0;
            FeatureCollection fc=CastaneaReader.with(this).read(parsefiles);
            for (Feature feature : fc)
            {
                Point point = (Point)feature.getGeometry();
                LngLatAlt position = point.getCoordinates();


                TreeMeta tm = new TreeMeta("unbekannt","unbekannt","unbekannt",feature);
                GeoPoint metapos = new GeoPoint(position.getLatitude(), position.getLongitude());

                //if(maxCounter==1) {
                    float[] dest = new float[3];
                    Location.distanceBetween(coordsPos.getLatitude(),coordsPos.getLongitude(),metapos.getLatitude(),metapos.getLongitude(),dest);
                    /*Toast.makeText(this, "DST:"+dest[0],
                            Toast.LENGTH_LONG).show();*/

                //}
                //feature.setProperty("DST",dest[0]);
                //feature.setProperty("DST",dest[0]);
                //feature.setProperty("DST",dest[0]);
                if(dest[0]<10000)
                {

                    Marker mt = new Marker(m);
                    mt.setPosition(metapos);
                    mt.setSnippet("Kastanie #XXX");
                    mt.setInfoWindow(new MarkerPopup(m,this));
                    mt.setRelatedObject(tm);
                    m.getOverlays().add(mt);
                    //mmap.addMarker(new MarkerOptions().position(metapos).title(feature.getProperty("genus")+"").snippet("  "));
                }
                if (maxCounter++ > 100) {
                    break;
                }
            }

        }
    }
    /*

        @Override
        public void onInfoWindowClick(Marker marker)
        {
        if(marker.getTitle().equals("Position"))
        {
            return;
        }
        Intent myIntent = new Intent(this, TreeInfo.class);

        //myIntent.putExtra("treeID", new double[]{position.latitude,position.longitude}); //Optional parameters
        this.startActivity(myIntent);
    }
    */





    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            //TO DO errormsg
            //showErrorDialog(connectionResult.getErrorCode());
    }


    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }



    public void onConnected(Bundle dataBundle) {
        // Display the connection status
       Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();


        Location l = mLocationClient.getLastLocation();
        coordsPos=l;
        if(l!=null)
        {

            GeoPoint position = new GeoPoint(l.getLatitude(),l.getLongitude());
            OverlayItem positionRef = new OverlayItem("Ich", "Hier bin ich", position);
            //mmap.addMarker(new MarkerOptions().position(coords).title("Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            //mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords,10));
            Marker pm = new Marker(m);
            pm.setSnippet("Standort");
            pm.setPosition(position);
            pm.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
            pm.setTitle("Standort");
            m.getOverlays().add(pm);

            mc.setCenter(position);
            mc.setZoom(12);
            m.invalidate();
        }
        addTrees();
    }

    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();

    }
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    public class TreeMeta implements Serializable{
        private String treePlantYear;
        private String treeTopSize;
        private String treeTrunkSize;
        private Feature tree;

        public TreeMeta(String treePlantYear, String treeTopSize, String treeTrunkSize,Feature tree) {
            this.treePlantYear = treePlantYear;
            this.treeTopSize = treeTopSize;
            this.treeTrunkSize = treeTrunkSize;
            this.tree=tree;
        }

        public String getTreePlantYear() {
            return treePlantYear;
        }

        public String getTreeTopSize() {
            return treeTopSize;
        }

        public String getTreeTrunkSize() {
            return treeTrunkSize;
        }
        public Feature getTree()
        {
            return tree;
        }
    }


    public class MarkerPopup extends MarkerInfoWindow
    {

        private MainActivity ma;
        private Marker m;
        public MarkerPopup( MapView mapView,MainActivity intenttarget) {
            super(R.layout.bonuspack_bubble, mapView);
            ma=intenttarget;
            Button btn = (Button)(mView.findViewById(R.id.bubble_moreinfo));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(ma, TreeInfo.class);
                    FeatureCollection tw = new FeatureCollection();
                    tw.add(((TreeMeta)m.getRelatedObject()).getTree());
                    try {
                        String metadata = new ObjectMapper().writeValueAsString(tw);
                        myIntent.putExtra("treeData", metadata); //Optional parameters
                        myIntent.putExtra("treeID",new double[]{m.getPosition().getLatitude(),m.getPosition().getLongitude()});
                        ma.startActivity(myIntent);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }


                }
            });

        }

        @Override public void onOpen(Object item){
            m=(Marker)item;
            super.onOpen(item);
            mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
        }
    }

}
