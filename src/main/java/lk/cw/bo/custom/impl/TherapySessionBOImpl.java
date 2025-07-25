package lk.cw.bo.custom.impl;

import lk.cw.bo.custom.TherapySessionBO;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.*;
import lk.cw.dto.TherapySessionDTO;
import lk.cw.entity.Patient;
import lk.cw.entity.Patient_Registration;
import lk.cw.entity.Therapist;
import lk.cw.entity.Therapy_Session;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {

    TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.THERAPYSESSION);


   AddPayDAO addPayDAO =(AddPayDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ADDPAYMENT);


PatientRegDAO patientRegistrationDAO = (PatientRegDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT_REG);

    @Override
    public boolean save(TherapySessionDTO therapySessionDTO) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Therapist therapist = session.get(Therapist.class, therapySessionDTO.getTherapistId());
            Patient patient = session.get(Patient.class, therapySessionDTO.getPatientId());
            if (therapist == null || patient == null) {
                return false;
            }

            Therapy_Session therapySession = new Therapy_Session(
                    therapySessionDTO.getSessionId(),
                    Date.valueOf(therapySessionDTO.getSessionDate().toLocalDate()),
                    therapySessionDTO.getSessionTime(),
                    therapySessionDTO.getStatus(),
                    therapist,
                    patient
            );

            boolean isSaved = therapySessionDAO.save(therapySession);
            if (!isSaved) {
                transaction.rollback();
                return false;
            }
            boolean isSaved2 = addPayDAO.save(therapySessionDTO.getPaymentDTOS());

            if (!isSaved2) {
                System.out.println("wdfege");

                transaction.rollback();
                return false;
            }
            System.out.println("wdfege");


            boolean isSaved3 = patientRegistrationDAO.reduesBal(therapySessionDTO.getPaymentDTOS());
            if (!isSaved3) {
                transaction.rollback();
                return false;
            }

            transaction.commit();
            return true;

        //    Patient patient = session.get(Patient.class, dto.getPatientId());



        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public String getNextId() throws SQLException, IOException {
        return therapySessionDAO.getNextId();
    }

    @Override
    public List<TherapySessionDTO> getAll() throws SQLException, IOException {
        List<Therapy_Session> therapy_sessions = therapySessionDAO.getAll();
        List<TherapySessionDTO> therapySessionDTOS = new ArrayList<>();

        for (Therapy_Session therapy_session : therapy_sessions) {
            TherapySessionDTO therapySessionDTO = new TherapySessionDTO();
            therapySessionDTO.setSessionId(therapy_session.getSessionId());
            therapySessionDTO.setSessionDate(therapy_session.getSessionDate());
            therapySessionDTO.setSessionTime(therapy_session.getSessionTime());
            therapySessionDTO.setStatus(therapy_session.getStatus());
            if (therapy_session.getTherapist() != null) {
                therapySessionDTO.setTherapistId(therapy_session.getTherapist().getTherapistId());
            } else {
                therapySessionDTO.setTherapistId("N/A");
            }
            if (therapy_session.getPatient() != null) {
                therapySessionDTO.setPatientId(therapy_session.getPatient().getPatientId());
            } else {
                therapySessionDTO.setPatientId("N/A");
            }
            therapySessionDTOS.add(therapySessionDTO);
        }
        return therapySessionDTOS;
    }

    @Override
    public boolean update(TherapySessionDTO therapySessionDTO) throws IOException, SQLException {

        Session session = FactoryConfiguration.getInstance().getSession();

        Therapist therapist = session.get(Therapist.class, therapySessionDTO.getTherapistId());
        Patient patient = session.get(Patient.class, therapySessionDTO.getPatientId());

        if (therapist == null || patient == null) {
            return false;
        }

        Therapy_Session therapySession = new Therapy_Session(
                therapySessionDTO.getSessionId(),
                Date.valueOf(therapySessionDTO.getSessionDate().toLocalDate()),
                therapySessionDTO.getSessionTime(),
                therapySessionDTO.getStatus(),
                therapist,
                patient
        );

        return therapySessionDAO.update(therapySession);
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return therapySessionDAO.delete(ID);
    }
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT);

    @Override
    public List<Therapy_Session> searchTherapySession(String name) throws SQLException, IOException, ClassNotFoundException {
        Patient patient = patientDAO.getPatientByName(name);

        if (patient != null) {
            return therapySessionDAO.getSessionByPatientId(patient.getPatientId());
        }else {
            return null;
        }
    }

    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.THERAPIST);

    @Override
    public List<Therapy_Session> searchTherapistTherapySession(String therapistId) throws SQLException, IOException, ClassNotFoundException {

        Therapist therapist = therapistDAO.getTherapistById(therapistId);
        if (therapist != null) {
            return therapySessionDAO.getTherapistById(therapist.getTherapistId());
        }else {
            return null;
        }
    }
}

//    @Override
//    public boolean save(TherapySessionDTO therapySessionDTO) throws IOException {
//
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//
//        try {
//            // Therapist සහ Patient entity objects ලබා ගන්නවා
//            Therapist therapist = session.get(Therapist.class, therapySessionDTO.getTherapistId());
//            Patient patient = session.get(Patient.class, therapySessionDTO.getPatientId());
//            if (therapist == null || patient == null) {
//                return false;
//            }
//
//            // Therapy_Session entity එක create කරනවා
//            Therapy_Session therapySession = new Therapy_Session(
//                    therapySessionDTO.getSessionId(),
//                    Date.valueOf(therapySessionDTO.getSessionDate().toLocalDate()),
//                    therapySessionDTO.getSessionTime(),
//                    therapySessionDTO.getStatus(),
//                    therapist,
//                    patient
//            );
//
//            boolean isSaved = therapySessionDAO.save(therapySession);
//            if (!isSaved) {
//                transaction.rollback();
//                return false;
//            }
//            boolean isSaved2 = addPayDAO.save(therapySession.paymentDTOS());
//            if (!isSaved2) {
//                transaction.rollback();
//                return false;
//            }
//
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace();
//            return false;
//        } finally {
//            session.close();
//        }
//
//        return isSaved;
//    }
//