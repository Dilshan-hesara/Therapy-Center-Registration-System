package lk.cw.dao.custom;

import lk.cw.dao.SuperDAO;
import lk.cw.entity.Patient;


import java.io.IOException;
import java.util.List;

public interface QueryDAO extends SuperDAO {
    public List<Patient> getPatientsEnrolledInPrograms() throws IOException;
}
