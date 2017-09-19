package com.ex.FantasySoccerLeague.tables;


import javax.persistence.*;

@Entity
@Table(name="Fantasy_user")
public class Fantasy_User {

    private String Email;
    private String Password;
    private String FName;
    private String LName;
    private Integer user_id;
    private Team team;

    public Fantasy_User(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER_ID",unique = true, nullable = false)
    public Integer getId() {
        return user_id;
    }

    public void setId(Integer id) {
        this.user_id = id;
    }

    @Column(name="EMAIL", unique = true)
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Column(name="User_pASSWORD", nullable = false)
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Column(name="F_NAME", nullable = false)
    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    @Column(name="L_NAME", nullable = false)
    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                '}';
    }

}