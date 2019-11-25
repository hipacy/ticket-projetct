package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Prio")
public class Prio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PrioId")
    private int prioId;

    @Column(name = "Prio")
    private String prio;


}
