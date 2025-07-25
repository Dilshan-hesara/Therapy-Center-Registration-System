package lk.cw.bo.custom.impl;

import lk.cw.bo.custom.PatientBO;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.PatientDAO;
import lk.cw.dao.custom.QueryDAO;
import lk.cw.dto.PatientDTO;
import lk.cw.entity.Patient;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO = (PatientDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT);

    @Override
    public boolean save(PatientDTO patientDTO) throws IOException, SQLException {
        System.out.println("PatientBOImpl Saving ID: " + patientDTO.getPatientId()); // Debugging

        return patientDAO.save(new Patient(patientDTO.getPatientId(),patientDTO.getName(),patientDTO.getBirthday(),patientDTO.getContactNumber(),patientDTO.getMedicalHistory()));
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        return patientDAO.getNextId();
    }

    @Override
    public List<PatientDTO> getAll() throws SQLException, IOException {
        List<Patient> patients = patientDAO.getAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();

        for (Patient patient : patients) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setPatientId(patient.getPatientId());
            patientDTO.setName(patient.getName());
            patientDTO.setBirthday(patient.getBirthday());
            patientDTO.setContactNumber(patient.getContactNumber());
            patientDTO.setMedicalHistory(patient.getMedicalHistory());
            patientDTOS.add(patientDTO);
        }
        return patientDTOS;
    }

    @Override
    public boolean update(PatientDTO patientDTO) throws IOException, SQLException {
        return patientDAO.update(new Patient(patientDTO.getPatientId(),patientDTO.getName(),patientDTO.getBirthday(),patientDTO.getContactNumber(),patientDTO.getMedicalHistory()));

    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return patientDAO.delete(ID);
    }

    @Override
    public PatientDTO findById(String patientId) throws SQLException, ClassNotFoundException {
        Patient patient = patientDAO.findById(patientId);
        return new PatientDTO(patient.getPatientId(), patient.getName(), patient.getBirthday(), patient.getContactNumber(), patient.getMedicalHistory());
    }

    @Override
    public ArrayList<String> getAllPatientIds() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> allIds = new ArrayList<>();
        ArrayList<String>all = patientDAO.getAllPatientIDs();
        for(String p: all){
            allIds.add(p);

        }
        return allIds;
    }

    @Override
    public int getTotalPatients() throws SQLException, ClassNotFoundException, IOException {
        return patientDAO.getTotalPatients();
    }

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    @Override
    public List<PatientDTO> getPatientsEnrolledInPrograms() throws IOException {
        List<Patient> patients = queryDAO.getPatientsEnrolledInPrograms();
        List<PatientDTO> patientDTOS = new ArrayList<>();

        for (Patient patient : patients) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setPatientId(patient.getPatientId());
            patientDTO.setName(patient.getName());
            patientDTO.setBirthday(patient.getBirthday());
            patientDTO.setContactNumber(patient.getContactNumber());
            patientDTO.setMedicalHistory(patient.getMedicalHistory());
            patientDTOS.add(patientDTO);

            patientDTOS.add(patientDTO);
        }
        return patientDTOS;
    }

}
