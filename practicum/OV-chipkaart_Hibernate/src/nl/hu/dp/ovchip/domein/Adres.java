package nl.hu.dp.ovchip.domein;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity(name = "Adres")
@Table(name = "Adres")
public class Adres {
    @Id
    @GeneratedValue
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
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

    public String toString(){
        return null;
    }
}
