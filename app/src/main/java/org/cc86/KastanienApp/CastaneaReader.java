package org.cc86.KastanienApp;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.geojson.FeatureCollection;

import java.io.InputStream;

/**
 * Created by adrian on 10.10.14.
 */
public class CastaneaReader {

    private Context context;

    private CastaneaReader(Context context) {
        this.context = context;
    }

    public static CastaneaReader with(Context context) {
        return new CastaneaReader(context);
    }

    public FeatureCollection read(int ressourceID) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream content = context.getResources().openRawResource(ressourceID);

        try {
            FeatureCollection featureCollection = objectMapper.readValue(content, FeatureCollection.class);
            return featureCollection;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
