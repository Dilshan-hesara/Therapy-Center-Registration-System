package lk.cw.dao.custom;

import lk.cw.dao.CrudDAO;
import lk.cw.entity.Payment;

import java.io.IOException;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    public List<Payment> searchPayment(String PatientId) throws IOException;

    }
