package lk.cw.dao.custom;

import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.CrudDAO;
import lk.cw.entity.TherapyProgram;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram> {


    public TherapyProgram findById(String programId) throws SQLException, ClassNotFoundException ;
    public ArrayList<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException ;

    ArrayList<String> getProgramList();
}
