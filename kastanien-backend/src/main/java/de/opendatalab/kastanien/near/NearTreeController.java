package de.opendatalab.kastanien.near;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.opendatalab.kastanien.data.NearUserQuery;

@RestController
@RequestMapping("/tree/near")
public class NearTreeController {

	@Autowired
	private NearService nearService;

	@RequestMapping(method = RequestMethod.GET)
    public Resource<GeoResultsWithBoundingBox> get(NearUserQuery nearUserQuery) {
        GeoResultsWithBoundingBox result = nearService.findNearTrees(nearUserQuery);
        if (result.isEmpty()) {
			nearUserQuery.setDistance(100);
			result = nearService.findNearTrees(nearUserQuery);
		}
        return new Resource<GeoResultsWithBoundingBox>(result);
    }



}
