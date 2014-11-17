package de.opendatalab.kastanien.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.opendatalab.kastanien.GeoPoint;
import org.apache.commons.io.FileUtils;
import org.geojson.*;

import java.io.File;
import java.io.FileOutputStream;

public class TreeFilter {

	private String fileName;

	public TreeFilter(String fileName) {
		this.fileName = fileName;
	}

	public static void main(String[] args) {
		try {
			new TreeFilter(args[0]).run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String content = FileUtils.readFileToString(new File(
				fileName), "UTF-8");
		FeatureCollection featureCollection = objectMapper.readValue(content, FeatureCollection.class);
		FeatureCollection result = new FeatureCollection();
		int counter = 0;
		for (Feature feature : featureCollection) {
			String baumart = feature.getProperty("BAUMART");
			if (baumart != null && baumart.contains("astanie")) {
				counter++;
				result.add(feature);
			}

			convert(feature.getGeometry());
		}
		objectMapper.writeValue(new FileOutputStream("kastanien.json"), result);
	}

	private GeoPoint convert(GeoJsonObject geometry) {
		if (geometry instanceof MultiPoint) {

		}
		else if (geometry instanceof  Point) {
		}
		return null;
	}

}

