package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ItemId")
    private Integer itemId;

    @Column(name = "Type")
    private String type;

    @Column(name = "Name")
    private String name;

    @Column(name = "BuyDate")
    private LocalDate buyDate;

    @Column(name = "VarrantyDate")
    private LocalDate varrantyDate;

    @Column(name = "IsActive")
    private Boolean isActive;

    @Column(name = "Image")
    private String imageLink;


}
