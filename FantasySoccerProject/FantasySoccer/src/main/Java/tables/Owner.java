package tables;


import javax.persistence.*;

@Table(name="OWNER", uniqueConstraints =
        {@UniqueConstraint(columnNames = "EMAIL"),
                @UniqueConstraint(columnNames = "PASSWORD"),
                @UniqueConstraint(columnNames = "FNAME"),
                @UniqueConstraint(columnNames = "LNAME")})
public class Owner {

    private String Email;
    private String Password;
    private String FName;
    private String LName;
    private Integer id;
    private Team team;

    public Owner(){}

    @Id
    @GeneratedValue
    @Column(name="OWNERID", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="EMAIL", unique = true)
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Column(name="PASSWORD", nullable = false)
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Column(name="FNAME", nullable = false)
    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    @Column(name="LNAME", nullable = false)
    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Team getTeam(){return this.team;}

    public void setTeam(Team team){
        this.team = team;
    }


    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                '}';
    }

}