package lk.cw.dao;

import lk.cw.dao.custom.impl.PatientDAOImpl;
import lk.cw.dao.custom.impl.TherapistDAOImpl;
import lk.cw.dao.custom.impl.TherapyProgramDAOImpl;
import lk.cw.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
      USER, PATIENT,THERAPIST,THERAPYOROGRAM, THERAPYSESSION,PATIENT_REG ,PAYMENT
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case USER:
                return new UserDAOImpl();

            case PATIENT:
                return new PatientDAOImpl();

            case THERAPIST:
                return new TherapistDAOImpl();

            case THERAPYOROGRAM:
                return new TherapyProgramDAOImpl();

            default:
                return null;
        }
    }
}

