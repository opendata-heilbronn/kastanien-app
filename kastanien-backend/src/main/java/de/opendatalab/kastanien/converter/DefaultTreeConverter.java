package de.opendatalab.kastanien.converter;

import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.MultiPoint;

import de.opendatalab.kastanien.GeoPoint;
import de.opendatalab.kastanien.Tree;

public class DefaultTreeConverter extends TreeConverter {

    @Override
    public Tree toTree(Feature feature) {
        Tree tree = new Tree();
        tree.setTreeType((String) feature.getProperty("BAUMART"));
        MultiPoint multiPoint = (MultiPoint) feature.getGeometry();
        LngLatAlt lngLatAlt = multiPoint.getCoordinates().get(0);
        tree.setLocation(new GeoPoint(lngLatAlt.getLatitude(), lngLatAlt.getLongitude()));
        convertProperties(feature, tree);
        return tree;
    }
}
