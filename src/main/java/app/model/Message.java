package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MessageId")
    private int messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TicketId")
    private Ticket ticketId;

    @Column(name = "DateTimeCreated")
    private LocalDateTime dateTimeCreated;

    @Column(name = "Body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Creator")
    private Users creator;

    @Column(name = "IsTechnical")
    private Boolean isTechnical;

    @Column(name = "IsActive")
    private Boolean isActive;
}
