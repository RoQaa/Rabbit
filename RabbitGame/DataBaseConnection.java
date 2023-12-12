package RabbitGame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnection {
    private Connection connection;
    private PreparedStatement selectScore;
    private PreparedStatement insertScore;
    private PreparedStatement checkPlayer;
    private PreparedStatement updateScore;


    DataBaseConnection() throws SQLException {
        connection= DriverManager.getConnection(Constant.DB_URL,Constant.USERNAME,Constant.PASSWORD);

        selectScore=connection.prepareStatement(Constant.SELECT_SCORES); //get Scores
        insertScore=connection.prepareStatement(Constant.INSERT_SCORE); //insert Score
        checkPlayer=connection.prepareStatement(Constant.CHECK_PLAYER); // check player
        updateScore=connection.prepareStatement(Constant.UPDATE_SCORE); // check player

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

    public void Update(String userName,int Score){
        try {
            updateScore.clearParameters();
            updateScore.setInt(1,Score);
            updateScore.setString(2,userName);
            updateScore.executeUpdate();



        } catch (SQLException e) {
            System.err.println("DB error: "+e.getMessage());
        }


    }
    public void Insert(String userName,int Score){
        try {
            insertScore.clearParameters();
            insertScore.setString(1,userName);
            insertScore.setInt(2,Score);
            insertScore.executeUpdate();



        } catch (SQLException e) {
            System.err.println("DB error: "+e.getMessage());
        }


    }
    public boolean checkPlayer(String playerName){
        try {
            checkPlayer.clearParameters();
            checkPlayer.setString(1,playerName);
            ResultSet resultSet= checkPlayer.executeQuery();
           if(resultSet.next()) {

               return true;
           }



        } catch (SQLException e) {
            System.err.println("DB check Player: "+e.getMessage());
        }
        return false;
    }

    public void Close() throws SQLException {
        connection.close();
        selectScore.close();
        checkPlayer.close();
        insertScore.close();
        updateScore.close();
    }

}
