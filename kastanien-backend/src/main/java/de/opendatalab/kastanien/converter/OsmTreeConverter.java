package de.opendatalab.kastanien.converter;

import de.opendatalab.kastanien.GeoPoint;
import de.opendatalab.kastanien.Tree;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.Point;

public class OsmTreeConverter extends TreeConverter {

	@Override
	public Tree toTree(Feature feature) {
		Tree tree = new Tree();
		tree.setTreeType(feature.getProperty("genus:de"));
		if (tree.getTreeType() == null) {
			tree.setTreeType(feature.getProperty("genus"));
		}
		Point multiPoint = (Point)feature.getGeometry();
		LngLatAlt lngLatAlt = multiPoint.getCoordinates();
		tree.setLocation(new GeoPoint(lngLatAlt.getLatitude(), lngLatAlt.getLongitude()));
		convertProperties(feature, tree);
		return tree;
	}
}
