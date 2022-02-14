package com.success;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
// import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaClient
public class AddressApplicaiton extends SpringBootServletInitializer {

  private static ConfigurableApplicationContext context;

  public static void main(String[] args) {
    context = SpringApplication.run(AddressApplicaiton.class, args);
  }

  public static void restart() {
    ApplicationArguments args = context.getBean(ApplicationArguments.class);
    Thread t =
        new Thread(
            () -> {
              context.close();
              context = SpringApplication.run(AddressApplicaiton.class, args.getSourceArgs());
            });
    t.setDaemon(false); // we want the main thread to wait until restart complete
    t.start();
  }
}
