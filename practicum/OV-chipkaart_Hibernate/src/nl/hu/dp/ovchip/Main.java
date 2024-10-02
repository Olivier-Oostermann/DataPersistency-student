package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
//    private static Session getSession() throws HibernateException {
//        return factory.openSession();
//    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        ReizigerDAO rdao = new ReizigerDAOHibernate(factory);
        AdresDAO adao = new AdresDAOHibernate(factory);
        OVChipkaartDAO odao = new OVChipkaartDAOHibernate(factory);
        ProductDAO pdao = new ProductDAOHibernate(factory);

        testReizigerDAO(rdao);
        testAdresDAO(adao, rdao);
        testOvChipkaartDAO(odao, rdao);
        testProductDAO(pdao, odao, rdao);
//        testFetchAll();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
//    private static void testFetchAll() {
//        Session session = getSession();
//        try {
//            Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                Query query = session.createQuery("from " + entityType.getName());
//
//                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//                System.out.println();
//            }
//        } finally {
//            session.close();
//        }
//    }
    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(6, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Haal reizigers op met de geboortedatum
        String geboorteDatum = "2002-12-03";
        List<Reiziger> reizigerByDate = rdao.findByGbdatum(java.sql.Date.valueOf(geboorteDatum));
        System.out.println("Aantal reizigers met geboortedatum " + geboorteDatum + ": " + reizigerByDate.size() + "\n");

        // Haal een reiziger op met het id
        int reiziger_id = 6;
        Reiziger reiziger = rdao.findById(reiziger_id);
        System.out.println("Reiziger met id " + reiziger_id + ": " + reiziger.toString() + "\n");

        // Update een reiziger aan en persisteer deze in de database
        reiziger.setVoorletters("O");
        reiziger.setTussenvoegsel("van der");
        reiziger.setAchternaam("Ende");
        rdao.update(reiziger);
        System.out.println("De reiziger is geupdate. de reiziger zijn/haar volledige naam is nu: " + reiziger.getNaam() + "\n");

        // Delete een reiziger van de database
        System.out.println("De volgende reiziger wordt verwijderd: " + reiziger.getNaam());
        rdao.delete(reiziger);

    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende Adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        String gbDatumAdres = "2003-12-17";
        Reiziger olivier = new Reiziger(99, "O", "", "Oostermann", java.sql.Date.valueOf(gbDatumAdres));
        rdao.save(olivier);

        Adres adres = new Adres();
        adres.setId(7);
        adres.setPostcode("2408RV");
        adres.setHuisnummer("21");
        adres.setStraat("Schouw");
        adres.setWoonplaats("Alphen aan den Rijn");
        adres.setReiziger(olivier);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(adres);
        List<Adres> alleAdressen = adao.findAll();
        System.out.println(alleAdressen.size() + " adressen\n");

        // Update een adres en persisteer deze in de database
        adres.setStraat("De Morgen");
        adao.update(adres);
        System.out.println("Het adres is geupdate. De straatnaam is nu: " + adres.getStraat() + "\n");

        // vind het adres van een reiziger
        Adres gevondenAdres = adao.findByReiziger(olivier);
        System.out.println("Het adres van een specifieke reiziger: " + gevondenAdres + "\n");

        // Delete een reiziger van de database
        System.out.println("Het volgende adres wordt verwijderd: " + adres);
        adao.delete(adres);
        rdao.delete(olivier);
    }

    private static void testOvChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ovChipkaartDAO -------------");

        // Haal alle ovchipkaarten op uit de database
        List<OVChipkaart> ovChipkaarten = odao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende ovchipkaarten:");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }
        System.out.println();


        // Maak een nieuw ovChipkaart aan en persisteer deze in de database
        String gbDatumT = "2003-10-09";
        Reiziger teun = new Reiziger(66, "T", "van der", "Broek", java.sql.Date.valueOf(gbDatumT));
        rdao.save(teun);

        String datum_geldig_tot = "2003-10-09";
        OVChipkaart ovChipkaart = new OVChipkaart();
        ovChipkaart.setKaart_nummer(203932);
        ovChipkaart.setGeldig_tot(java.sql.Date.valueOf(datum_geldig_tot));
        ovChipkaart.setKlasse(1);
        ovChipkaart.setSaldo(500);
        ovChipkaart.setReiziger(teun);
        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " ovChipkaarten, na ovChipkaartDAO.save() ");
        odao.save(ovChipkaart);
        List<OVChipkaart> alleOvChipkaarten = odao.findAll();
        System.out.println(alleOvChipkaarten.size() + " ovChipkaarten\n");

        // Update een ovChipkaart en persisteer deze in de database
        ovChipkaart.setSaldo(600);
        odao.update(ovChipkaart);
        System.out.println("De ovChipkaart is geupdate. Het saldo is nu: " + ovChipkaart.getSaldo() + "\n");

        // vind het ovChipkaarten van een reiziger
        List<OVChipkaart> gevondenOvChipkaarten;
        gevondenOvChipkaarten = odao.findByReiziger(teun);
        System.out.println("De ovChipkaart van een specifieke reiziger: " + gevondenOvChipkaarten + "\n");

        // Delete een ovChipkaarten en reiziger van de database
        System.out.println("De volgende ovChipkaart wordt verwijderd: " + ovChipkaart);
        odao.delete(ovChipkaart);
        rdao.delete(teun);
    }

    private static void testProductDAO(ProductDAO pdao, OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        String gbDatumT = "2003-10-09";
        Reiziger productReiziger = new Reiziger(21, "O", "van der", "Broek", java.sql.Date.valueOf(gbDatumT));
        rdao.save(productReiziger);

        String datum_geldig_tot = "2020-10-09";
        OVChipkaart ovChipkaart = new OVChipkaart();
        ovChipkaart.setKaart_nummer(23919);
        ovChipkaart.setGeldig_tot(java.sql.Date.valueOf(datum_geldig_tot));
        ovChipkaart.setKlasse(1);
        ovChipkaart.setSaldo(500);
        ovChipkaart.setReiziger(productReiziger);
        odao.save(ovChipkaart);

        System.out.println("\n---------- Test productDAO -------------");
        // Haal alle producten op uit de database
        List<Product> producten = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende ovchipkaarten:");
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println();

        // Maak een nieuw product aan en persisteer deze in de database
        Product product = new Product(7, "Test productnaam", "Dit is een beschrijving van de test", 22.30);
        product.addOvChipkaarten(ovChipkaart);
        System.out.println("Het volgende product wordt toegevoegd: " + product + "\n");
        pdao.save(product);

        // Update een product van de database
        System.out.println("Het volgende product wordt geupdate: " + product);
        product.setNaam("Studenten Ov");
        pdao.update(product);
        System.out.println("Het product is nu: " + product + "\n");

        // Vind alle producten van een ov chipkaart
        System.out.println("De ov chipkaart heeft deze producten: ");
        List<Product> productenFromChipkaart = pdao.findByOVChipkaart(ovChipkaart);
        for(Product p : productenFromChipkaart){
            System.out.println(p);
        }
        System.out.println();

        // Delete een product van de database
        System.out.println("Het volgende product wordt verwijderd: " + product);

        pdao.delete(product);
        odao.delete(ovChipkaart);
        rdao.delete(productReiziger);


    }

}
