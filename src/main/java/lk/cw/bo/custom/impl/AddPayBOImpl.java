package lk.cw.bo.custom.impl;

import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.AddPayBO;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.AddPayDAO;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dao.custom.PaymentDAO;
import lk.cw.dao.custom.impl.AddPayDAOImpl;
import lk.cw.dto.PaymentDTO;
import lk.cw.entity.Patient;
import lk.cw.entity.Patient_Registration;
import lk.cw.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddPayBOImpl implements AddPayBO {

    AddPayDAO addPayDAO = new AddPayDAOImpl();
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
 //   PatientRegistrationBO patientRegistrationBO = (PatientRegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REGISTRATION);
   // TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_SESSION);
    PatientRegDAO patientRegistrationDAO = (PatientRegDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT_REG);

    @Override
    public boolean save(PaymentDTO paymentDTO) throws IOException, SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Patient patient = session.get(Patient.class, paymentDTO.getPatientId());

            if (patient == null) {
                System.out.println("Patient not found for PatientID: " + paymentDTO.getPatientId());
                return false;
            }

            Payment payment = new Payment(
                    paymentDTO.getPaymentId(),
                    patient, // Patient object
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getStatus()
            );

            addPayDAO.save(payment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }





    @Override
    public String getNextId() throws SQLException, IOException {
        return paymentDAO.getNextId();
    }



    @Override
    public boolean update(PaymentDTO paymentDTO) throws IOException, SQLException {
//        Session session = FactoryConfiguration.getInstance().getSession();
//
//        Patient patient = session.get(Patient.class, paymentDTO.getPatientId());
//        if (patient == null) {
//            return false;
//        }
//        Payment payment = new Payment(
//                paymentDTO.getPaymentId(),
//                patient,
//                paymentDTO.getAmount(),
//                paymentDTO.getPaymentDate(),
//                paymentDTO.getStatus()
//        );
//        return paymentDAO.update(payment);    }
        return false;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return paymentDAO.delete(ID);
    }
}
