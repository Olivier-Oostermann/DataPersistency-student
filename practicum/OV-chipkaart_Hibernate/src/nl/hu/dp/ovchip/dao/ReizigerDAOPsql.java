package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Reiziger reiziger) throws SQLException {
        try {
            String save_query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(save_query);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update (Reiziger reiziger) throws SQLException {
        try {
            String update_query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ? WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(update_query);
            pst.setString(1, reiziger.getVoorletters());
            pst.setString(2, reiziger.getTussenvoegsel());
            pst.setString(3, reiziger.getAchternaam());
            pst.setInt(4, reiziger.getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete (Reiziger reiziger) throws SQLException {
        try {
            String delete_query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(delete_query);
            pst.setInt(1, reiziger.getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Reiziger findById(int reiziger_id) throws SQLException {
        try {
            String findAllById_query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(findAllById_query);
            pst.setInt(1, reiziger_id);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                return reiziger;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    public List<Reiziger> findByGbdatum(Date datum) throws SQLException {
        try {
            List<Reiziger> reizigers = new ArrayList<>();

            String findAllByGeboortedatum_query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement pst = conn.prepareStatement(findAllByGeboortedatum_query);
            pst.setDate(1, datum);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                reizigers.add(reiziger);
            }
            return reizigers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Reiziger> findAll() throws SQLException {
        try {
            List<Reiziger> reizigers = new ArrayList<>();

            String findAll_query = "SELECT * FROM reiziger";
            PreparedStatement pst = conn.prepareStatement(findAll_query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(
                        rs.getInt("reiziger_id"),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum")
                );
                reizigers.add(reiziger);
            }
            return reizigers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
