package bgu.spl.net.api;

public abstract class User {
    private String password;
    private boolean isLoggedIn;
    private String userName;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.isLoggedIn = false;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.password;
    }
    public boolean isLoggedIn(){
        return this.isLoggedIn;
    }
    public void logIn(){
        this.isLoggedIn = true;
    }
    public void logOut(){
        this.isLoggedIn = false;
    }





}
