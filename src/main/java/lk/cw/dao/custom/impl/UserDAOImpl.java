package lk.cw.dao.custom.impl;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.UserDAO;
import lk.cw.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    public String getPasswordByUserName(String userName) throws Exception {
        System.out.println("Fetching password for username: " + userName);
        String password = null;

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String sql = "SELECT Password FROM User WHERE UserName = :userName";
            NativeQuery<String> query = session.createNativeQuery(sql, String.class);
            query.setParameter("userName", userName);

            password = query.getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error fetching password: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        System.out.println("Password retrieved: " + password);
        return password;
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT u.Id FROM User u ORDER BY u.Id DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }

        return "U001";
    }


    @Override
    public boolean save(User user) throws IOException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public List<User> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<User> query = session.createQuery("SELECT c FROM User c", User.class);
        List<User> customers = query.list();

        transaction.commit();
        session.close();
        return customers;
    }

    @Override
    public boolean update(User user) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(user);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return false;
    }

    public String getRoleByUserName(String userName) throws Exception {
        System.out.println("Fetching role for username: " + userName);
        String role = null;

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String sql = "SELECT Role FROM User WHERE UserName = :userName";
            NativeQuery<String> query = session.createNativeQuery(sql, String.class);
            query.setParameter("userName", userName);

            role = query.getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error fetching role: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        System.out.println("Role retrieved: " + role);
        return role;
    }

}