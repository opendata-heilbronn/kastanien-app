package de.opendatalab.kastanien.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/importTrees")
public class ImportController {

	@Autowired
	private ImportService importService;

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		long trees = importService.importTrees();
		return "OK, trees: " + trees;
	}
}
