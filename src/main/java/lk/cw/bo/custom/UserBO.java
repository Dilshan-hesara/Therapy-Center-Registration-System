package lk.cw.bo.custom;

import lk.cw.bo.SuperBO;
import lk.cw.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    public boolean save(UserDTO userDTO) throws IOException;
    public String getPasswordByUserName(String userName) throws Exception ;
    public String getRoleByUserName(String userName) throws Exception;
    public String getNextId() throws SQLException, IOException ;
    public List<UserDTO> getAll() throws SQLException, IOException;
    public boolean update(UserDTO userDTO) throws IOException, SQLException;

}
