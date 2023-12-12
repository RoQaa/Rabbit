package RabbitGame;

public interface Constant {
    String DB_URL="jdbc:oracle:thin:@localhost:1521:xe";
    String USERNAME="system";
    String PASSWORD="1141967";
    String SELECT_SCORES="select * from hr.Score ORDER BY score DESC";
    String INSERT_SCORE="insert into hr.SCORE(user_name,score) VALUES(?,?)";
    String CHECK_PLAYER="select * from hr.score where user_name=?";
    String UPDATE_SCORE="update HR.score SET score=? WHERE user_name=?";

}
