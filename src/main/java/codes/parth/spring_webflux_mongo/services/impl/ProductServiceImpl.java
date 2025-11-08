package codes.parth.spring_webflux_mongo.services.impl;

import codes.parth.spring_webflux_mongo.models.Product;
import codes.parth.spring_webflux_mongo.repositories.ProductRepository;
import codes.parth.spring_webflux_mongo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository repository;

  @Override
  public Mono<Product> saveProduct(Product product) {
    return repository.save(product);
  }

  @Override
  public Mono<Product> getProductById(String id) {
    // findById returns Mono<Product> and handles the case where the product might not exist
    return repository
        .findById(id)
        .switchIfEmpty(
            Mono.error(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id)));
  }

  @Override
  public Flux<Product> getAllProducts() {
    return repository.findAll();
  }

  @Override
  public Mono<Product> updateProduct(String id, Product updatedProduct) {
    return repository
        .findById(id)
        .switchIfEmpty(
            Mono.error(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id)))
        .map(
            existingProduct -> {
              existingProduct.setName(updatedProduct.getName());
              existingProduct.setPrice(updatedProduct.getPrice());
              existingProduct.setStock(updatedProduct.getStock());
              return existingProduct;
            })
        .flatMap(repository::save);
  }

  @Override
  public Mono<Void> deleteProduct(String id) {
    return repository
        .deleteById(id)
        .switchIfEmpty(
            Mono.error(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id)));
  }
}
