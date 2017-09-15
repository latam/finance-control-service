package pl.mlata.financecontrolservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import aspect.logging.AspectLogger;

@Configuration
@EnableAspectJAutoProxy
public class AspectLoggerConfiguration {
    @Bean
    @Profile("dev")
    public AspectLogger aspectLogger(Environment env) {
        return new AspectLogger(env);
    }
}
