package de.opendatalab.kastanien.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.opendatalab.kastanien.GeoPoint;
import de.opendatalab.kastanien.Tree;
import de.opendatalab.kastanien.mongo.TreeRepository;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.MultiPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImportService {

	private static final String[] KNOWN_DATA = { "kastanien.json" };
	@Autowired
	private TreeRepository treeRepository;

	public void importTrees() {
		try {
			InputStream inputStream = ImportService.class.getClassLoader().getResourceAsStream("kastanien.json");
			FeatureCollection featureCollection =
					new ObjectMapper().readValue(inputStream, FeatureCollection.class);
			for (Feature feature : featureCollection) {
				Tree tree =
						toTree(feature);
				treeRepository.save(tree);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Tree toTree(Feature feature) {
		Tree tree = new Tree();
		tree.setTreeType(feature.getProperty("BAUMART"));
		MultiPoint multiPoint = (MultiPoint)feature.getGeometry();
		LngLatAlt lngLatAlt = multiPoint.getCoordinates().get(0);
		tree.setLocation(new GeoPoint(lngLatAlt.getLatitude(), lngLatAlt.getLongitude()));
		Map<String, String> props = new HashMap<>();
		for (Map.Entry<String, Object> entry : feature.getProperties().entrySet()) {
			if (entry.getValue() != null) {
				props.put(entry.getKey(), entry.getValue().toString());
			}
		}
		tree.setProperties(props);
		return tree;
	}
}
