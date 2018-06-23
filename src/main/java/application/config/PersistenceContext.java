package application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan
@EnableNeo4jRepositories
public class PersistenceContext {
    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://35.229.18.60")
                .credentials("neo4j", "0708")
                .build();
    }
}
