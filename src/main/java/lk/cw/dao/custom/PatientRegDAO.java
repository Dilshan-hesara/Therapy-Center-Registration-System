package lk.cw.dao.custom;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.CrudDAO;
import lk.cw.entity.Patient_Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;

public interface PatientRegDAO extends CrudDAO<Patient_Registration> {

    public Patient_Registration findById(String patientId) throws SQLException, ClassNotFoundException,IOException;
    public double getBalanceByPatientId(String patientId) throws IOException,IOException ;

    public boolean updateBalance(String patientId) throws SQLException, ClassNotFoundException, IOException ;
    }
