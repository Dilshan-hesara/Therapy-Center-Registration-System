package lk.cw.dao.custom;

import lk.cw.dao.CrudDAO;
import lk.cw.dto.PaymentDTO;
import lk.cw.entity.Payment;

import java.io.IOException;
import java.util.ArrayList;

public interface AddPayDAO extends CrudDAO<Payment> {
    boolean save(ArrayList<PaymentDTO> paymentDTOS) throws IOException;

    boolean saved(PaymentDTO paymentDTO);
}
