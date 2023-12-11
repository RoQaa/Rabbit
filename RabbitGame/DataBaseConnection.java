package RabbitGame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnection {
    private Connection connection;
    private PreparedStatement selectScore;

    DataBaseConnection() throws SQLException {
        connection= DriverManager.getConnection(Constant.DB_URL,Constant.USERNAME,Constant.PASSWORD);
        selectScore=connection.prepareStatement(Constant.SELECT_SCORES); //get Scores


    }
    public List <Score> getAllScore(){
        List<Score>res=new ArrayList<>();
        try {

        ResultSet resultSet= selectScore.executeQuery();

        while(resultSet.next()){
            Score sc=new Score();

            sc.setScore( resultSet.getInt("score"));
            sc.setUser_name(resultSet.getString("user_name"));

            res.add(sc);

            }

        } catch (SQLException e) {
            System.err.println("DB error: "+e.getMessage());
        }

        return res;
    }

    public void Close() throws SQLException {
        connection.close();
        selectScore.close();
    }

}