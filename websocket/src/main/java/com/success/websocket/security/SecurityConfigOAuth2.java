package com.success.websocket.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.client.RestTemplate;

import com.success.websocket.security.services.CustomOAuth2UserService;
import com.success.websocket.security.services.CustomOIDCUserService;
import com.success.websocket.utils.Constants;

@Configuration
@EnableWebSecurity
@Profile("oauth2")
public class SecurityConfigOAuth2 extends WebSecurityConfigurerAdapter {
  @Autowired private CustomOAuth2UserService customOAuth2UserService;
  @Autowired private AuthSuccessHandler authSuccessHandler;
  @Autowired private CustomOIDCUserService oidcService;

  @Bean
  public AuthRequestRepoisotry cookieAuthorizationRequestRepository() {
    return new AuthRequestRepoisotry();
  }

  @Bean
  public StrictHttpFirewall httpFirewall() {
    StrictHttpFirewall firewall = new StrictHttpFirewall();
    firewall.setAllowSemicolon(true);
    return firewall;
  }
  /**
   * 1. Session management is stateless. so we need to provide a custom repository to manage the
   * session during authentication process 2. provide a customer service to extract the user
   * information 3. provide auth success handler to handle the redirect. after successful
   * authentication, create jwt token and redirect the request to UI clients 4. provide a token end
   * point intercepter to add token_type parameter in the access token request (for linkedin)
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(Constants.ALLOWED_URLS_LIST.toArray(new String[] {}))
        .permitAll()
        .antMatchers("/auth/**", "/oauth2/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(
            authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
        .oauth2Login()
        .authorizationEndpoint()
        .baseUri("/oauth2/authorize")
        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
        .and()
        .redirectionEndpoint()
        .baseUri("/oauth2/callback/*")
        .and()
        .userInfoEndpoint()
        .userService(customOAuth2UserService)
        .oidcUserService(oidcService)
        .and()
        .successHandler(authSuccessHandler)
        .tokenEndpoint()
        .accessTokenResponseClient(authorizationCodeTokenResponseClient())
        .and()
        .loginPage("/p/mylogin");

    http.headers().frameOptions().disable(); // for h2-console
    http.csrf().disable(); //for h2-console
  }

  @Bean
  public TokenFilter authenticationTokenFilterBean() {
    return new TokenFilter();
  }

  /**
   * before requesting access_token, add token_type request parameter (work around for linkedin)
   *
   * @return
   */
  private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
      authorizationCodeTokenResponseClient() {
    OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter =
        new OAuth2AccessTokenResponseHttpMessageConverter();
    tokenResponseHttpMessageConverter.setTokenResponseConverter(
        new OAuth2AccessTokenResponseConverterWithDefaults());

    RestTemplate restTemplate =
        new RestTemplate(
            Arrays.asList(new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
    restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

    DefaultAuthorizationCodeTokenResponseClient tokenResponseClient =
        new DefaultAuthorizationCodeTokenResponseClient();
    tokenResponseClient.setRestOperations(restTemplate);

    return tokenResponseClient;
  }
}
