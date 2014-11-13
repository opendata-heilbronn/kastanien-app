package org.cc86.kastanien;

import org.cc86.kastanien.data.NearUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by adrian on 23.10.14.
 */
@RestController
@RequestMapping("/tree/near")
public class NearTreeController {

	@Autowired
	private NearService nearService;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<NearTree> get(NearUserQuery nearUserQuery) {
		List<NearTree> result = nearService.findNearTrees(nearUserQuery);
		if (result.isEmpty()) {
			nearUserQuery.setDistance(100);
			result = nearService.findNearTrees(nearUserQuery);
		}
		return new Resources<NearTree>(result);
	}



}
