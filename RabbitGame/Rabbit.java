package RabbitGame;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;
import javax.media.opengl.GLCanvas;
import javax.swing.*;


public class Rabbit extends JFrame {
    private  static JTextField textField;


    Rabbit() throws SQLException {



        GLCanvas glcanvas;
        Animator animator;

        RabbitGLEventListener listener = new RabbitGLEventListener();
        DataBaseConnection db=new DataBaseConnection();
        textField=new JTextField("PlayerName");
        JOptionPane.showMessageDialog(null,textField);
        listener.setPlayerName(textField.getText());
        if(!db.checkPlayer(textField.getText())){
            db.Insert(listener.getPlayerName(),0);
        db.Close();
        }


        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);
        glcanvas.addMouseMotionListener(listener);
        listener.setGLCanvas(glcanvas);


        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(30);
        animator.add(glcanvas);
        animator.start();

        setTitle("Rabbit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
 }
    
    public static void main(String []args) throws SQLException {

        new Rabbit();

        //db.Insert("mohamed",10);
//        DataBaseConnection db = new DataBaseConnection();
//        List<Score> scoreList=db.getAllScore();
//
//        db.Close();
//        for(Score currentScore:scoreList){
//            System.out.println(currentScore.getUser_name());
//            System.out.println(currentScore.getScore());
        }
    }



