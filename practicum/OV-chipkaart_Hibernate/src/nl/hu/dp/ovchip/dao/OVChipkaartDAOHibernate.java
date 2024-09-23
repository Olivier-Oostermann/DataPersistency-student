package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private SessionFactory sessionFactory;

    public OVChipkaartDAOHibernate(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(ovChipkaart);
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
    public boolean update(OVChipkaart ovChipkaart) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.update(ovChipkaart);
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
    public boolean delete(OVChipkaart ovChipkaart) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            session.delete(ovChipkaart);
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
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        List<OVChipkaart> ovChipkaarten;
        try{
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OVChipkaart> cr = cb.createQuery(OVChipkaart.class);
            Root<OVChipkaart> root = cr.from(OVChipkaart.class);
            cr.select(root).where(cb.equal(root.get("reiziger"), reiziger));
            Query<OVChipkaart> query = session.createQuery(cr);
            ovChipkaarten = query.list();
            tx.commit();
            return ovChipkaarten;
        } catch (HibernateException e){
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<OVChipkaart> ovChipkaarten;

        try{
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OVChipkaart> cr = cb.createQuery(OVChipkaart.class);
            Root<OVChipkaart> root = cr.from(OVChipkaart.class);
            cr.select(root);
            Query<OVChipkaart> query = session.createQuery(cr);

            ovChipkaarten = query.list();
            tx.commit();
            return ovChipkaarten;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
