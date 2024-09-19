package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO{
    private SessionFactory sessionFactory;

    public AdresDAOHibernate(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(adres);
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
    public boolean update(Adres adres) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try{
            session.update(adres);
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
    public boolean delete(Adres adres) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.delete(adres);
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
    public Adres findByReiziger(Reiziger r) throws SQLException {
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Adres> adressen;

        try{
            Query<Adres> query = session.createQuery("FROM Adres", Adres.class);
            adressen = query.list();
            tx.commit();
            return adressen;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
