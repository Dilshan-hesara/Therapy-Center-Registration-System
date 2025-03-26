package lk.cw.dao.custom;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.CrudDAO;
import lk.cw.dto.PatientDTO;
import lk.cw.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientDAO extends CrudDAO<Patient> {

    public Patient findById(String patientId) throws SQLException, ClassNotFoundException ;
    public ArrayList<String> getAllPatientIDs() throws SQLException, ClassNotFoundException, IOException;
    public int getTotalPatients() throws SQLException, ClassNotFoundException, IOException;
    public List<Object[]> getPatientsBySessionId(String sessionId) throws SQLException, ClassNotFoundException, IOException;
}
