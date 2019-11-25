package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TicketId")
    private Integer ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PrioId")
    private Prio prioId;

    @OneToMany
    @BatchSize(size = 10)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
            name = "Ticket_Item",
            joinColumns = @JoinColumn(name = "TicketId"),
            inverseJoinColumns = @JoinColumn(name = "ItemId"))
    private List<Item> items;

    @OneToMany
    @BatchSize(size = 10)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
            name = "Ticket_Team",
            joinColumns = @JoinColumn(name = "TicketId"),
            inverseJoinColumns = @JoinColumn(name = "TeamId"))
    private List<Team> assignedTeams;

    @Column(name = "Category")
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssignedTechnicianId")
    private Users assignedTechnicianId;

    @Column(name = "Title")
    @NotEmpty(message = "*Podaj Tytuł!")
    private String title;

    @Column(name = "TicketBody")
    @NotEmpty(message = "*Wpisz treść ticketu!!")
    private String ticketBody;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Stateid")
    private StateOfTicket stateId;

    @Column(name = "ApprovalNeeded")
    private Boolean approvalNeeded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RequestorId")
    private Users requestorId;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "lastModifiedDate")
    private LocalDateTime lastModifiedDate;


}
