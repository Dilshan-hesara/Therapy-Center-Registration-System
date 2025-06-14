package lk.cw.dao.custom.impl;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dto.PaymentDTO;
import lk.cw.entity.Patient;
import lk.cw.entity.Patient_Registration;
import lk.cw.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRegDAOImpl implements PatientRegDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.registrationId FROM Patient_Registration l ORDER BY l.registrationId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("R%03d", newIdIndex);
        }

        return "R001";
    }

    @Override
    public List<Patient_Registration> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Patient_Registration> query = session.createQuery("SELECT c FROM Patient_Registration c", Patient_Registration.class);
        List<Patient_Registration> patientRegistrations = query.list();

        transaction.commit();
        session.close();
        return patientRegistrations;
    }

    @Override
    public boolean save(Patient_Registration patient_registration) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(patient_registration);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Patient_Registration patient_registration) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(patient_registration);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Patient_Registration patient_registration = new Patient_Registration();
        patient_registration.setRegistrationId(ID);
        session.remove(patient_registration);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Patient_Registration findById(String patientId ) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Patient_Registration> query = session.createNativeQuery(
                "SELECT * FROM Patient_Registration WHERE patientId = :pid", Patient_Registration.class);
        query.setParameter("pid", patientId);

        Patient_Registration registration = query.uniqueResult();

        transaction.commit();
        session.close();
        return registration;

    }
    //
    @Override
    public boolean updateBalance(String patientId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("UPDATE Patient_Registration pr SET pr.balance = 0 WHERE pr.patient.patientId = :patientId");
        query.setParameter("patientId", patientId);
        int update = query.executeUpdate();
        transaction.commit();
        session.close();

        return update > 0;
    }

    @Override
    public int getSessionCount(String patientId) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        int count = 0;

        try {
            Query<Integer> query = session.createQuery(
                    "SELECT pr.sessionCount FROM Patient_Registration pr WHERE pr.patient.id = :pid",
                    Integer.class
            );
            query.setParameter("pid", patientId);

            Integer result = query.uniqueResult();
            count = result != null ? result : 0;

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return count;
    }

    @Override
    public boolean reduesBal(ArrayList<PaymentDTO> paymentDTOS) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            for (PaymentDTO dto : paymentDTOS) {

                Patient_Registration registration = session.createQuery(
                                "FROM Patient_Registration pr WHERE pr.patient.patientId = :pid", Patient_Registration.class)
                        .setParameter("pid", dto.getPatientId())
                        .uniqueResult();

                if (registration != null) {
                    double oldBalance = registration.getBalance();
                    double newBalance = oldBalance - dto.getAmount();
                    registration.setBalance(newBalance);

                    System.out.println("Old" + oldBalance + ", New " + newBalance);

                    session.update(registration);
                } else {
                    transaction.rollback();
                    session.close();
                    return false;
                }
            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    double getBalancePatientId(String patientId) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Double balance = session.createQuery("SELECT pr.balance FROM Patient_Registration pr WHERE pr.patient.patientId = :patientId", Double.class)
                .setParameter("patientId", patientId).uniqueResult();

        transaction.commit();
        session.close();
        return balance != null ? balance : 0.00;
    }



    @Override
    public double getBalanceByPatientId(String patientId) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Double balance = session.createQuery("SELECT pr.balance FROM Patient_Registration pr WHERE pr.patient.patientId = :patientId", Double.class)
                .setParameter("patientId", patientId).uniqueResult();

        transaction.commit();
        session.close();
        return balance != null ? balance : 0.00;
    }



}
