package lk.cw.bo.custom.impl;

import lk.cw.bo.BOFactory;
import lk.cw.bo.custom.PatientBO;
import lk.cw.bo.custom.PaymentBO;
import lk.cw.bo.custom.TherapySessionBO;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.PatientRegDAO;
import lk.cw.dao.custom.PaymentDAO;
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

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
//    PatientBO patientRegistrationBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REG);
//    TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYSESSION);
//    PatientRegDAO patientRegistrationDAO = (PatientRegDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT_REG);
//

//    @Override
//    public boolean save(PaymentDTO paymentDTO) throws IOException, SQLException, ClassNotFoundException {
//        return false;
//    }

//    @Override
//    public String getNextId() throws SQLException, IOException {
//        return paymentDAO.getNextId();
//    }

    @Override
    public List<PaymentDTO> getAll() throws SQLException, IOException {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDTO> paymentDTOS = new ArrayList<>();

        for (Payment payment : payments) {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentId(payment.getPaymentId());
            if (payment.getPatient() != null) {
                paymentDTO.setPatientId(payment.getPatient().getPatientId());
            }else {
                paymentDTO.setPatientId("N/A");
            }
            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setPaymentDate(payment.getPaymentDate());
            paymentDTO.setStatus(payment.getStatus());
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }

//    @Override
//    public boolean update(PaymentDTO paymentDTO) throws IOException, SQLException {
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
//
//    @Override
//    public boolean delete(String ID) throws SQLException, IOException {
//        return paymentDAO.delete(ID);
//    }

}
