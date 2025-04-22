package lk.cw.bo.custom;

import lk.cw.bo.SuperBO;
import lk.cw.dto.PaymentDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface AddPayBO extends SuperBO {

    public boolean save(PaymentDTO paymentDTO) throws IOException, SQLException, ClassNotFoundException ;
    public String getNextId() throws SQLException, IOException;
    public boolean update(PaymentDTO paymentDTO) throws IOException, SQLException ;
    public boolean delete(String ID) throws SQLException, IOException;
}
