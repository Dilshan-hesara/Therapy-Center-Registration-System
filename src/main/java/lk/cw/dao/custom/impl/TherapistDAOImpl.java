package lk.cw.dao.custom.impl;

import lk.cw.dao.custom.TherapistDAO;
import lk.cw.entity.Therapist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        return "";
    }

    @Override
    public List<Therapist> getAll() throws SQLException, IOException {
        return List.of();
    }

    @Override
    public boolean save(Therapist entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean update(Therapist entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return false;
    }
}
