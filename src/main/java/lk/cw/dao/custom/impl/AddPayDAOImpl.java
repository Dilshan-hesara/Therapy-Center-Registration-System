package lk.cw.dao.custom.impl;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.custom.AddPayDAO;
import lk.cw.dto.PaymentDTO;
import lk.cw.entity.Patient;
import lk.cw.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddPayDAOImpl implements AddPayDAO {

    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.paymentId FROM Payment l ORDER BY l.paymentId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }

        return "P001";
    }

    @Override
    public List<Payment> getAll() throws SQLException, IOException {
        return List.of();
    }

//    @Override
//    public boolean save(Payment payment) throws SQLException, IOException {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(payment);
//        transaction.commit();
//        session.close();
//        return true;
//    }

    @Override
    public boolean save(Payment payment) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Patient patient = session.get(Patient.class, payment.getPatient().getPatientId());

            if (patient == null) {
             //   System.out.println("Patient  " + payment.getPatient().getPatientId());
                return false;
            }

            payment.setPatient(patient);

            session.save(payment);
            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }



    @Override
    public boolean update(Payment payment) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(payment);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Payment payment = new Payment();
        payment.setPaymentId(ID);
        session.remove(payment);


        transaction.commit();
        session.close();

        return true;
    }

    public boolean save(ArrayList<PaymentDTO> paymentDTOS) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();


        try {
//            if (paymentDTOS != null) {
//            for (int i = 0; i < paymentDTOS.size(); i++) {
//                PaymentDTO dto = paymentDTOS.get(i);
//                System.out.println("\nPayment #" + (i + 1) + ":");
//                System.out.println("ID: " + dto.getPaymentId());
//                System.out.println("Amount: " + dto.getAmount());
//                System.out.println("Date: " + dto.getPaymentDate());
//                System.out.println("Status: " + dto.getStatus());
//                System.out.println("Pation: " + dto.getPatientId());
//                // Add any other fields you want to print
//            }

//            for (PaymentDTO dto : paymentDTOS) {
//                Payment payment = new Payment(
//                        dto.getPaymentId(),//P005
//                        dto.getStatus(),//PAY PENDING
//                        dto.getAmount(),//23
//
//                        dto.getPaymentDate(),//
//
//                        dto.getPatientId() //P001
//                );
//                session.save(payment);
//            }

            for (PaymentDTO dto : paymentDTOS) {
                System.out.println("patientId = " + dto.getPatientId()); // DEBUG

                Patient patient = session.get(Patient.class, dto.getPatientId());

                Payment payment = new Payment(
                        dto.getPaymentId(),
                        dto.getStatus(),
                        dto.getAmount(),
                        dto.getPaymentDate(),
                        patient
                );

                session.save(payment);
            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean saved(PaymentDTO paymentDTO) {
        return false;
    }

//    @Override
//    public boolean save(ArrayList<PaymentDTO> paymentDTOS) throws IOException {
////        saved(paymentDTOS)
//        return false;
//    }

//
//    @Override
//    public boolean save(ArrayList<PaymentDTO> paymentDTOS) throws IOException {
//
//        // Print all payment DTOs
//        System.out.println("\n==== Printing Payment DTOs ====");
//        System.out.println("Number of payments: " + (paymentDTOS == null ? 0 : paymentDTOS.size()));
//
//        if (paymentDTOS != null) {
//            for (int i = 0; i < paymentDTOS.size(); i++) {
//                PaymentDTO dto = paymentDTOS.get(i);
//                System.out.println("\nPayment #" + (i + 1) + ":");
//                System.out.println("ID: " + dto.getPaymentId());
//                System.out.println("Amount: " + dto.getAmount());
//                System.out.println("Date: " + dto.getPaymentDate());
//                System.out.println("Status: " + dto.getStatus());
//                System.out.println("Pation: " + dto.getPatientId());
//                // Add any other fields you want to print
//            }
//        }
//        System.out.println("==== End of Payment DTOs ====\n");
//        return false;
//    }


//    private Payment convertToEntity(PaymentDTO dto) {
//        Payment payment = new Payment();
//
//        payment.setPaymentId(dto.getPaymentId());
//        payment.setAmount(dto.getAmount());
//        payment.setPaymentDate(dto.getPaymentDate());
//        payment.setStatus(dto.getStatus());
//
//        if (dto.getPatientId() != null) {
//            Patient patient = new Patient();
//            patient.setPatientId(dto.getPatientId());
//            payment.setPatient(patient);
//        }
//
//        return payment;
//    }

}
