package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO{
    private SessionFactory sessionFactory = null;

    public AdresDAOHibernate(SessionFactory sf){
        this.sessionFactory = sf;
    }
    @Override
    public boolean save(Adres adres) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.update(adres);
            tx.commit();
        } catch (HibernateException e){
            throw e;
        } finally {
            session.close();
            return true;
        }
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.delete(adres);
            tx.commit();
        } catch (HibernateException e){
            throw e;
        } finally {
            session.close();
            return true;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger r) throws SQLException {
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        return null;
    }
}
