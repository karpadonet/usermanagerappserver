package sashaspivak.usermanagerapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

//this class represents a user
@Entity
@Table (name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//generates the id value when a new record is added
    @Column(nullable = false, updatable = false)
    private long id;
    private String userName;
    private String password;
    private String registerTime;
    private String lastUpdateTime;
    private String ipAddress;
    private boolean loggedIn;

    public User(){}

    public User(String userName, String password, boolean loggedIn, long id, String registerTime, String lastUpdateTime,
                String ipAddress) {
        this.userName = userName;
        this.password = password;
        this.id = id;
        this.loggedIn = false;
        this.registerTime = registerTime;
        this.lastUpdateTime = lastUpdateTime;
        this.ipAddress = ipAddress;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                ", registerTime='" + registerTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

    //compare an object passed to the program with an object from my database
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }
}
