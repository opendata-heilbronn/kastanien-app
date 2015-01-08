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
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;


public class MainActivity extends Activity implements LocationListener, de.opendatalab.kastanien.ResultHandler<GeoResultsWithBoundingBox>

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
    public void onResponse(GeoResultsWithBoundingBox response) {
        for (NearTree nearTree : response.getTrees()) {
            Tree tree = nearTree.getContent().getContent();
            GeoPoint metapos = new GeoPoint(tree.getLocation().getLatitude(), tree.getLocation().getLongitude());

            Marker mt = new Marker(mapView);
            mt.setPosition(metapos);
            mt.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mt.setIcon(getResources().getDrawable(R.drawable.tree_icon));
            mt.setSnippet("Kastanie " + nearTree.getDistance() + "marker entfernt");
            mt.setInfoWindow(new MarkerPopup(mapView, this));
            mt.setRelatedObject(tree);
            mapView.getOverlays().add(mt);
        }
        BoundingBox bb = response.getBoundingBox();
        mapController.zoomToSpan(new BoundingBoxE6(bb.getNorth(), bb.getEast(), bb.getSouth(), bb.getWest()));
    }

    public void addTrees() {
        new de.opendatalab.kastanien.TreeLoaderTask(lastPosition, 25, this).execute();
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
        private Marker marker;

        public MarkerPopup(MapView mapView, MainActivity intenttarget) {
            super(R.layout.bonuspack_bubble, mapView);
            ma = intenttarget;
            Button btn = (Button) (mView.findViewById(R.id.bubble_moreinfo));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(ma, de.opendatalab.kastanien.TreeInfo.class);
                    Tree relatedObject = (Tree) marker.getRelatedObject();
                    try {
                        String metadata = new ObjectMapper().writeValueAsString(relatedObject);
                        myIntent.putExtra("treeData", metadata); //Optional parameters
                        myIntent.putExtra("treeID", new double[]{marker.getPosition().getLatitude(), marker.getPosition().getLongitude()});
                        ma.startActivity(myIntent);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }


                }
            });

        }

        @Override
        public void onOpen(Object item) {
            marker = (Marker) item;
            super.onOpen(item);
            mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
        }
    }

}
