package sashaspivak.usermanagerapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sashaspivak.usermanagerapp.exception.UserNotFoundException;
import sashaspivak.usermanagerapp.model.User;
import sashaspivak.usermanagerapp.model.repo.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //find and return a list of all users
    public List<User> findAllUsers() throws Throwable{
      //try to get a list of users, if I succeed , hide the passwords
      try{
        List<User> user_list = userRepo.findAll();

        // For loop for iterating over the List
        for (int i = 0; i < user_list.size(); i++) {

          //hide the password when returning the user list from the server
          user_list.get(i).setPassword("****");
        }
        return user_list;

      //if the user is not found, error message
      }catch(Exception e){
        throw new UserNotFoundException("No users");
      }
    }

    //function updates the user registry
    /*public User updateUser(User user){
        return userRepo.save(user);
    }*/

    //function searches for the user by userName only
    public User findUser(String userName) throws Throwable
    {
      User tempUser;
      //try to find user by user name, if I succeed hide the password
      try{
        tempUser =  userRepo.findUserByUserName(userName).get();
        tempUser.setPassword("****");
        return tempUser;

      }catch(Exception e){
        throw new UserNotFoundException("User not found");
      }
    }

    //find user by his id, if user wasn't found an exception will be thrown
    //function searches user by user name and password
    public User findUserByUserName(String userName, String password) throws Throwable {
      User tempUser;
      try{
        tempUser =  userRepo.findUserByUserName(userName).get();
        //if a user with the user name was found
          if (tempUser.getPassword().equals(password)) {
            tempUser.setPassword("****"); //mask returned password
            return tempUser;
        }
          else
            throw new UserNotFoundException("Wrong password");

      }catch(Exception e){
        throw new UserNotFoundException("User not found");
      }
    }

    //function adds a new user registry to the database
    public User addUser(User user) throws Throwable{
        //init new user
        user.setLoggedIn(false);
        user.setRegisterTime("");
        user.setLastUpdateTime("");
        user.setIpAddress("");

        User tempUser;

        try{
          //check if this user already exists in the database
          //if it does do not add id to the database, because username is a unique key
          tempUser = userRepo.findUserByUserName(user.getUserName()).get();
        }
        catch (Exception e) {
          return userRepo.save(user);
        }

      throw new UserNotFoundException("The user already exists");

    }

    public User update(User user) throws Throwable {
        //if the user name and the password are corresponding, then user's status will be updated to logged in
        User tempUser;

        try{
          //try to find the user
          tempUser = userRepo.findUserByUserName(user.getUserName()).get();

              //set the updated values
              tempUser.setLoggedIn(user.isLoggedIn());;
              if (user.isLoggedIn() == true) //increase the log in counter
                tempUser.setLoginCount(tempUser.getLoginCount() + 1);

              tempUser.setLastUpdateTime(user.getLastUpdateTime());
              tempUser.setRegisterTime(user.getRegisterTime());
              tempUser.setIpAddress(user.getIpAddress());
              return userRepo.save(tempUser);

        }
        catch (Exception e) {
          throw new UserNotFoundException("User not found");
        }
    }

}
