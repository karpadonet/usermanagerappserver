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
        return userRepo.findAll();
    }
    public User updateUser(User user){
        return userRepo.save(user);
    }

    //find user by his id, if user wasn't found an exception will be thrown
    public User findUserByUserName(User user) throws Throwable {
      Optional<User> ret;
      User tempUser;

      ret = userRepo.findUserByUserName(user.getUserName());
      tempUser = ret.get();

      //check if the user was found
      if (tempUser != null)
      {
        //if teh user was found check the users password
        if (user.getPassword().equals(tempUser.getPassword()))
        {
            return tempUser;
        }
        else {
          throw new UserNotFoundException("Wrong password for user");
        }
      }
      else
      {
        throw new UserNotFoundException("User by user name" + user.getUserName() + "was not found");
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
            if(user.equals(tempUser))
            {
                //set the updated values
                user.setLoggedIn(tempUser.isLoggedIn());;
                user.setLastUpdateTime(tempUser.getLastUpdateTime());
                user.setRegisterTime(tempUser.getRegisterTime());
                user.setIpAddress(tempUser.getIpAddress());
                return userRepo.save(user);
            }

        }
        return null;
    }

}
