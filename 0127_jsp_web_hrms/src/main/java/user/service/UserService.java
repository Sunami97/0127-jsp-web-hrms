package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import user.dao.UserDAO;
import user.model.UserDTO;

public class UserService {
	private UserDAO userDAO = new UserDAO();

    public UserDTO login(Connection conn, String user_id, String password) throws Exception {
        return userDAO.login(conn, user_id, password);
    }
    
    public boolean isUserExist(Connection conn, String user_id) throws Exception {
        return userDAO.isUserExist(conn, user_id);
    }

    
    public void updateLoginStatus(Connection conn, String userId, String status) throws SQLException {
        userDAO.updateLoginStatus(conn, userId, status);
    }
} 
