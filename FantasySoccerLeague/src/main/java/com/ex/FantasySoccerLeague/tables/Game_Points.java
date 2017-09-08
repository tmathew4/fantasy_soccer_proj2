package com.ex.FantasySoccerLeague.tables;

import javax.persistence.*;

@Entity
@Table(name="GAME_POINTS")
public class Game_Points {
    public Game_Points(){}

    private Integer gamePoints_Id;
    private Player player;
    private Game game;
    private Integer Assists;
    private Integer SOG;
    private Integer Goals;
    private Integer Own_Goals;
    private Integer Red_Cards;
    private Integer Yellow_Cards;

    @Id
    @GeneratedValue
    @Column(name="GAME_POINTSID", nullable = false)
    public Integer getId() {
        return gamePoints_Id;
    }

    public void setId(Integer id) {
        gamePoints_Id = id;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PLAYER_ID")
    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer (Player playerId) {
        this.player = player;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="GAME_ID")
    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Column(name="Assists")
    public Integer getAssists() {
        return Assists;
    }


    public void setAssists(Integer assists) {
        Assists = assists;
    }

    @Column(name="SOG")
    public Integer getSOG() {
        return SOG;
    }

    public void setSOG(Integer SOG) {
        this.SOG = SOG;
    }

    @Column(name="GOALS")
    public Integer getGoals() {
        return Goals;
    }

    public void setGoals(Integer goals) {
        Goals = goals;
    }

    @Column(name="OWN_GOALS")
    public Integer getOwn_Goals() {
        return Own_Goals;
    }

    public void setOwn_Goals(Integer own_Goals) {
        Own_Goals = own_Goals;
    }

    @Column(name="RED_CARDS")
    public Integer getRed_Cards() {
        return Red_Cards;
    }

    public void setRed_Cards(Integer red_Cards) {
        Red_Cards = red_Cards;
    }

    @Column(name="YELLOW_CARDS")
    public Integer getYellow_Cards() {
        return Yellow_Cards;
    }

    public void setYellow_Cards(Integer yellow_Cards) {
        Yellow_Cards = yellow_Cards;
    }

}
