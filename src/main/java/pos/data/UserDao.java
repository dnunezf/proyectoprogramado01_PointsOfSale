package pos.data;

import pos.logic.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao
{
    Database db;

    public UserDao(Database db) {
        this.db = db;  // Cambiado para usar 'this.db'
    }


    public User findUserByIdAndPassword(String id, String password) throws Exception {
        String query = "SELECT * FROM user WHERE id = ? AND password = ?";
        PreparedStatement statement = Database.instance().prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, password);
        ResultSet rs = Database.instance().executeQuery(statement);

        if (rs.next()) {
            return new User(rs.getString("id"), rs.getString("password"));
        }
        return null;
    }
}