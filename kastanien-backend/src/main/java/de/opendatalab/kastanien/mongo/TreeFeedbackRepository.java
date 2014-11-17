package de.opendatalab.kastanien.mongo;

import de.opendatalab.kastanien.TreeFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TreeFeedbackRepository extends MongoRepository<TreeFeedback, String> {

}
