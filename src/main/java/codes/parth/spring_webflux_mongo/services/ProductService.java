package codes.parth.spring_webflux_mongo.services;

import codes.parth.spring_webflux_mongo.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
  Mono<Product> saveProduct(Product product);

  Mono<Product> getProductById(String id);

  Flux<Product> getAllProducts();

  Mono<Product> updateProduct(String id, Product product);

  Mono<Void> deleteProduct(String id);
}
