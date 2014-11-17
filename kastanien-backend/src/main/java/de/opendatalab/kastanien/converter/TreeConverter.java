package de.opendatalab.kastanien.converter;

import de.opendatalab.kastanien.Tree;
import org.geojson.Feature;

import java.util.HashMap;
import java.util.Map;

public abstract class TreeConverter {

	public abstract Tree toTree(Feature feature);

	protected void convertProperties(Feature feature, Tree tree) {
		Map<String, String> props = new HashMap<>();
		for (Map.Entry<String, Object> entry : feature.getProperties().entrySet()) {
			if (entry.getValue() != null) {
				props.put(entry.getKey(), entry.getValue().toString());
			}
		}
		tree.setProperties(props);
	}
}
