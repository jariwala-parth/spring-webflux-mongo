package codes.parth.spring_webflux_mongo.repositories;

import codes.parth.spring_webflux_mongo.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

  Flux<Product> findByName(String name);
}
