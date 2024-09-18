package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.postgresql.core.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private SessionFactory sessionFactory = null;
    private Transaction tx;

    public ReizigerDAOHibernate(SessionFactory sf) throws SQLException {
        this.sessionFactory = sf;
    }

    public boolean save(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(reiziger);
            tx.commit();
            session.flush();

            return true;
//            String save_query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?,?,?,?,?)";
//            Query query = session.createQuery(save_query);
//            query.executeUpdate();
//
//            PreparedStatement pst = conn.prepareStatement(save_query);
//            pst.setInt(1, reiziger.getId());
//            pst.setString(2, reiziger.getVoorletters());
//            pst.setString(3, reiziger.getTussenvoegsel());
//            pst.setString(4, reiziger.getAchternaam());
//            pst.setDate(5, reiziger.getGeboortedatum());
//            pst.executeUpdate();
//            return true;
        } catch (HibernateException e) {
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        return false;
    }

    @Override
    public Reiziger findById(int reiziger_id) throws SQLException {
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) throws SQLException {
        return List.of();
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Reiziger";
            Query<Reiziger> query = session.createQuery(hql, Reiziger.class);
            return query.list();
        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            sessionFactory.close();
        }
        return List.of();
    }

}
