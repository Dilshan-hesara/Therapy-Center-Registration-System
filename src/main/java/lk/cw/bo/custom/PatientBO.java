package lk.cw.bo.custom;

import lk.cw.bo.SuperBO;
import lk.cw.dto.PatientDTO;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientBO extends SuperBO {
    public boolean save(PatientDTO patientDTO) throws IOException, SQLException;
    public String getNextId() throws SQLException, IOException;
    public List<PatientDTO> getAll() throws SQLException, IOException;
    public boolean update(PatientDTO patientDTO) throws IOException, SQLException;
    public boolean delete(String ID) throws SQLException, IOException;
    public PatientDTO findById(String patientId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllPatientIds() throws SQLException, ClassNotFoundException, IOException;
    public int getTotalPatients() throws SQLException, ClassNotFoundException, IOException;




    }
