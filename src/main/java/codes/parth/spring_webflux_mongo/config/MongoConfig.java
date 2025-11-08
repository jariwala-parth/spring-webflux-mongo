package codes.parth.spring_webflux_mongo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories(basePackages = {"codes.parth.spring_webflux_mongo.repositories"})
@Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {

  @Value("${spring.data.mongodb.url}")
  private String dataSourceUrl;

  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create();
  }

  @Override
  protected String getDatabaseName() {
    return "reactive-test";
  }

  @Override
  @Bean
  public MongoClient reactiveMongoClient() {

    CodecRegistry codecRegistry =
        CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry());

    MongoClientSettings settings =
        MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(dataSourceUrl))
            .codecRegistry(codecRegistry)
            .build();

    return MongoClients.create(settings);
  }
}
