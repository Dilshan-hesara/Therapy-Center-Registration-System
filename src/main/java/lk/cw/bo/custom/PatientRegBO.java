package lk.cw.bo.custom;

import lk.cw.bo.SuperBO;
import lk.cw.dto.PatientRegistrationDTO;
import lk.cw.dto.TherapyProgramDTO;
import lk.cw.entity.Patient_Registration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PatientRegBO extends SuperBO {

    public boolean save(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException;
    public String getNextId() throws SQLException, IOException;
    public List<PatientRegistrationDTO> getAll() throws SQLException, IOException;
    public boolean update(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException;
    public boolean delete(String ID) throws SQLException, IOException;
    public Patient_Registration findById(String patientId) throws SQLException, ClassNotFoundException;


    public List<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException;

}
