package sashaspivak.usermanagerapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sashaspivak.usermanagerapp.model.User;
import sashaspivak.usermanagerapp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;//bringing the service

    public UserResource(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() throws Throwable{
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

  @GetMapping("/find/{userName}+{password}")
  public ResponseEntity<User> getUserByUserName(@PathVariable("userName") String userName,
                                                @PathVariable("password") String password ) throws Throwable {
    User user = userService.findUserByUserName(userName, password);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/findbyname/{userName}")
  public ResponseEntity<User> getUser(@PathVariable("userName") String userName) throws Throwable {
    User user = userService.findUser(userName);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Throwable{
        //User updateUser = userService.updateUser(user);
        User updateUser = userService.update(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) throws Throwable {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
