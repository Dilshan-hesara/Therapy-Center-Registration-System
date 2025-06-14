package lk.cw.bo.custom;

import lk.cw.bo.SuperBO;
import lk.cw.dto.TherapySessionDTO;
import lk.cw.entity.Therapy_Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TherapySessionBO extends SuperBO {

  //  public boolean save(TherapySessionDTO therapySessionDTO) throws IOException, SQLException;
    public String getNextId() throws SQLException, IOException;
    public List<TherapySessionDTO> getAll() throws SQLException, IOException;
    public boolean update(TherapySessionDTO therapySessionDTO) throws IOException, SQLException;
    public boolean delete(String ID) throws SQLException, IOException;

    boolean save(TherapySessionDTO therapySessionDTO) throws IOException, SQLException;


  public List<Therapy_Session> searchTherapySession(String name) throws SQLException, IOException, ClassNotFoundException;
  public List<Therapy_Session> searchTherapistTherapySession(String therapistId) throws SQLException, IOException, ClassNotFoundException;

}
