
import com.sun.opengl.util.Animator;
import java.awt.BorderLayout;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;


public class Rabbit extends JFrame {
     private Animator Animation;
    Rabbit(){
        super("Rabbit");
        GLCanvas gl = new GLCanvas();
        RabbitGLEventListener r = new RabbitGLEventListener();
        Animation=new Animator(gl);
        
        
        
        
        //TODO do What u want Team Members(Farouk,Wafdy,manar,Boda)
        
        
        
        
        gl.addGLEventListener(r);
        getContentPane().add(gl,BorderLayout.CENTER);
        setSize(1500,900);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
 }
    
    public static void main(String []args){
        new Rabbit();
    }
    }

