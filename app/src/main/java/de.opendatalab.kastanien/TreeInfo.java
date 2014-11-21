package de.opendatalab.kastanien;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class TreeInfo extends Activity {

    private Feature tree;
    private GeoPoint position;
    private MapView mmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_info);
        double[] pos = getIntent().getDoubleArrayExtra("treeID");
        String treemetayaml = getIntent().getStringExtra("treeData");
        try {
            tree = new ObjectMapper().readValue(treemetayaml, FeatureCollection.class).getFeatures().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        position = new GeoPoint(pos[0], pos[1]);


        ArrayList<String> kvpairs = new ArrayList<String>();
        Map<String, Object> meta = tree.getProperties();
        Set<String> metaIDs = meta.keySet();
        for (String key : metaIDs) {
            kvpairs.add(key + ": " + meta.get(key));
        }
        ListView v = (ListView) findViewById(R.id.treeDataList);
        v.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kvpairs));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mmap = (MapView) findViewById(R.id.dmap);
        mmap.invalidate();
        mmap.getController().setCenter(position);
        mmap.getController().setZoom(12);
        Marker m = new Marker(mmap);
        m.setPosition(position);
        m.setSnippet("Selected Tree");
        mmap.invalidate();
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
