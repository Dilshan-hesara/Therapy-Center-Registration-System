package lk.cw.bo.custom.impl;


import lk.cw.bo.custom.UserBO;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.UserDAO;
import lk.cw.dto.UserDTO;
import lk.cw.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean save(UserDTO userDTO) throws IOException {
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        return userDAO.save(new User(userDTO.getId(), userDTO.getUserName(), hashedPassword, userDTO.getRole()));
    }
}
