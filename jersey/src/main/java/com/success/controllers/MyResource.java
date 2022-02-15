package com.success.controllers;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.success.exceptions.MyException;
import com.success.models.Car;
import com.success.services.MyService;

/** Root resource (exposed at "myresource" path) */
@Path("c")
public class MyResource {

  @Inject private MyService service;

  /**
   * Method handling HTTP GET requests. The returned object will be sent to the client as
   * "text/plain" media type.
   *
   * @return String that will be returned as a text/plain response.
   */
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getIt() {
    return service.hello();
  }

  @GET
  @Path("car")
  @Produces(MediaType.APPLICATION_JSON)
  public Car getCar() {
    Car c = new Car();
    c.setModel("Honda");
    c.setName("Acura");
    return c;
  }

  @GET
  @Path("ex")
  @Produces(MediaType.APPLICATION_JSON)
  public Car getException() throws MyException {
    Car c = new Car();
    c.setModel("Honda");
    c.setName("Acura");
    throw new MyException("Not found!!!");
  }
}
