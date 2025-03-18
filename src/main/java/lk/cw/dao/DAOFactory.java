package lk.cw.dao;

import lk.cw.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
       PATIENT, PAYMENT,THERAPIST,THERAPYOROGRAM,THERAPYSESSION,USER
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case USER:
                return (SuperDAO) new UserDAOImpl();


            default:
                return null;
        }
    }
}

