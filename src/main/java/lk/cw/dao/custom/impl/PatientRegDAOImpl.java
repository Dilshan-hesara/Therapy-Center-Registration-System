package lk.cw.dao.custom.impl;

import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.entity.Patient_Registration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PatientRegDAOImpl implements PatientRegDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        return "";
    }

    @Override
    public List<Patient_Registration> getAll() throws SQLException, IOException {
        return List.of();
    }

    @Override
    public boolean save(Patient_Registration entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean update(Patient_Registration entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return false;
    }
}
