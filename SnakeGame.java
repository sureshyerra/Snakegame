import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    JFrame jFrame;
    SnakeGame(){
        jFrame = new JFrame("SnakeGame");
        jFrame.setBounds(10,10,905,700);
        MyPanel panel = new MyPanel();
        panel.setBackground(Color.gray);
        jFrame.add(panel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
    public static void main(String[] args) {
        SnakeGame s= new SnakeGame();
    }
}
