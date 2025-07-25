package lk.cw.dao.custom.impl;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.TherapyProgramDAO;
import lk.cw.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {


    @Override
    public TherapyProgram findById(String programId) throws SQLException, ClassNotFoundException {
        TherapyProgram therapyProgram = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // Fetch the Student entity using the primary key
            therapyProgram = session.get(TherapyProgram.class, programId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch the student by ID: " + programId);
        }

        return therapyProgram;
    }

    @Override
    public ArrayList<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = ("SELECT programId FROM TherapyProgram");

        Query query = session.createQuery(hql);
        ArrayList<String> list = (ArrayList<String>) query.list();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public ArrayList<String> getProgramList() {
        ArrayList<String> programNames = new ArrayList<>();

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            List<String> names = session.createQuery("SELECT tp.programName FROM TherapyProgram tp", String.class).list();
            programNames.addAll(names);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return programNames;
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.programId FROM TherapyProgram l ORDER BY l.programId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("THP%03d", newIdIndex);
        }

        return "THP001";
    }

    @Override
    public List<TherapyProgram> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<TherapyProgram> query = session.createQuery("SELECT p FROM TherapyProgram p", TherapyProgram.class);
        List<TherapyProgram> therapyPrograms = query.list();

        transaction.commit();
        session.close();
        return therapyPrograms;
    }

    @Override
    public boolean save(TherapyProgram therapyProgram) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(therapyProgram);

        transaction.commit();
        session.close();

        return true;

    }

    @Override
    public boolean update(TherapyProgram therapyProgram) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(therapyProgram);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setProgramId(ID);
        session.remove(therapyProgram);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public int getTotalPrograms() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Long> query = session.createQuery("SELECT COUNT(tp) FROM TherapyProgram tp", Long.class);
        Long total = query.uniqueResult();

        transaction.commit();
        session.close();
        return total != null ? total.intValue() : 0;
    }
}
