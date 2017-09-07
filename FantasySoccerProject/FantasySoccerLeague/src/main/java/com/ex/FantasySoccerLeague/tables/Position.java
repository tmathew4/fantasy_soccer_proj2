package com.ex.FantasySoccerLeague.tables;
        import javax.persistence.Entity;
        import javax.persistence.Id;
        import javax.persistence.Table;
        import javax.persistence.Column;


//Make it a enum??
@Entity
@Table(name="player_POSITION")
public class Position {

    private Integer position_Id;
    private String position_Name;

    public Position() {}

    @Id
    @Column(name="POSITION_ID")
    public Integer getId() {
        return position_Id;
    }

    public void setId(Integer position_id) {
        position_Id = position_id;
    }

    @Column(name="POSITION_NAME")
    public String getName() {
        return position_Name;
    }

    public void setName(String name) {
        position_Name = name;
    }

    @Override
    public String toString() {
        return "Position{" +
                "Id=" + position_Id +
                ", Name='" + position_Name + '\'' +
                '}';
    }

}