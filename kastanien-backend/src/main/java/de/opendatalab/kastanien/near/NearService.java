package de.opendatalab.kastanien.near;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import de.opendatalab.kastanien.Tree;
import de.opendatalab.kastanien.data.NearUserQuery;

@Service
public class NearService {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private EntityLinks entityLinks;

    public GeoResultsWithBoundingBox findNearTrees(NearUserQuery nearUserQuery) {
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
        return new GeoResultsWithBoundingBox(result);
    }

}
