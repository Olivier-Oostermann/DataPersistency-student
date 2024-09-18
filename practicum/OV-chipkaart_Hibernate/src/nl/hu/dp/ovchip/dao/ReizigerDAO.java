package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Reiziger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger) throws SQLException;
    Reiziger findById(int reiziger_id) throws SQLException;
    List<Reiziger> findByGbdatum(Date date) throws SQLException;
    List<Reiziger> findAll() throws SQLException;
}
