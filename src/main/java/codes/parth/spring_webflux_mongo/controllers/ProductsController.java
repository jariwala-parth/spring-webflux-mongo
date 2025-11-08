package codes.parth.spring_webflux_mongo.controllers;

import codes.parth.spring_webflux_mongo.models.Product;
import codes.parth.spring_webflux_mongo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductsController {

  private final ProductService productService;

  // CREATE
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Product> createProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }

  // READ All
  @GetMapping
  public Flux<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  // READ By ID
  @GetMapping("/{id}")
  public Mono<Product> getProductById(@PathVariable String id) {
    // If findById returns Mono.empty(), Spring WebFlux automatically returns a 404 NOT FOUND.
    return productService.getProductById(id);
  }

  // UPDATE
  @PutMapping("/{id}")
  public Mono<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
    return productService
        .updateProduct(id, product)
        // If the Mono is empty (product not found), return 404 NOT FOUND
        .switchIfEmpty(
            Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")));
  }

  // DELETE
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteProduct(@PathVariable String id) {
    return productService.deleteProduct(id);
  }
}
