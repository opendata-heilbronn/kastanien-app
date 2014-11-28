package de.opendatalab.kastanien.converter;

import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.Point;

import de.opendatalab.kastanien.GeoPoint;
import de.opendatalab.kastanien.Tree;

public class OsmTreeConverter extends TreeConverter {

    @Override
    public Tree toTree(Feature feature) {
        Tree tree = new Tree();
        tree.setTreeType((String) feature.getProperty("genus:de"));
        if (tree.getTreeType() == null) {
            tree.setTreeType((String) feature.getProperty("genus"));
        }
        Point multiPoint = (Point) feature.getGeometry();
        LngLatAlt lngLatAlt = multiPoint.getCoordinates();
        tree.setLocation(new GeoPoint(lngLatAlt.getLatitude(), lngLatAlt.getLongitude()));
        convertProperties(feature, tree);
        return tree;
    }
}
