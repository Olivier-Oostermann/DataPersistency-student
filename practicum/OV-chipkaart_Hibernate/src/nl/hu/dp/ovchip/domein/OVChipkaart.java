package nl.hu.dp.ovchip.domein;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "OVChipkaart")
@Table(name = "OVChipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer")
    private int kaart_numer;
    @Column(name = "geldig_tot")
    private Date geldig_tot;
    @Column(name = "klasse")
    private int klasse;
    @Column(name = "saldo")
    private int saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    public OVChipkaart(){

    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public int getKaart_nummer() {
        return kaart_numer;
    }

    public void setKaart_numer(int kaart_numer) {
        this.kaart_numer = kaart_numer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "OVChipkaart: " +
                "kaart_numer = " + kaart_numer +
                ", geldig_tot = " + geldig_tot +
                ", klasse = " + klasse +
                ", saldo = " + saldo;
    }
}