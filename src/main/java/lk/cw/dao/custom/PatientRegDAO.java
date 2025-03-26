package lk.cw.dao.custom;

import lk.cw.dao.CrudDAO;
import lk.cw.entity.Patient_Registration;

import java.sql.SQLException;

public interface PatientRegDAO extends CrudDAO<Patient_Registration> {

    public Patient_Registration findById(String patientId) throws SQLException, ClassNotFoundException;

}
