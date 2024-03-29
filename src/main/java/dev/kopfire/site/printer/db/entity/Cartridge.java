package dev.kopfire.site.printer.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cartridges")
public class Cartridge {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne()
    @JoinColumn(name = "type_cartridge", referencedColumnName = "id")
    private TypesCartridges type_cartridge;

    @Column
    private String text_qr;

    @ManyToOne()
    @JoinColumn(name = "office", referencedColumnName = "id")
    private Offices office;

    @Column
    private String status;

    @Column
    private String text_status;
}