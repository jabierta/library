package com.spring.examples.elasticsearch.controller;

import com.spring.examples.elasticsearch.service.UserService;
import com.spring.examples.elasticsearch.controller.request.CreateUserRequest;
import com.spring.examples.elasticsearch.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/user")
  private User test(@RequestBody CreateUserRequest createUserRequest) {
    return userService.create(createUserRequest.getFirstName(), createUserRequest.getLastName());
  }
}
