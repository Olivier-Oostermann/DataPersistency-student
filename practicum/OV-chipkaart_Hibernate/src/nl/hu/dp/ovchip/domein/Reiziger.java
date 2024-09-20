package nl.hu.dp.ovchip.domein;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    @Id
    @Column(name = "reiziger_id")
    private int id;

    @Column(name = "voorletters")
    private String voorletters;

    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "geboortedatum")
    private Date geboortedatum;

    @OneToOne(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Adres adres;

    @OneToMany(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OVChipkaart> ovChipkaarten;

    public Reiziger() {}

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }
    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }
    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getNaam(){
        return voorletters + " " + tussenvoegsel + " " + achternaam;
    }

    public String toString(){
        return "Uw volledige naam is " + voorletters + " " + tussenvoegsel + " " + achternaam + "\nU bent geboren op " + geboortedatum + "\n";
    }
}

