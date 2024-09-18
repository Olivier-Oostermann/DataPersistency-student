package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Adres adres) throws SQLException {
        try {
            String save_query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(save_query);
            pst.setInt(1, adres.getId());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6, adres.getReiziger().getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(Adres adres) throws SQLException {
        try {
            String update_query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? WHERE adres_id = ?";
            PreparedStatement pst = conn.prepareStatement(update_query);
            pst.setString(1, adres.getPostcode());
            pst.setString(2, adres.getHuisnummer());
            pst.setString(3, adres.getStraat());
            pst.setString(4, adres.getWoonplaats());
            pst.setInt(5, adres.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(Adres adres) throws SQLException {
        try {
            String delete_query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement pst = conn.prepareStatement(delete_query);
            pst.setInt(1, adres.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public Adres findByReiziger(Reiziger r) throws SQLException {
        try {
            String findAllById_query = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(findAllById_query);
            pst.setInt(1, r.getId());

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");

                Adres adres = new Adres();
                adres.setId(id);
                adres.setPostcode(postcode);
                adres.setHuisnummer(huisnummer);
                adres.setStraat(straat);
                adres.setWoonplaats(woonplaats);
                pst.close();
                return adres;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<Adres> findAll() throws SQLException {
        try {
            List<Adres> adressen = new ArrayList<>();

            String findAll_query = "SELECT * FROM adres";
            PreparedStatement pst = conn.prepareStatement(findAll_query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");

                Adres adres = new Adres();
                adres.setId(id);
                adres.setPostcode(postcode);
                adres.setHuisnummer(huisnummer);
                adres.setStraat(straat);
                adres.setWoonplaats(woonplaats);

                adressen.add(adres);
            }
            return adressen;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


}
