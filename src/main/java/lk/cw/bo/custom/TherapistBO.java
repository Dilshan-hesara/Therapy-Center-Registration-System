package lk.cw.bo.custom;

import lk.cw.bo.SuperBO;
import lk.cw.dto.TherapistDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TherapistBO extends SuperBO {
    public boolean save(TherapistDTO therapistDTO) throws IOException, SQLException;
    public String getNextId() throws SQLException, IOException;
    public TherapistDTO findById(String therapistId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllTherapistIDs() throws SQLException, ClassNotFoundException, IOException;
    public List<TherapistDTO> getAll() throws SQLException, IOException;
    public boolean update(TherapistDTO therapistDTO) throws IOException, SQLException;
    public boolean delete(String ID) throws SQLException, IOException;

    public int getTotalTherapists() throws SQLException, ClassNotFoundException, IOException ;
}
