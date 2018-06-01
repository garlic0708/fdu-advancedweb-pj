package application;

import org.springframework.context.annotation.Bean;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public class PersistenceContext {
    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://localhost")
                .credentials("neo4j", "123456")
                .build();
    }
}
