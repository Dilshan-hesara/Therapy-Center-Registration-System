package lk.cw.bo.custom;

import lk.cw.bo.SuperBO;
import lk.cw.dto.TherapyProgramDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TherapyProgramBO extends SuperBO {

    public TherapyProgramDTO findById(String programId) throws SQLException, ClassNotFoundException;

    public ArrayList<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException;

    public String getNextId() throws SQLException, IOException;

    public List<TherapyProgramDTO> getAll() throws SQLException, IOException;

    public boolean save(TherapyProgramDTO therapyProgramDTO) throws SQLException, IOException;

    public boolean update(TherapyProgramDTO therapyProgramDTO) throws SQLException, IOException;

    public boolean delete(String ID) throws SQLException, IOException;

    public int getTotalPrograms() throws SQLException, ClassNotFoundException, IOException;

}
