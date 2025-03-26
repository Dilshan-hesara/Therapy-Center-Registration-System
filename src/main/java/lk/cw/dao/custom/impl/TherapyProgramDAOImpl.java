package lk.cw.dao.custom.impl;

import lk.cw.dao.custom.TherapyProgramDAO;
import lk.cw.entity.TherapyProgram;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        return "";
    }

    @Override
    public List<TherapyProgram> getAll() throws SQLException, IOException {
        return List.of();
    }

    @Override
    public boolean save(TherapyProgram entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean update(TherapyProgram entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return false;
    }
}
