package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Adres adres) throws SQLException {
        String save_query = "INSERT INTO reiziger (adres_id, postcode, huisnummer, straat, woonplaats) VALUES (?,?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(save_query);
        pst.setInt(1, adres.getId());
        pst.setString(2, adres.getPostcode());
        pst.setString(3, adres.getHuisnummer());
        pst.setString(4, adres.getStraat());
        pst.setString(5, adres.getWoonplaats());
        pst.executeUpdate();
        pst.close();
        return true;
    }

    public boolean update(Adres adres) throws SQLException {
        String update_query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? WHERE adres_id = ?";
        PreparedStatement pst = conn.prepareStatement(update_query);
        pst.setString(1, adres.getPostcode());
        pst.setString(2, adres.getHuisnummer());
        pst.setString(3, adres.getStraat());
        pst.setString(4, adres.getWoonplaats());
        pst.setInt(5, adres.getId());
        pst.executeUpdate();
        pst.close();
        return true;
    }

    public boolean delete(Adres adres) throws SQLException {
        String delete_query = "DELETE FROM adres WHERE adres_id = ?";
        PreparedStatement pst = conn.prepareStatement(delete_query);
        pst.setInt(1, adres.getId());
        pst.executeUpdate();
        pst.close();
        return true;
    }


    public Adres findByReiziger(int reiziger_id) throws SQLException {
        String findAllById_query = "SELECT * FROM adres WHERE reiziger_id = ?";
        PreparedStatement pst = conn.prepareStatement(findAllById_query);
        pst.setInt(1, reiziger_id);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Adres adres = new Adres();
            adres.setPostcode("postcode");
            adres.setHuisnummer("huisnummer");
            adres.setStraat("straat");
            adres.setWoonplaats("woonplaats");
            return adres;
        }
        return null;
    }



}
