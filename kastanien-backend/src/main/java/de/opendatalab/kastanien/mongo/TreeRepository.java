package de.opendatalab.kastanien.mongo;

import de.opendatalab.kastanien.Tree;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TreeRepository extends MongoRepository<Tree, String> {

}
