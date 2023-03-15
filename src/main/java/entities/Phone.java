package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "person_id")
    @ManyToOne
    private Person person;

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }
}
