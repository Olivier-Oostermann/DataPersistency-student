package nl.hu.dp.ovchip.domein;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity(name = "Adres")
@Table(name = "Adres")
public class Adres {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "huisnummer")
    private String huisnummer;

    @Column(name = "straat")
    private String straat;

    @Column(name = "woonplaats")
    private String woonplaats;

    @OneToOne(fetch = FetchType.LAZY)
    private Reiziger reiziger;

    public Adres(){

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }
    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String toString(){
        return "Adresgegevens: " + postcode + " " + woonplaats + " " + straat + " " + huisnummer;
    }
}
