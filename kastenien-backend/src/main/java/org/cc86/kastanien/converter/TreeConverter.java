package org.cc86.kastanien.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.cc86.kastanien.data.GeoPoint;
import org.geojson.*;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by adrian on 23.10.14.
 */
public class TreeConverter {

	private String fileName;

	public TreeConverter(String fileName) {
		this.fileName = fileName;
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
		objectMapper.writeValue(new FileOutputStream("kastanien.geojson"), result);
	}

	private GeoPoint convert(GeoJsonObject geometry) {
		if (geometry instanceof MultiPoint) {

		}
		else if (geometry instanceof  Point) {
			
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			new TreeConverter(args[0]).run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
