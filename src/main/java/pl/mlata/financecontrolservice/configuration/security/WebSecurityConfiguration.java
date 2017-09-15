package pl.mlata.financecontrolservice.configuration.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import pl.mlata.financecontrolservice.configuration.security.authentication.JwtAuthenticationProvider;
import pl.mlata.financecontrolservice.configuration.security.authentication.JwtLoginAuthenticationProvider;
import pl.mlata.financecontrolservice.configuration.security.authentication.JwtLoginFilter;
import pl.mlata.financecontrolservice.configuration.security.authentication.JwtTokenAuthenticationProcessingFilter;
import pl.mlata.financecontrolservice.configuration.security.authentication.extractor.TokenExtractor;
import pl.mlata.financecontrolservice.rest.service.TokenAuthenticationService;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private JwtLoginAuthenticationProvider jwtLoginAuthenticationProvider;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    @Autowired
    private TokenExtractor tokenExtractor;
    @Autowired
    private CorsFilter corsFilter;

    private final String loginEntryPoint = "/auth/login";
    private final String registrationEntryPoint = "/auth/registration";
    private final String h2consoleEntryPoint = "/h2-console/**";

    protected JwtLoginFilter buildJwtLoginFilter() throws Exception {
        JwtLoginFilter filter = new JwtLoginFilter(loginEntryPoint, this.authenticationManager(), failureHandler, tokenAuthenticationService);
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(loginEntryPoint, registrationEntryPoint, h2consoleEntryPoint);
        SkipRequestPathMatcher matcher = new SkipRequestPathMatcher(pathsToSkip, "/**");
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(matcher, failureHandler, tokenExtractor);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.authenticationProvider(jwtLoginAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(loginEntryPoint, registrationEntryPoint, h2consoleEntryPoint).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and()
                .addFilterBefore(
                        buildJwtLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        buildJwtAuthenticationProcessingFilter(),
                        UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(corsFilter, ChannelProcessingFilter.class);
    }

}
