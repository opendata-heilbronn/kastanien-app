package org.cc86.kastanien;

import org.cc86.kastanien.data.NearUserQuery;
import org.cc86.kastanien.data.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian on 23.10.14.
 */
@Service
public class NearService {

	@Autowired
	private MongoOperations mongoOperations;
	@Autowired
	private EntityLinks entityLinks;

	public List<NearTree> findNearTrees(NearUserQuery nearUserQuery) {
		Point location = new Point(nearUserQuery.getLongitude(), nearUserQuery.getLatitude());
		NearQuery query = NearQuery.near(location).maxDistance(
				new Distance(nearUserQuery.getDistance(), Metrics.KILOMETERS));

		List<NearTree> result = new ArrayList<>();
		GeoResults<Tree> geoResults = mongoOperations.geoNear(query, Tree.class);
		for (GeoResult<Tree> geoResult : geoResults) {
			Link link = entityLinks.linkToSingleResource(Tree.class,
					geoResult.getContent().getTreeId()).withSelfRel();
			result.add(new NearTree(new Resource<Tree>(geoResult.getContent(), link), geoResult
					.getDistance().getValue()));
		}
		return result;
	}

}