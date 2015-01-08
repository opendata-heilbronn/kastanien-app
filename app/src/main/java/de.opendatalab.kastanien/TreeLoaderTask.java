package de.opendatalab.kastanien;

import android.location.Location;
import android.os.AsyncTask;

import org.springframework.web.client.RestTemplate;

public class TreeLoaderTask extends AsyncTask<Void, Void, GeoResultsWithBoundingBox> {

    private static final String URL = "http://kastanien.grundid.de/tree/near?latitude={0}&longitude={1}&distance={2}";
    private Location position;
    private int radius;
    private de.opendatalab.kastanien.ResultHandler<GeoResultsWithBoundingBox> responseHandler;

    public TreeLoaderTask(Location position, int radius, de.opendatalab.kastanien.ResultHandler<GeoResultsWithBoundingBox> responseHandler) {
        this.position = position;
        this.radius = radius;
        this.responseHandler = responseHandler;
    }

    @Override
    protected GeoResultsWithBoundingBox doInBackground(Void... voids) {
        RestTemplate rt = new RestTemplate();
        GeoResultsWithBoundingBox geoResultsWithBoundingBox =
                rt.getForObject(URL, GeoResultsWithBoundingBox.class, position.getLatitude(), position.getLongitude(), radius);
        return geoResultsWithBoundingBox;
    }

    @Override
    protected void onPostExecute(GeoResultsWithBoundingBox geoResultsWithBoundingBox) {
        responseHandler.onResponse(geoResultsWithBoundingBox);
    }
}
