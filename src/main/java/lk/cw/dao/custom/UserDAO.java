package lk.cw.dao.custom;

import lk.cw.dao.SuperDAO;
import lk.cw.entity.User;

import java.io.IOException;

public interface UserDAO extends SuperDAO {
    public String getPasswordByUserName(String userName) throws Exception ;
    boolean save(User user) throws IOException;

    }
