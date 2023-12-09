package com.example.myapplication;

/**
 * this is a helper class that creates the users with their informations
 */

public class userInfo {

    private String userName;
    private String passWord;

    private String firstname;
    private String lastname;
    private String phoneNumber;

    private String email;
    private String team;

    /**
     *
     * @param userName
     * @param passWord
     * @param Firstname
     * @param Lastname
     * @param Email
     * @param phoneNumber
     *
     * create user
     */
    public userInfo(String userName, String passWord, String Firstname, String Lastname, String Email, String phoneNumber, String team) {
        this.userName = userName;
        this.passWord = passWord;
        this .firstname = Firstname;
        this.lastname = Lastname;
        this.email =Email;
        this.phoneNumber = phoneNumber;


    }

    public userInfo(String userName, String passWord, String Email, String phoneNumber, String team) {
        this.userName = userName;
        this.passWord = passWord;
        this.email =Email;
        this.phoneNumber = phoneNumber;


    }

    /**
     * get username
     * @return
     */
    public String getteams(){
        return team ;

    }
    /**
     * get username
     * @return
     */
    public String getUsername(){
       return userName ;

    }

    /**
     * get password
     * @return
     */
    public String getPassword(){
        return passWord ;

    }

    /**
     * get firstname
     * @return
     */
    public String getFirstName(){
        return firstname ;

    }

    /**
     * get last name
     * @return
     */
    public String getLastname(){
        return lastname ;

    }

    /**
     * get phone number
     * @return
     */
    public String getphoneNumber(){
        return phoneNumber ;

    }

    /**
     * get eamil
     * @return
     */
    public String getEmail(){
        return email ;

    }

    /**
     * set the user name
     * @param oldUser
     */
    public void SetUserName(String oldUser){
      userName = oldUser;
     }

    /**
     * set password
     * @param oldUser
     */
    public void SetPassword(String oldUser){
        passWord = oldUser;
    }

    /**
     * SET EMAIL
     * @param oldUser
     */
    public void Setemail(String oldUser){
        email = oldUser;
    }

    /**
     * SET PHOHNE
     * @param oldUser
     */
    public void SetPhone(String oldUser){
        phoneNumber = oldUser;
    }

    /**
     * set the tame
     * @param olduser
     */
    public void setteams(String olduser) {team = olduser ;}

}
