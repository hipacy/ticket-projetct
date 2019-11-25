package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "Password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "FirstName")
    @NotEmpty(message = "*Please provide your first name")
    private String firstName;

    @Column(name = "LastName")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @BatchSize(size = 10)
    @ManyToMany
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "Users_Team",
            joinColumns = @JoinColumn(name = "Users_TeamId", referencedColumnName = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "Team_TeamId", referencedColumnName = "TeamId"))
    private List<Team> teams;

    @Column(name = "Email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "JobTitle")
    private String jobTitle;

    @Column(name = "IsActive")
    private Integer isActive;

    @Column(name = "Image")
    private String imageLink;

    @BatchSize(size = 5)
    @ManyToMany
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "Users_UserType",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "UserTypeId"))
    @NotEmpty(message = "*Please choose at least one role!")
    private List<UserType> roles;


}
