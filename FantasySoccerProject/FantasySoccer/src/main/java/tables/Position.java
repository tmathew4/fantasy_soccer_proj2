package tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="POSITION")
public class Position {
    private Integer Id;
    private String Name;

    public Position() {}

    @Id
    @Column(name="POSITIONID")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Column(name="NAME")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Position{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                '}';
    }
}