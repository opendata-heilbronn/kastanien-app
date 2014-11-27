package de.opendatalab.kastanien;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;


public class MainActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, LocationListener

{
    private Location lastPosition = null;
    private Marker lastPositionMarker = null;

    private TextView hw;
    private MapView mapView;
    private MapController mapController;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //addTrees();
        ((SeekBar) findViewById(R.id.distanceSelector)).setOnSeekBarChangeListener(this);

        mapView = (MapView) findViewById(R.id.mmap);
        mapView.setBuiltInZoomControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(10);
        mapController.setCenter(new GeoPoint(49.9, 9.05));
        mapView.setMultiTouchControls(true);

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
        if (v.getId() == R.id.button) {
            // addTrees();


        } else {

        }
    }

    public void addTrees() {


        new de.opendatalab.kastanien.TreeLoaderTask(lastPosition,100000).execute();
        /*

        TODO REFACTOR TO API
        int maxCounter = 0;
        int[] parseListOSM = {R.raw.castanea, R.raw.aesculus};
        for (int parsefiles : parseListOSM) {
            maxCounter = 0;
            FeatureCollection fc = de.opendatalab.kastanien.CastaneaReader.with(this).read(parsefiles);
            for (Feature feature : fc) {
                Point point = (Point) feature.getGeometry();
                LngLatAlt position = point.getCoordinates();
               // String httpquery = "http://nominatim.openstreetmap.org/reverse?format=json&lat="+position.getLatitude()+"&lon="+position.getLongitude()+"&zoom=186addressdetails=1";
                //Log.i("Castanea",response);
                de.opendatalab.kastanien.TreeMeta tm = new de.opendatalab.kastanien.TreeMeta("unbekannt", "unbekannt", "unbekannt", feature);
                GeoPoint metapos = new GeoPoint(position.getLatitude(), position.getLongitude());

                float[] dest = new float[3];
                Location.distanceBetween(lastPosition.getLatitude(), lastPosition.getLongitude(), metapos.getLatitude(), metapos.getLongitude(), dest);
                if (isNear(dest[0])) {

                    Marker mt = new Marker(mapView);
                    mt.setPosition(metapos);
                    mt.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    mt.setIcon(getResources().getDrawable(R.drawable.tree_icon));
                    mt.setSnippet("Kastanie " + dest[0] + "m entfernt");
                    mt.setInfoWindow(new MarkerPopup(mapView, this));
                    mt.setRelatedObject(tm);
                    mapView.getOverlays().add(mt);
                }
                if (maxCounter++ > 100) {
                    break;
                }
            }

        }*/
    }

    private boolean isNear(float v) {
        return v < 100000; // 100 km
    }


    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }


    public void onNewPosition() {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();


        if (lastPosition != null) {

            GeoPoint position = new GeoPoint(lastPosition.getLatitude(), lastPosition.getLongitude());
            OverlayItem positionRef = new OverlayItem("Ich", "Hier bin ich", position);
            if (lastPositionMarker == null) {
                lastPositionMarker = new Marker(mapView);
                lastPositionMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                lastPositionMarker.setSnippet("Standort");
                lastPositionMarker.setIcon(getResources().getDrawable(R.drawable.flag_icon));
                lastPositionMarker.setTitle("Standort");
                mapView.getOverlays().add(lastPositionMarker);
                mapController.setCenter(position);
                mapController.setZoom(14);

            }
            lastPositionMarker.setPosition(position);

            mapView.invalidate();
        }
        addTrees();
    }

    protected void onStart() {
        super.onStart();
        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 1000, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 1000, this);
        }
    }

    @Override
    protected void onStop() {
        if (locationManager != null)
            locationManager.removeUpdates(this);
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

    @Override
    public void onLocationChanged(Location location) {
        Log.i("KASTANIEN", "New Position: " + location);
        lastPosition = location;
        onNewPosition();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }





    public class MarkerPopup extends MarkerInfoWindow {

        private MainActivity ma;
        private Marker m;

        public MarkerPopup(MapView mapView, MainActivity intenttarget) {
            super(R.layout.bonuspack_bubble, mapView);
            ma = intenttarget;
            Button btn = (Button) (mView.findViewById(R.id.bubble_moreinfo));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(ma, de.opendatalab.kastanien.TreeInfo.class);
                    FeatureCollection tw = new FeatureCollection();
                    tw.add(((de.opendatalab.kastanien.TreeMeta) m.getRelatedObject()).getTree());
                    try {
                        String metadata = new ObjectMapper().writeValueAsString(tw);
                        myIntent.putExtra("treeData", metadata); //Optional parameters
                        myIntent.putExtra("treeID", new double[]{m.getPosition().getLatitude(), m.getPosition().getLongitude()});
                        ma.startActivity(myIntent);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }


                }
            });

        }

        @Override
        public void onOpen(Object item) {
            m = (Marker) item;
            super.onOpen(item);
            mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
        }
    }

}
