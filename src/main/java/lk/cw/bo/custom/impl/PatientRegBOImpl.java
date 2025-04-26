package lk.cw.bo.custom.impl;

import lk.cw.bo.custom.PatientRegBO;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.AddPayDAO;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dao.custom.TherapyProgramDAO;
import lk.cw.dto.PatientRegistrationDTO;
import lk.cw.dto.TherapyProgramDTO;
import lk.cw.entity.Patient;
import lk.cw.entity.Patient_Registration;
import lk.cw.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRegBOImpl implements PatientRegBO {

    PatientRegDAO patientRegistrationDAO = (PatientRegDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT_REG);


   // PatientRegistrationDAO patientRegistrationDAO = (PatientRegistrationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT_REGISTRATION);

    AddPayDAO addPayDAO =(AddPayDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ADDPAYMENT);

    @Override
    public boolean save(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Patient patient = session.get(Patient.class, patientRegistrationDTO.getPatientId());
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, patientRegistrationDTO.getProgramId());

            if (patient == null || therapyProgram == null) {
                transaction.rollback();
                return false;
            }

            Patient_Registration patient_registration = new Patient_Registration(
                    patientRegistrationDTO.getRegistrationId(),
                    patient,
                    therapyProgram,
                    patientRegistrationDTO.getRegistrationDate(),
                    patientRegistrationDTO.getRegisterFee(),
                    patientRegistrationDTO.getBalance()
            );

            boolean isSaved = patientRegistrationDAO.save(patient_registration);

            if (!isSaved) {
                transaction.rollback();
                return false;
            }

//            boolean isSaved2 = addPayDAO.save(patientRegistrationDTO.getPaymentDTOS());
//
//            if (!isSaved2) {
//                transaction.rollback();
//                return false;
//            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

//    @Override
//    public boolean save(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//        Patient patient = session.get(Patient.class, patientRegistrationDTO.getPatientId());
//        TherapyProgram therapyProgram = session.get(TherapyProgram.class, patientRegistrationDTO.getProgramId());
//
//        if (patient == null || therapyProgram == null) {
//            return false;
//        }
//
//        Patient_Registration patient_registration = new Patient_Registration(
//                patientRegistrationDTO.getRegistrationId(),
//                patient,
//                therapyProgram,
//                patientRegistrationDTO.getRegistrationDate(),
//                patientRegistrationDTO.getRegisterFee(),
//                patientRegistrationDTO.getBalance()
//        );
//        boolean isSaved = patientRegistrationDAO.save(patient_registration);
//        if (isSaved) {
//            transaction.rollback();
//            return false;
//        }
//
//        boolean isSaved2 = addPayDAO.save(patientRegistrationDTO.getPaymentDTOS());
//
//        if (!isSaved2) {
//            System.out.println("wdfege");
//
//            transaction.rollback();
//            return false;
//        }
//        System.out.println("wdfege");
//
//
//    }

    @Override
    public String getNextId() throws SQLException, IOException {
        return patientRegistrationDAO.getNextId();
    }

    @Override
    public List<PatientRegistrationDTO> getAll() throws SQLException, IOException {
        List<Patient_Registration> patientRegistrations = patientRegistrationDAO.getAll();
        List<PatientRegistrationDTO> patientRegistrationDTOs = new ArrayList<>();

        for (Patient_Registration patientRegistration : patientRegistrations) {
            PatientRegistrationDTO patientRegistrationDTO = new PatientRegistrationDTO();

            patientRegistrationDTO.setRegistrationId(patientRegistration.getRegistrationId());
                patientRegistrationDTO.setPatientId(patientRegistration.getPatient().getPatientId());
                patientRegistrationDTO.setProgramId(patientRegistration.getTherapyProgram().getProgramId());
            patientRegistrationDTO.setRegistrationDate(patientRegistration.getRegistrationDate());
            patientRegistrationDTO.setRegisterFee(patientRegistration.getRegisterFee());
            patientRegistrationDTO.setBalance(patientRegistration.getBalance());
            patientRegistrationDTO.setSessionCount(patientRegistration.getSessionCount());
            patientRegistrationDTOs.add(patientRegistrationDTO);
        }
        return patientRegistrationDTOs;
    }

    @Override
    public boolean update(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException {

        Session session = FactoryConfiguration.getInstance().getSession();

        Patient patient = session.get(Patient.class, patientRegistrationDTO.getPatientId());
        TherapyProgram therapyProgram = session.get(TherapyProgram.class, patientRegistrationDTO.getProgramId());

        if (patient == null || therapyProgram == null) {
            return false;
        }

        Patient_Registration patient_registration = new Patient_Registration(
                patientRegistrationDTO.getRegistrationId(),
                patient,
                therapyProgram,
                patientRegistrationDTO.getRegistrationDate(),
                patientRegistrationDTO.getRegisterFee(),
                patientRegistrationDTO.getBalance()
        );
        return patientRegistrationDAO.update(patient_registration);    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return patientRegistrationDAO.delete(ID);
    }




    @Override
    public boolean updateBalance(String patientId) throws SQLException, ClassNotFoundException, IOException {
        return patientRegistrationDAO.updateBalance(patientId);
    }

    @Override
    public double getBalanceByPatientId(String patientId) throws IOException {
        return patientRegistrationDAO.getBalanceByPatientId(patientId);
    }

    @Override
    public boolean saved(PatientRegistrationDTO patientRegistrationDTO) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Patient patient = session.get(Patient.class, patientRegistrationDTO.getPatientId());
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, patientRegistrationDTO.getProgramId());

            if (patient == null || therapyProgram == null) {
                transaction.rollback();
                return false;
            }

            Patient_Registration patient_registration = new Patient_Registration(
                    patientRegistrationDTO.getRegistrationId(),
                    patient,
                    therapyProgram,
                    patientRegistrationDTO.getRegistrationDate(),
                    patientRegistrationDTO.getRegisterFee(),
                    patientRegistrationDTO.getBalance()
            );

            boolean isSaved = patientRegistrationDAO.save(patient_registration);

            if (!isSaved) {
                transaction.rollback();
                return false;
            }

            boolean isSaved2 = addPayDAO.save(patientRegistrationDTO.getPaymentDTOS());

//            if (!isSaved2) {
//                transaction.rollback();
//                return false;
//            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
