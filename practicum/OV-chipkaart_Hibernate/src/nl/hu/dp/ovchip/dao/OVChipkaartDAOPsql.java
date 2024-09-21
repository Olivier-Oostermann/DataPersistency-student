package nl.hu.dp.ovchip.dao;



import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn){
        this.conn = conn;
    }


    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            String save_query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(save_query);
            pst.setInt(1, ovChipkaart.getKaart_nummer());
            pst.setDate(2, ovChipkaart.getGeldig_tot());
            pst.setInt(3, ovChipkaart.getKlasse());
            pst.setInt(4, ovChipkaart.getSaldo());
            pst.setInt(5, ovChipkaart.getReiziger().getId());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            String update_query = "UPDATE ov_Chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(update_query);
            pst.setDate(1, ovChipkaart.getGeldig_tot());
            pst.setInt(2, ovChipkaart.getKlasse());
            pst.setInt(3, ovChipkaart.getSaldo());
            pst.setInt(4, ovChipkaart.getReiziger().getId());
            pst.setInt(5, ovChipkaart.getKaart_nummer());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            String delete_query = "DELETE FROM ov_Chipkaart WHERE kaart_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(delete_query);
            pst.setInt(1, ovChipkaart.getKaart_nummer());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
//        try {
//            List<OVChipkaart> ovChipkaarten = new ArrayList<>();
//
//            String findAllById_query = "SELECT * FROM ov_Chipkaart WHERE reiziger_id = ?";
//            PreparedStatement pst = conn.prepareStatement(findAllById_query);
//            pst.setInt(1, reiziger.getId());
//
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                int kaart_nummer = rs.getInt("kaart_nummer");
//                Date geldig_tot = rs.getDate("geldig_tot");
//                int klasse = rs.getInt("klasse");
//                int saldo = rs.getInt("saldo");
//
//                OVChipkaart ovChipkaart = new OVChipkaart();
//                ovChipkaart.setKaart_nummer(kaart_nummer);
//                ovChipkaart.setGeldig_tot(geldig_tot);
//                ovChipkaart.setKlasse(klasse);
//                ovChipkaart.setSaldo(saldo);
//
//                ovChipkaarten.add(ovChipkaart);
//            }
//            rs.close();
//            pst.close();
//            return ovChipkaarten;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            List<OVChipkaart> ovChipkaarten = new ArrayList<>();

            String findAll_query = "SELECT * FROM ov_Chipkaart";
            PreparedStatement pst = conn.prepareStatement(findAll_query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int kaart_nummer = rs.getInt("kaart_nummer");
                Date geldig_tot = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                int saldo = rs.getInt("saldo");

                OVChipkaart ovChipkaart = new OVChipkaart();
                ovChipkaart.setKaart_nummer(kaart_nummer);
                ovChipkaart.setGeldig_tot(geldig_tot);
                ovChipkaart.setKlasse(klasse);
                ovChipkaart.setSaldo(saldo);
                ovChipkaarten.add(ovChipkaart);
            }
            rs.close();
            pst.close();
            return ovChipkaarten;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
