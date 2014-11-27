package de.opendatalab.kastanien;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by tgoerner on 27.11.2014.
 */
public class TreeLoaderTask extends AsyncTask<Void,Void,List<de.opendatalab.kastanien.Tree>> {

    private Location position;
    private int radius;

    public TreeLoaderTask(Location position, int radius) {
        this.position = position;
        this.radius = radius;
    }

    @Override
    protected List<de.opendatalab.kastanien.Tree> doInBackground(Void... voids) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> respWrapped = rt.getForEntity("192.168.10.160:8080/tree/near?latitude={0}&longitude={1}",String.class,49.0,9);
        String result=respWrapped.getBody();
        Log.i("CastaneaTask",result);
        return null;
    }
}
