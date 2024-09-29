package nl.hu.dp.ovchip.dao;


import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection conn;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }


    public boolean save(Product product) {
        try {
            String save_query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(save_query);
            pst.setInt(1, product.getProduct_nummer());
            pst.setString(2, product.getNaam());
            pst.setString(3, product.getBeschrijving());
            pst.setDouble(4, product.getPrijs());
            pst.executeUpdate();
            pst.close();

            for(OVChipkaart ovChipkaart: product.getOvChipkaarten()){
                String save_query2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?,?,?,?)";
                PreparedStatement pst2 = conn.prepareStatement(save_query2);
                pst2.setInt(1, ovChipkaart.getKaart_nummer());
                pst2.setInt(2, product.getProduct_nummer());
                pst2.setString(3, "Actief");
                pst2.setDate(4, Date.valueOf(LocalDate.now()));
                pst2.executeUpdate();
                pst2.close();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(Product product) {
        try {
            String update_query = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(update_query);
            pst.setString(1, product.getNaam());
            pst.setString(2, product.getBeschrijving());
            pst.setDouble(3, product.getPrijs());
            pst.setInt(4, product.getProduct_nummer());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(Product product) {
        try {
            String delete_query_chip_product = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
            PreparedStatement pst1 = conn.prepareStatement(delete_query_chip_product);
            pst1.setInt(1, product.getProduct_nummer());
            pst1.executeUpdate();
            pst1.close();

            String delete_query_product = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement pst2 = conn.prepareStatement(delete_query_product);
            pst2.setInt(1, product.getProduct_nummer());
            pst2.executeUpdate();
            pst2.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            List<Product> producten = new ArrayList<>();

            String findAllByOv_query = "SELECT * FROM product " +
                                       "JOIN ov_chipkaart_product ON ov_chipkaart_product.product_nummer = product.product_nummer " +
                                       "JOIN ov_chipkaart ON ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer " +
                                       "WHERE ov_chipkaart.kaart_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(findAllByOv_query);
            pst.setInt(1, ovChipkaart.getKaart_nummer());

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int product_nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");

                Product product = new Product(product_nummer, naam, beschrijving, prijs);
                producten.add(product);
            }
            rs.close();
            pst.close();
            return producten;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> findAll() {
        try {
            List<Product> producten = new ArrayList<>();

            String findAll_query = "SELECT * FROM product";
            PreparedStatement pst = conn.prepareStatement(findAll_query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int product_nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");

                Product product = new Product(product_nummer, naam, beschrijving, prijs);
                producten.add(product);
            }
            rs.close();
            pst.close();
            return producten;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
