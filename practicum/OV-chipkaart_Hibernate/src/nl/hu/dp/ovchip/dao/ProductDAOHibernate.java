package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private SessionFactory sessionFactory;

    public ProductDAOHibernate(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public boolean save(Product product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        return false;
    }

    @Override
    public boolean update(Product product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        return false;
    }

    @Override
    public boolean delete(Product product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        return false;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        return List.of();
    }

    @Override
    public List<Product> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Product> producten;

        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cr = cb.createQuery(Product.class);
            Root<Product> root = cr.from(Product.class);
            cr.select(root);
            Query<Product> query = session.createQuery(cr);

            producten = query.list();
            tx.commit();
            return producten;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
