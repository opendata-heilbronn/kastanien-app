
package de.opendatalab.kastanien.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.opendatalab.kastanien.Tree;
import de.opendatalab.kastanien.mongo.TreeRepository;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImportService {

	private static final Map<String, Class<? extends TreeConverter>> KNOWN_DATA = new HashMap<>();

	static {
		KNOWN_DATA.put("kastanien.json", DefaultTreeConverter.class);
		KNOWN_DATA.put("castanea.json", OsmTreeConverter.class);
		KNOWN_DATA.put("aesculus.json", OsmTreeConverter.class);
	}
	@Autowired
	private TreeRepository treeRepository;

	public long importTrees() {
		treeRepository.deleteAll();
		try {
			for (Map.Entry<String, Class<? extends TreeConverter>> entry : KNOWN_DATA.entrySet()) {
				InputStream inputStream = ImportService.class.getClassLoader().getResourceAsStream(entry.getKey());
				FeatureCollection featureCollection =
						new ObjectMapper().readValue(inputStream, FeatureCollection.class);
				TreeConverter treeConverter = entry.getValue().newInstance();
				for (Feature feature : featureCollection) {
					Tree tree = treeConverter.toTree(feature);
					treeRepository.save(tree);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return treeRepository.count();
	}
}
