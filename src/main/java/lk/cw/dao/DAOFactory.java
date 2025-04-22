package lk.cw.dao;

import lk.cw.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
      USER, PATIENT,THERAPIST,THERAPYOROGRAM, THERAPYSESSION,PATIENT_REG ,PAYMENT, ADDPAYMENT
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

            case PATIENT_REG:
                return new PatientRegDAOImpl();

            case THERAPYSESSION:
                return new TherapySessionDAOImpl();

                case PAYMENT:
                    return new PaymentDAOImpl();
            default:
                return null;
        }
    }
}

