package tables;


import javax.persistence.*;

@Entity
@Table(name="LEAGUE", uniqueConstraints = {@UniqueConstraint(columnNames = "NAME")})
public class League {

    private Integer Id;
    private String Name;
    private Team team;

    public League() {}

    @Id
    @GeneratedValue
    @Column(name="LEAGUEID", unique=true, nullable = false)
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Column(name="Name", nullable = false)
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "League", cascade = CascadeType.ALL)
    public Team getTeam() {return this.team; }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "League{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                '}';
    }



}