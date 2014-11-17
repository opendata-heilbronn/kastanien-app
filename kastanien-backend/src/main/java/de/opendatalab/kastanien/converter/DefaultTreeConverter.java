package de.opendatalab.kastanien.converter;

import de.opendatalab.kastanien.GeoPoint;
import de.opendatalab.kastanien.Tree;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.MultiPoint;

public class DefaultTreeConverter extends TreeConverter {

	@Override public Tree toTree(Feature feature) {
		Tree tree = new Tree();
		tree.setTreeType(feature.getProperty("BAUMART"));
		MultiPoint multiPoint = (MultiPoint)feature.getGeometry();
		LngLatAlt lngLatAlt = multiPoint.getCoordinates().get(0);
		tree.setLocation(new GeoPoint(lngLatAlt.getLatitude(), lngLatAlt.getLongitude()));
		convertProperties(feature, tree);
		return tree;
	}
}
