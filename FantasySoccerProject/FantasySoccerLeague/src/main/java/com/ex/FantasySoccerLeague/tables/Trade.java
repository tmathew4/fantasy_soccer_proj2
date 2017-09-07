package com.ex.FantasySoccerLeague.tables;

import javax.persistence.*;


@Entity
@Table(name="TRADE")
public class Trade {

    private Integer Id;
    private Player player1Id;
    private Player player2Id;

    public Trade() {}

    @Override
    public String toString() {
        return "Trade{" +
                "Id=" + Id +
                ", Player1Id=" + player1Id +
                ", Player2Id=" + player2Id +
                '}';
    }

    @Id
    @GeneratedValue
    @Column(name="TRADE_ID", nullable = false)
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="PLAYER_ID", insertable = false, updatable = false)
    public Player getPlayer1Id() {
        return this.player1Id;
    }

    public void setPlayer1Id(Player player1Id) {
        this.player1Id = player1Id;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="PLAYER_ID", insertable = false, updatable = false)
    public Player getPlayer2Id() {
        return this.player2Id;
    }
    public void setPlayer2Id(Player player2Id) {
        this.player2Id = player2Id;
    }

}