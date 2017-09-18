package com.ex.FantasySoccerLeague.tables;

import javax.persistence.*;

@Entity
@Table(name="TEAM")
public class Team {

    @Override
    public String toString() {
        return "Team{" +
                "Id=" + team_Id +
                ", Name='" + team_Name + '\'' +
                ", Owner=" + user +
                ", Points=" + Points +
                ", Money=" + money +
                ", League=" + league +
                '}';
    }

    private Integer team_Id;
    private String team_Name;
    private Fantasy_User user;
    private Integer Points;
    private League league;
    private Integer money;

    public Team(){}


    @Id
    @GeneratedValue
    @Column(name="TEAM_ID", unique = true, nullable = false)
    public Integer getId() {
        return team_Id;
    }

    public void setId(Integer id) {
        team_Id = id;
    }

    @Column(name="team_Name", nullable = false, length = 20)
    public String getName() {
        return team_Name;
    }

    public void setName(String name) {
        team_Name = name;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    public Fantasy_User getUser(){return this.user;}

    public void setUser(Fantasy_User user) {
        this.user = user;
    }

    @Column(name="POINTS", length = 10)
    public Integer getPoints() {
        return Points;
    }

    public void setPoints(Integer points) {
        Points = points;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="LEAGUE_ID")
    public League getLeague() {
        return this.league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Column(name="MONEY", length = 10)
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}