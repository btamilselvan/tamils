package com.success.exceptions;

import javax.annotation.Priority;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyException extends Exception implements ExceptionMapper<MyException> {

  /** */
  private static final long serialVersionUID = 1L;

  public MyException() {}

  public MyException(String message) {
    super(message);
  }

  @Override
  public Response toResponse(MyException exception) {
    return Response.status(404).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
  }
}
