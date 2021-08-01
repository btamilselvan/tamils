package com.success.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

  @Autowired private RestartEndpoint restartEndPoint;

  public void restart() {
    restartEndPoint.restart();
  }
}
