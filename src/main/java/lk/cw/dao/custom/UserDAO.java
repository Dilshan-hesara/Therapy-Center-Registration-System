package lk.cw.dao.custom;

import lk.cw.dao.CrudDAO;
import lk.cw.dao.SuperDAO;
import lk.cw.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public String getPasswordByUserName(String userName) throws Exception ;
    boolean save(User user) throws IOException;

    String getRoleByUserName(String userName) throws Exception;
}
