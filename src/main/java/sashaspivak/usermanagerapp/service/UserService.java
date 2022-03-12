package sashaspivak.usermanagerapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sashaspivak.usermanagerapp.exception.UserNotFoundException;
import sashaspivak.usermanagerapp.model.User;
import sashaspivak.usermanagerapp.model.repo.UserRepo;

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
    public List<User> findAllUsers(){
      List<User> user_list = userRepo.findAll();

      // For loop for iterating over the List
      for (int i = 0; i < user_list.size(); i++) {

        // Print all elements of List
        user_list.get(i).setPassword("****");
      }

      return user_list;
    }
    public User updateUser(User user){
        return userRepo.save(user);
    }

    //find user only by name
    public User findUser(String userName) throws Throwable
    {
      User tempUser;
      tempUser =  userRepo.findUserByUserName(userName).get();

        if (tempUser!=null) {
          tempUser.setPassword("****");
          return tempUser;
        }
        else
          throw new UserNotFoundException("User not found");
    }

    //find user by his id, if user wasn't found an exception will be thrown
    public User findUserByUserName(String userName, String password) throws Throwable {
      User tempUser;

      tempUser =  userRepo.findUserByUserName(userName).get();

      //if a user with the user name was found
      if (tempUser!=null)
      {
        //compare passwords
        if (tempUser.getPassword().equals(password)) {
          tempUser.setPassword("****"); //mask returned password
          return tempUser;
        }
        else
          throw new UserNotFoundException("Wrong password");
      }
      else
      {
        throw new UserNotFoundException("User not found");
      }
    }

    public User addUser(User user) {
        //init new user
        user.setLoggedIn(false);
        user.setRegisterTime("");
        user.setLastUpdateTime("");
        user.setIpAddress("");

        Optional<User> tempUser;

        //check if this user already exists in the database
        //if it does do not add id to the database, because username is a unique key
        tempUser = userRepo.findUserByUserName(user.getUserName());

        if (tempUser != null){
            //save user to db
            return userRepo.save(user);
        }

        return null;

    }

    public User update(User user){
        //if the user name and the paswword are corresponding, then user's status will be updated to logged in
        User tempUser;
        tempUser = userRepo.findUserByUserName(user.getUserName()).get();

        if (tempUser != null){
            //checking users password
            if(user.getUserName().equals(tempUser.getUserName()))
            {
                //set the updated values
                tempUser.setLoggedIn(user.isLoggedIn());;
                if (user.isLoggedIn() == true) //increase the log in counter
                  tempUser.setLoginCount(tempUser.getLoginCount() + 1);
                tempUser.setLastUpdateTime(user.getLastUpdateTime());
                tempUser.setRegisterTime(user.getRegisterTime());
                tempUser.setIpAddress(user.getIpAddress());
                return userRepo.save(tempUser);
            }

        }
        return null;
    }

}
