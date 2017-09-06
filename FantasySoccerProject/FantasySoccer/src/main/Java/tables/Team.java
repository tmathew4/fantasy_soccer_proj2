package tables;

        import javax.persistence.*;

@Entity
@Table(name="TEAM", uniqueConstraints = { @UniqueConstraint(columnNames="NAME")})
public class Team {



    @Override
    public String toString() {
        return "Team{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Owner=" + Owner +
                ", points=" + points +
                ", League=" + league +
                '}';
    }

    private Integer Id;
    private String Name;
    private Owner Owner;
    private Integer points;
    private League league;


    public Team(){}


    @Id
    @GeneratedValue
    @Column(name="TEAMID", unique = true, nullable = false)
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Column(name="Name", nullable = false, length = 20)
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="OWNERID", referencedColumnName = "OWNERID")
    public Owner getOwner(){return this.Owner;}

    public void setOwner(Owner owner) {
        Owner = owner;
    }

    @Column(name="POINTS", length = 10)
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="LEAGUEID", referencedColumnName = "LEAGUEID")
    public League getLeague() {
        return this.league;
    }

    public void setLeague(League league) {
        this.league = league;
    }


}