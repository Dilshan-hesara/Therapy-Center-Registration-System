package lk.cw.dao.custom;

import lk.cw.dao.CrudDAO;
import lk.cw.entity.Patient_Registration;

import java.io.IOException;
import java.sql.SQLException;

public interface PatientRegDAO extends CrudDAO<Patient_Registration> {

    public Patient_Registration findById(String patientId) throws SQLException, ClassNotFoundException,IOException;
    public double getBalanceByPatientId(String patientId) throws IOException,IOException ;

    public boolean updateBalance(String patientId) throws SQLException, ClassNotFoundException, IOException ;

    int getSessionCount(String patienid) throws IOException;
}
