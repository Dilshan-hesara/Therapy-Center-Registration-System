package lk.cw.dao.custom.impl;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.TherapySessionDAO;
import lk.cw.entity.Patient;
import lk.cw.entity.Therapist;
import lk.cw.entity.Therapy_Session;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.sessionId FROM Therapy_Session l ORDER BY l.sessionId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }

        return "S001";
    }

    @Override
    public List<Therapy_Session> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Therapy_Session> query = session.createQuery("SELECT c FROM Therapy_Session c", Therapy_Session.class);
        List<Therapy_Session> therapySessions = query.list();

        transaction.commit();
        session.close();
        return therapySessions;
    }


    @Override
    public boolean save(Therapy_Session therapySession) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Therapy Session එක save කරනවා
            session.save(therapySession);

            String hql = "UPDATE Patient_Registration pr " +
                    "SET pr.sessionCount = pr.sessionCount + 1 " +
                    "WHERE pr.patient.patientId = :patientId ";

            Query query = session.createQuery(hql);
            query.setParameter("patientId", therapySession.getPatient().getPatientId());


//            query.setParameter("programId", therapySession.getPatient().getProgram().getProgramId());
            query.executeUpdate();

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







    @Override
    public boolean update(Therapy_Session therapy_session) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Therapist therapist = session.get(Therapist.class, therapy_session.getSessionId());
        Patient patient = session.get(Patient.class, therapy_session.getSessionId());

        if(therapist == null || patient==null){
            return false;
        }

        session.update(therapy_session);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Therapy_Session therapy_session = new Therapy_Session();
        therapy_session.setSessionId(ID);
        session.remove(therapy_session);


        transaction.commit();
        session.close();

        return true;
    }
    @Override
    public List<Therapy_Session> getSessionByPatientId(String patientId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Therapy_Session> list = session.createQuery("FROM Therapy_Session WHERE patient.patientId = :id", Therapy_Session.class)
                .setParameter("id", patientId)
                .list();
        transaction.commit();
        session.close();
        return list;
    }
    @Override
    public List<Therapy_Session> getTherapistById(String therapistId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Therapy_Session> list = session.createQuery("FROM Therapy_Session WHERE therapist.therapistId = :therapistId", Therapy_Session.class)
                .setParameter("therapistId", therapistId)
                .list();
        transaction.commit();
        session.close();
        return list;
    }
}
