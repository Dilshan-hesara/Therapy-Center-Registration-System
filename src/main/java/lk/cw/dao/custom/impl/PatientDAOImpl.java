package lk.cw.dao.custom.impl;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.PatientDAO;
import lk.cw.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.patientId FROM Patient l ORDER BY l.patientId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }

        return "P001";
    }

    @Override
    public List<Patient> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Patient> query = session.createQuery("SELECT p FROM Patient p", Patient.class);
        List<Patient> patients = query.list();

        transaction.commit();
        session.close();
        return patients;
    }

    @Override
    public boolean save(Patient patient) throws IOException, SQLException {
        System.out.println("Saving Patient ID: " + patient.getPatientId());

        if (patient.getPatientId() == null || patient.getPatientId().isEmpty()) {
            throw new SQLException("Patient ID is null!");
        }

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(patient);
        transaction.commit();
        session.close();
        return true;
    }

        @Override
        public Patient find(String id) throws SQLException, ClassNotFoundException {
            try (Session session = FactoryConfiguration.getInstance().getSession()) {
                return session.get(Patient.class, id);
            } catch (Exception e) {
                throw new SQLException("Failed to find patient", e);
            }
        }


    @Override
    public boolean update(Patient patient) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(patient);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Patient patient = new Patient();
        patient.setPatientId(ID);
        session.remove(patient);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Patient findById(String patientId) throws SQLException, ClassNotFoundException {
        Patient patient = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {

            patient = session.get(Patient.class, patientId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch the student by ID: " + patientId);
        }

        return patient;
    }

    @Override
    public ArrayList<String> getAllPatientIDs() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = ("SELECT patientId FROM Patient");

        Query query = session.createQuery(hql);
        ArrayList<String> list = (ArrayList<String>) query.list();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public int getTotalPatients() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Long> query = session.createQuery("SELECT COUNT(p) FROM Patient p", Long.class);
        Long total = query.uniqueResult();

        transaction.commit();
        session.close();
        return total != null ? total.intValue() : 0;
    }

    @Override
    public List<Object[]> getPatientsBySessionId(String sessionId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        return session.createQuery(
                        "SELECT p.id, p.name, ts.sessionDate, ts.sessionTime " +
                                "FROM Patient p " +
                                "JOIN p.therapySessions ts " +
                                "WHERE ts.id = :sessionId", Object[].class)
                .setParameter("sessionId", sessionId)
                .list();



    }
}
