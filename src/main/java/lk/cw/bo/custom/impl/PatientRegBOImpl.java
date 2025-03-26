package lk.cw.bo.custom.impl;

import lk.cw.bo.custom.PatientRegBO;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dao.custom.TherapyProgramDAO;
import lk.cw.dto.PatientRegistrationDTO;
import lk.cw.dto.TherapyProgramDTO;
import lk.cw.entity.Patient;
import lk.cw.entity.Patient_Registration;
import lk.cw.entity.TherapyProgram;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRegBOImpl implements PatientRegBO {

    PatientRegDAO patientRegistrationDAO = (PatientRegDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT_REG);

    @Override
    public boolean save(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException {
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
                patientRegistrationDTO.getRegistrationDate()
        );
        return patientRegistrationDAO.save(patient_registration);
    }

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
            if (patientRegistration.getPatient() != null) {
                patientRegistrationDTO.setPatientId(patientRegistration.getPatient().getPatientId());
            } else {
                patientRegistrationDTO.setPatientId("N/A");
            }
            if (patientRegistration.getTherapyProgram() != null) {
                patientRegistrationDTO.setProgramId(patientRegistration.getTherapyProgram().getProgramId());
            } else {
                patientRegistrationDTO.setProgramId("N/A");
            }
            patientRegistrationDTO.setRegistrationDate(patientRegistration.getRegistrationDate());
            patientRegistrationDTOs.add(patientRegistrationDTO);
        }
        return patientRegistrationDTOs;
    }
    @Override
    public ArrayList<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> allIds = new ArrayList<>();
        ArrayList<String>all = therapyProgramDAO.getAllProgramIDs();
        for(String p: all){
            allIds.add(p);

        }
        return allIds;
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
                patientRegistrationDTO.getRegistrationDate()
        );
        return patientRegistrationDAO.update(patient_registration);    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return patientRegistrationDAO.delete(ID);
    }

    @Override
    public Patient_Registration findById(String patientId) throws SQLException, ClassNotFoundException {
        return patientRegistrationDAO.findById(patientId);
    }

    TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.THERAPYOROGRAM);



}
