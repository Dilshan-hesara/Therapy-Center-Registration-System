package lk.cw.dao.custom.impl;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.QueryDAO;
import lk.cw.entity.Patient;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<Patient> getPatientsEnrolledInPrograms() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        String hql = "SELECT p \n" +
                "            FROM Patient p \n" +
                "            WHERE \n" +
                "                (SELECT COUNT(DISTINCT pr.therapyProgram.programId) \n" +
                "                 FROM Patient_Registration pr \n" +
                "                 WHERE pr.patient = p) = \n" +
                "                (SELECT COUNT(tp.programId) FROM TherapyProgram tp)";

//        String hql =
//                "SELECT p \n" +
//                        "FROM patient p\n" +
//                        "WHERE (\n" +
//                        "    SELECT COUNT(DISTINCT pr.programId)\n" +
//                        "    FROM patient_registration pr\n" +
//                        "    WHERE pr.patientId = p.PatientID\n" +
//                        ") = (\n" +
//                        "    SELECT COUNT(*) \n" +
//                        "    FROM therapy_program\n" +
//                        ");";


        List<Patient> patients = session.createQuery(hql, Patient.class).getResultList();
        tx.commit();
        session.close();
        return patients;


    }
}
