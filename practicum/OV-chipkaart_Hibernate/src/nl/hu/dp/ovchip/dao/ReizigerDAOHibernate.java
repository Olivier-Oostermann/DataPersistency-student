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
//    private Transaction tx;

    public ReizigerDAOHibernate(SessionFactory sf) throws SQLException {
        this.sessionFactory = sf;
    }

    public boolean save(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            session.save(reiziger);
            tx.commit();
            session.flush();
        } catch (HibernateException e) {
            tx.rollback();
            return false;
        } finally {
            session.close();
            return true;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try{
            String hql = "UPDATE Reiziger SET voorletters = :voorletters, tussenvoegsel = :tussenvoegsel, achternaam = :achternaam, geboortedatum = :geboortedatum WHERE id = :reiziger_id";
            Query query = session.createQuery(hql);
            query.setParameter("voorletters", reiziger.getVoorletters());
            query.setParameter("tussenvoegsel", reiziger.getTussenvoegsel());
            query.setParameter("achternaam", reiziger.getAchternaam());
            query.setParameter("geboortedatum", reiziger.getGeboortedatum());
            query.setParameter("reiziger_id", reiziger.getId());
            query.executeUpdate();
            tx.commit();
//            session.flush();
        } catch (HibernateException e){
            throw e;
        } finally {
            session.close();
            return true;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try{
            String hql = "DELETE FROM Reiziger WHERE id = :reiziger_id";
            Query query = session.createQuery(hql);
            query.setParameter("reiziger_id", reiziger.getId());
            query.executeUpdate();
            tx.commit();
//            session.flush();
        } catch (HibernateException e){
            throw e;
        } finally {
            session.close();
            return true;
        }
    }

    @Override
    public Reiziger findById(int reiziger_id) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Reiziger reiziger = null;

        try{
            String hql = "FROM Reiziger WHERE id = :reiziger_id";
            Query<Reiziger> query = session.createQuery(hql, Reiziger.class);
            query.setParameter("reiziger_id", reiziger_id);
            reiziger = query.getSingleResult();
            tx.commit();
//            session.flush();
        } catch (HibernateException e){
            throw e;
        } finally {
            session.close();
        }
        return reiziger;

    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<Reiziger> reizigers;
        try{
            String hql = "FROM Reiziger WHERE geboortedatum = :date";
            Query<Reiziger> query = session.createQuery(hql, Reiziger.class);
            query.setParameter("date", date);
            reizigers = query.list();
            tx.commit();
//            session.flush();
        } catch (HibernateException e){
            throw e;
        } finally {
            session.close();
        }
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<Reiziger> reizigers;
        try{
            String hql = "FROM Reiziger";
            Query<Reiziger> query = session.createQuery(hql, Reiziger.class);
            reizigers = query.list();
            tx.commit();
//            session.flush();
        } catch (HibernateException e) {
//            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return reizigers;
    }

}
