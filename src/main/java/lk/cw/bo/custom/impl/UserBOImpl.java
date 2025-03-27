package lk.cw.bo.custom.impl;


import lk.cw.bo.custom.UserBO;
import lk.cw.config.FactoryConfiguration;
import lk.cw.dao.DAOFactory;
import lk.cw.dao.custom.UserDAO;
import lk.cw.dto.UserDTO;
import lk.cw.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean save(UserDTO userDTO) throws IOException {
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        return userDAO.save(new User(userDTO.getId(), userDTO.getUserName(), hashedPassword, userDTO.getRole()));
    }

    @Override
    public String getPasswordByUserName(String userName) throws Exception {
        return userDAO.getPasswordByUserName(userName);
    }

    @Override
    public String getRoleByUserName(String userName) throws Exception {
        return userDAO.getRoleByUserName(userName);
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        return userDAO.getNextId();
    }


    @Override
    public List<UserDTO> getAll() throws SQLException, IOException {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUserName());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());
            userDTOs.add(userDTO);

        }
        return userDTOs;
    }

    @Override
    public boolean update(UserDTO userDTO) throws IOException, SQLException {
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        return userDAO.update(new User(userDTO.getId(), userDTO.getUserName(), hashedPassword, userDTO.getRole()));
    }
}
