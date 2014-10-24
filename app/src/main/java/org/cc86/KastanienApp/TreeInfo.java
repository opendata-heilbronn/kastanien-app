package org.cc86.KastanienApp;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.geojson.Feature;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class TreeInfo extends Activity {

    private Feature tree;
    private GoogleMap mmap;
    private LatLng position;

    private void setupmap()
    {

        if(mmap==null)
        {
            FragmentManager f = getFragmentManager();
            MapFragment frg = (MapFragment) f.findFragmentById(R.id.dmap);
            Log.d("ALZR", frg + "");
            mmap = frg .getMap();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_info);
        setupmap();
        double[] pos = getIntent().getDoubleArrayExtra("treeID");
        tree=MainActivity.getMetadata().get(new LatLng(pos[0],pos[1])).getTree();
        ArrayList<String>kvpairs = new ArrayList<String>();
        Map<String,Object> meta = tree.getProperties();
        Set<String> metaIDs = meta.keySet();
        for(String key :metaIDs)
        {
            kvpairs.add(key+": "+meta.get(key));
        }
        ListView v = (ListView) findViewById(R.id.treeDataList);
        v.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kvpairs));
        position=new LatLng(pos[0],pos[1]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mmap.addMarker(new MarkerOptions().title("Aktueller Baum").position(position).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tree_info, menu);
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
}
