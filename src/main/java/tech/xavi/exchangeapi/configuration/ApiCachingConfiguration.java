package tech.xavi.exchangeapi.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.xavi.exchangeapi.constants.ExchangeApiConstants;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class ApiCachingConfiguration implements ExchangeApiConstants {


    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(API_CACHE_NAME);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(CACHE_TTL_MS, TimeUnit.MILLISECONDS);
    }
}
