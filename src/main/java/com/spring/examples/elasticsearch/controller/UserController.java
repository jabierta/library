package com.spring.examples.elasticsearch.controller;

import com.spring.examples.elasticsearch.controller.request.CreateUserRequest;
import com.spring.examples.elasticsearch.domain.Book;
import com.spring.examples.elasticsearch.domain.User;
import com.spring.examples.elasticsearch.service.ActivityService;
import com.spring.examples.elasticsearch.service.BookService;
import com.spring.examples.elasticsearch.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final BookService bookService;
  private final ActivityService activityService;

  @PostMapping("/user")
  private User create(@RequestBody CreateUserRequest createUserRequest) {
    return userService.create(createUserRequest.getFirstName(), createUserRequest.getLastName());
  }

  @GetMapping("/user")
  private List<User> list() {
    return userService.list();
  }

  @GetMapping("/getFavouriteBook")
  private Book getFavouriteBook(
      @RequestParam(name = "userId", required = true) String userId,
      @RequestParam(name = "month", required = false) Integer month,
      @RequestParam(name = "year", required = true) Integer year) {
    return bookService.find(activityService.getFavouriteBook(userId, month, year));
  }

  @GetMapping("/averageHoldOnBook")
  private Integer averageHoldOnBook(
      @RequestParam(name = "userId", required = true) String userId) {
    return (int) activityService.getAverageHoldOnBooksByUserId(userId);
  }
}
