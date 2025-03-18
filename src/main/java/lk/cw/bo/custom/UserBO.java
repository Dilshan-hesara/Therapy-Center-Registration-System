package lk.cw.bo.custom;

import lk.cw.bo.SuperBO;
import lk.cw.dto.UserDTO;

import java.io.IOException;

public interface UserBO extends SuperBO {
    public boolean save(UserDTO userDTO) throws IOException;
    public String getPasswordByUserName(String userName) throws Exception ;
    public String getRoleByUserName(String userName) throws Exception;
}
