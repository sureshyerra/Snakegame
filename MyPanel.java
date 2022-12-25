import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    ImageIcon right = new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon left = new ImageIcon(getClass().getResource("leftmouth.png"));
    ImageIcon up = new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon down = new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));
    int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    int score = 0;
    Random random = new Random();
    boolean gameover = false;
    int foodx = 150;
    int foody = 150;
    boolean isright = true;
    boolean isleft = false;
    boolean isup = false;
    boolean isdown = false;
    int[]snakex = new int[750];
    int[]snakey = new int[750];
    int move = 0;
    int lengthOfSnake = 3;;
    Timer time;
    MyPanel(){
        addKeyListener(this);
        setFocusable(true);
        time = new Timer(50,this);
        time.start();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);
        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);
        if(move == 0){
            snakex[0] = 100;
            snakex[1] = 75;
            snakex[2] = 50;

            snakey[0] = 100;
            snakey[1] = 100;
            snakey[2] = 100;
        }
        if(isright){
            right.paintIcon(this,g,snakex[0],snakey[0]);
        }
        if(isleft){
            left.paintIcon(this,g,snakex[0],snakey[0]);

        }
        if(isup){
            up.paintIcon(this,g,snakex[0],snakey[0]);
        }
        if(isdown){
            down.paintIcon(this,g,snakex[0],snakey[0]);
        }
        for(int i = 1;i<lengthOfSnake;i++){
            snakeimage.paintIcon(this,g,snakex[i],snakey[i]);
        }
        enemy.paintIcon(this,g,foodx,foody);
        if(gameover){
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("GAME IS OVER",300,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
            g.drawString("press space to restart",300,350);
        }
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,15));
        g.drawString("score "+score,725,30);
        g.drawString("lengthofsnake "+lengthOfSnake,725,50);
        g.dispose();





    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && gameover){
            restart();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && (!isleft)){
            isdown = false;
            isup = false;
            isright = true;
            isleft= false;
            move++;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && (!isright)){
            isdown = false;
            isup = false;
            isright = false;
            isleft= true;
            move++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && (!isdown)){
            isdown = false;
            isup = true;
            isright = false;
            isleft= false;
            move++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && (!isup)){
            isdown = true;
            isup = false;
            isright = false;
            isleft= false;
            move++;
        }




    }

    private void restart() {
        gameover = false;
        move = 0;
        lengthOfSnake = 3;
        isup = false;
        isright = true;
        isdown = false;
        isleft = false;
        time.start();
        newenemy();
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
     for(int i = lengthOfSnake-1;i>0;i--){
         snakex[i] = snakex[i-1];
         snakey[i] = snakey[i-1];
     }
     if(isright){
         snakex[0]= snakex[0] +25;
     }
     if(isleft){
         snakex[0] = snakex[0]-25;

     }
     if(isup){
         snakey[0] = snakey[0]-25;
     }
     if (isdown){
         snakey[0] = snakey[0]+25;
     }
     if(snakex[0]>850)snakex[0] = 25;
     if(snakex[0]<25)snakex[0] = 850;
     if(snakey[0] > 625)snakey[0] = 75;
     if(snakey[0]<75)snakey[0] = 625;
     collisionwithenemy();
     collisionwithebody();
     repaint();
    }

    private void collisionwithebody() {
        for(int i = lengthOfSnake-1;i>0;i--){
            if(snakex[i] == snakex[0] && snakey[i] == snakey[0]){
                time.stop();
                gameover = true;
            }
        }
    }

    private void collisionwithenemy() {
        if(snakex[0] == foodx && snakey[0] == foody) {
            newenemy();
            score++;
            lengthOfSnake++;
            snakex[lengthOfSnake - 1] = snakex[lengthOfSnake - 2];
            snakey[lengthOfSnake - 1] = snakey[lengthOfSnake - 2];
        }
    }

    private void newenemy() {
        foodx = xpos[random.nextInt(xpos.length-1)];
        foody = ypos[random.nextInt(ypos.length-1)];
        for(int i = lengthOfSnake-1;i>=0;i--){
            if(snakex[i] == foodx && snakey[i] == foody){
                newenemy();
            }
        }
    }
}
