package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.*;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private SessionFactory sessionFactory;

    public ReizigerDAOHibernate(SessionFactory sf) throws SQLException {
        this.sessionFactory = sf;
    }

    public boolean save(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.update(reiziger);
            tx.commit();
            return true;
        } catch (Exception e){
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.delete(reiziger);
            tx.commit();
            return true;
        } catch (Exception e){
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Reiziger findById(int reiziger_id) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            Reiziger reiziger = session.get(Reiziger.class, reiziger_id);
            tx.commit();
            return reiziger;
        } catch (Exception e){
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }

    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<Reiziger> reizigers;
        try{
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Reiziger> cr = cb.createQuery(Reiziger.class);
            Root<Reiziger> root = cr.from(Reiziger.class);
            cr.select(root).where(cb.equal(root.get("geboortedatum"), date));
            Query<Reiziger> query = session.createQuery(cr);
            reizigers = query.list();
            tx.commit();
            return reizigers;
        } catch (HibernateException e){
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Reiziger> reizigers;

        try{
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Reiziger> cr = cb.createQuery(Reiziger.class);
            Root<Reiziger> root = cr.from(Reiziger.class);
            cr.select(root);
            Query<Reiziger> query = session.createQuery(cr);

            reizigers = query.list();
            tx.commit();
            return reizigers;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
