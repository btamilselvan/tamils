package com.success.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
@Profile("keycloak")
public class PersonSecurityConfigKeycloak extends KeycloakWebSecurityConfigurerAdapter {

  @Autowired public KeycloakClientRequestFactory keycloakClientRequestFactory;

  @Bean
  public KeycloakRestTemplate keycloakRestTemplate() {
    return new KeycloakRestTemplate(keycloakClientRequestFactory);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {

    KeycloakAuthenticationProvider kcProvider = new KeycloakAuthenticationProvider();
    // this is needed to override the default "ROLES_" prefix in the role name
    SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
    //    grantedAuthorityMapper.setPrefix(""); //override the default ROLES_ prefix
    // we could also uppercase, lowercase the role names, and set default authorities, etc
    kcProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
    auth.authenticationProvider(kcProvider);
  }

  @Bean
  public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
    // this is needed to make keycloak to use application.properties instead of keycloak.json
    return new KeycloakSpringBootConfigResolver();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/private/**").hasRole("app").anyRequest().permitAll();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }
}
