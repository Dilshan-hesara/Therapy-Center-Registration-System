package lk.cw.dao.custom;

import lk.cw.dao.CrudDAO;
import lk.cw.entity.Therapy_Session;
import lk.cw.tm.TherapySessionTM;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TherapySessionDAO extends CrudDAO <Therapy_Session>{

    public List<Therapy_Session> getSessionByPatientId(String patientId) throws SQLException, ClassNotFoundException, IOException;
    public List<Therapy_Session> getTherapistById(String therapistId) throws SQLException, ClassNotFoundException, IOException;
}
