package tech.xavi.exchangeapi.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class ApiCachingConfiguration {

    @Value("${api.cfg.cache.ttl-ms}")
    private long ttlMsCacheConfiguration;
    public static final String CACHE_NAME = "api_cache_name";

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(CACHE_NAME);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(ttlMsCacheConfiguration, TimeUnit.MILLISECONDS);
    }
}
