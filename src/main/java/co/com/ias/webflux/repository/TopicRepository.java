package co.com.ias.webflux.repository;

import co.com.ias.webflux.model.Topic;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends ReactiveCrudRepository<Topic, Integer> {
}
