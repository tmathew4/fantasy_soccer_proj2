package com.ex.FantasySoccerLeague.tables;


import javax.persistence.*;

@Entity
@Table(name="LEAGUE")
public class League {

    private Integer league_Id;
    private String league_Name;

    public League() {}

    @Id
    @GeneratedValue
    @Column(name="LEAGUE_ID", unique=true, nullable = false)
    public Integer getId() {
        return league_Id;
    }

    public void setId(Integer id) {
        league_Id = id;
    }

    @Column(name="League_Name", nullable = false)
    public String getName() {
        return league_Name;
    }

    public void setName(String name) {
        league_Name = name;
    }

    @Override
    public String toString() {
        return "League{" +
                "Id=" + league_Id +
                ", Name='" + league_Name + '\'' +
                '}';
    }



}