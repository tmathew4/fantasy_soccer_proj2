package com.ex.FantasySoccerLeague.tables;

import javax.persistence.*;

@Entity
@Table(name="Player_Stats")
public class Player_Stats {

    private Integer Player_Stats_id;
    private String firstName;
    private String lastName;
    private String headShot;
    private String nationName;
    private String nationFlag;
    private String birthDate;
    private Integer height;
    private Integer weight;
    private Player playerId;

    public Player_Stats(){}

    @Id
    @Column(name="PLAYER_STATS_ID", nullable = false)
    public Integer getPlayer_Stats_id(){return this.Player_Stats_id;}

    public void setPlayer_Stats_id(Integer id){
        this.Player_Stats_id = id;
    }

    @Column(name="FirstName")
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="LastName")
    public String getLastName() {return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name="HeadShot")
    public String getHeadShot() {return headShot;}
    public void setHeadShot(String headShot) {
        this.headShot = headShot;
    }

    @Column(name="NationName")
    public String getNationName() {return nationName;}
    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    @Column(name="NationFlag")
    public String getNationFlag() {return nationFlag;}
    public void setNationFlag(String nationFlag) {
        this.nationFlag = nationFlag;
    }

    @Column(name="Player_BirthDate")
    public String getBirthDate() {return birthDate;}
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name="Player_Height")
    public Integer getHeight() {return height;}
    public void setHeight(Integer height) {
        this.height = height;
    }

    @Column(name="Player_Weight")
    public Integer getWeight() {return weight;}
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Player_id")
    public Player getPlayerId(){return this.playerId;}
    public void setPlayerId(Player player_id){
        this.playerId = player_id;
    }

    @Override
    public String toString() {
        return "Player_Stats{" +
                "Player_Stats_id=" + Player_Stats_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", headShot='" + headShot + '\'' +
                ", nationName='" + nationName + '\'' +
                ", nationFlag='" + nationFlag + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", player_id=" + playerId +
                '}';
    }

}
