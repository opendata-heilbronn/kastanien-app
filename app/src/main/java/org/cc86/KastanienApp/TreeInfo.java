package org.cc86.KastanienApp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import org.geojson.Feature;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class TreeInfo extends Activity {

    private Feature tree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_info);
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
