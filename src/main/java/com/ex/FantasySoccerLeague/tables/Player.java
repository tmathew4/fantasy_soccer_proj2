package com.ex.FantasySoccerLeague.tables;

import javax.persistence.*;


@Entity
@Table(name="PLAYER")
public class Player {

    private Integer player_Id;
    private String  player_Name;
    private Integer player_Number;
    private Integer Goals;
    private Integer SOG;
    private Integer Own_Goals;
    private Integer Assists;
    private Integer Red_Card;
    private Integer Yellow_Card;
    private Integer Percentage;
    private Team team;
    private Position position;
    private League league;
    private Integer cost;

    public Integer getPercentage() {
        return Percentage;
    }

    public void setPercentage(Integer percentage) {
        Percentage = percentage;
    }

    public Player(){}

    @Override
    public String toString() {
        return "Player{" +
                "Id=" + player_Id +
                ", Name='" + player_Name + '\'' +
                ", Number=" + player_Number +
                ", Goals=" + Goals +
                ", SOG=" + SOG +
                ", Own_Goals=" + Own_Goals +
                ", Assists=" + Assists +
                ", Red_Card=" + Red_Card +
                ", Yellow_Card=" + Yellow_Card +
                ", Percentage=" + Percentage +
                ", Cost=" + cost +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="PLAYER_ID", nullable = false)
    public Integer getId() {
        return player_Id;
    }

    public void setId(Integer id) {
        player_Id = id;
    }

    @Column(name="player_NAME", nullable = false)
    public String getName() {
        return player_Name;
    }

    public void setName(String name) {
        player_Name = name;
    }

    @Column(name="player_NUMBER")
    public Integer getNumber() {
        return player_Number;
    }

    public void setNumber(Integer number) {
        player_Number = number;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TEAM_ID")
    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Column(name="GOALS")
    public Integer getGoals() {
        return Goals;
    }

    public void setGoals(Integer goals) {
        Goals = goals;
    }

    @Column(name="SOG")
    public Integer getSOG() {
        return SOG;
    }

    public void setSOG(Integer SOG) {
        this.SOG = SOG;
    }

    @Column(name="OWN_GOALS")
    public Integer getOwn_Goals() {
        return Own_Goals;
    }

    public void setOwn_Goals(Integer own_Goals) {
        Own_Goals = own_Goals;
    }

    @Column(name="ASSISTS")
    public Integer getAssists() {
        return Assists;
    }

    public void setAssists(Integer assists) {
        Assists = assists;
    }

    @Column(name="RED_CARD")
    public Integer getRed_Card() {
        return Red_Card;
    }

    public void setRed_Card(Integer red_Card) {
        Red_Card = red_Card;
    }

    @Column(name="YELLOW_CARD")
    public Integer getYellow_Card() {
        return Yellow_Card;
    }

    public void setYellow_Card(Integer yellow_Card) {
        Yellow_Card = yellow_Card;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="POSITION_ID")
    public Position getPosition() {return this.position;}
    public void setPosition(Position position){
        this.position = position;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="LEAGUE_ID")
    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Column(name="COST")
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}

