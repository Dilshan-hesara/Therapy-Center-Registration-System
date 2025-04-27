package lk.cw.dao.custom;

import lk.cw.dao.CrudDAO;
import lk.cw.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapistDAO extends CrudDAO<Therapist> {
    public Therapist findById(String therapistId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllTherapistIDs() throws SQLException, ClassNotFoundException, IOException;
    public Therapist getTherapistById(String therapistId) throws SQLException, ClassNotFoundException, IOException ;

    public int getTotalTherapists() throws SQLException, ClassNotFoundException, IOException ;
}
