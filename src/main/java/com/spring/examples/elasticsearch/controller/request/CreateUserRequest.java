package com.spring.examples.elasticsearch.controller.request;

import lombok.Data;

@Data
public class CreateUserRequest {
  private String firstName;
  private String lastName;
}
