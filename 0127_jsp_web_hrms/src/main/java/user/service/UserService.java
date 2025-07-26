package user.service;

import java.sql.Connection;

import user.dao.UserDAO;
import user.model.UserDTO;

public class UserService {
	private UserDAO userDAO = new UserDAO();

    public UserDTO login(Connection conn, String user_id, String password) throws Exception {
        return userDAO.login(conn, user_id, password);
    }
}
