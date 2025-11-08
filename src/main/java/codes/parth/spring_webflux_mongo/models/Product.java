package codes.parth.spring_webflux_mongo.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
@Builder
public class Product {
  @Id private String id;
  private String name;
  private double price;
  private int stock;
}
