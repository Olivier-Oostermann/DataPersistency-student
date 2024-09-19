package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.ReizigerDAO;
import nl.hu.dp.ovchip.dao.ReizigerDAOHibernate;
import nl.hu.dp.ovchip.dao.ReizigerDAOPsql;
import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Connection;
import java.sql.DriverManager;
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
        testReizigerDAO(rdao);
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
        String geboorteDatum = "1981-03-14";
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

//         Delete een reiziger van de database
        System.out.println("De volgende reiziger wordt verwijderd: " + reiziger.getNaam());
        rdao.delete(reiziger);

    }
}
