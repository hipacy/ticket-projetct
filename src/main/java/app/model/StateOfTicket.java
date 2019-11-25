package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StateOfTicket")
public class StateOfTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "StateId")
    private Integer stateId;

    @Column(name = "State")
    private String state;


}
