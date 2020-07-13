package ImageGenerator;

import lib.ICONSTANTS;

import java.awt.*;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;

public class Flower extends JPanel implements ICONSTANTS {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flower");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel flower = new Flower();
        frame.add(flower);
        frame.setVisible(true);
    }



    public Polygon generateTriangle(float angle){
        Polygon newPolygon = new Polygon();
        Polygon polygon = new Polygon();
        newPolygon.addPoint(CENTER_PETAL_X, CENTER_PETAL_Y);
        newPolygon.addPoint((int) (CENTER_PETAL_X + Math.cos(angle)* POLYGON_HEIGHT), (int) (CENTER_PETAL_Y - Math.sin(angle)* POLYGON_HEIGHT));
        newPolygon.addPoint((int)(CENTER_PETAL_X + Math.cos(angle/2)* POLYGON_HEIGHT),(int)(CENTER_PETAL_Y - Math.sin(angle)* POLYGON_HEIGHT));
        polygon.addPoint(CENTER_PETAL_X, CENTER_PETAL_Y);
        polygon.addPoint((int)(CENTER_PETAL_X + Math.cos(angle/2)* POLYGON_HEIGHT),(int)(CENTER_PETAL_Y - Math.sin(angle)* POLYGON_HEIGHT));
        polygon.addPoint((int)(CENTER_PETAL_X),(int)(CENTER_PETAL_Y - Math.sin(angle)* POLYGON_HEIGHT));
        return newPolygon;
    }

    public Vector<Polygon> generatePetal(){
        Vector<Polygon> polygons = new Vector<>();

        Random random = new Random();
        int x = CENTER_PETAL_X;
        int y = CENTER_PETAL_Y;
        int addToX = POLYGON_WIDTH;
        int addToY = POLYGON_WIDTH;

        int moveLocationY = getNewLocationY(y,addToY);
        int angle = 0;

        while(angle <= TWO_PI_RADIAN){
            for(int j=0; j<LINES_PER_PETAL; j++){
                for(int i=0; i<LINE_IN_PETAL ;i++){
                    polygons.add(this.generateReversedTriangle(x,y,angle));
                    polygons.add(this.generateTriangle(x, getNewLocationY(y,addToY),angle));

                    if(x > CENTER_PETAL_X + MAX_PETAL_WIDTH || x < CENTER_PETAL_X - MAX_PETAL_WIDTH){
                        addToX = -addToX;
                    }
                    if(y < CENTER_PETAL_Y - MAX_PETAL_HEIGHT || y > CENTER_PETAL_Y + MAX_PETAL_HEIGHT){
                        addToY = -addToY;
                    }
                    x = getNewLocationX(x,addToX);
                    y = getNewLocationY(y,addToY);
                }
                x = CENTER_PETAL_X;
                y = moveLocationY;
                moveLocationY = getNewLocationY(y, addToY);
            }
            x = CENTER_PETAL_X;
            y = CENTER_PETAL_Y;
            moveLocationY = getNewLocationY(y,addToY);
            angle += TWO_PI_RADIAN/PETAL_QUANTITY;
        }
        return polygons;
    }

    public Vector<Polygon> generateCenter(){
        Vector<Polygon> polygons = new Vector<>();
        for(int angle = 0; angle<=TWO_PI_RADIAN; angle+=TWO_PI_RADIAN/CENTER_QUANTITY){
            polygons.add(generateDiamond(CENTER_PETAL_X,CENTER_PETAL_Y,angle));
        }
        return polygons;
    }

    public Polygon generateDiamond(int x, int y, int radian){
        Polygon polygon = new Polygon();
        polygon.addPoint(rotateX(x,y,radian),rotateY(x,y,radian));
        polygon.addPoint(rotateX(x+CENTER_WIDTH,y-CENTER_HEIGHT,radian),rotateY(x+CENTER_WIDTH,y-CENTER_HEIGHT,radian));
        polygon.addPoint(rotateX(x,y-CENTER_HEIGHT*2,radian),rotateY(x,y-CENTER_HEIGHT*2,radian));
        polygon.addPoint(rotateX(x-CENTER_WIDTH,y-CENTER_HEIGHT,radian),rotateY(x-CENTER_WIDTH,y-CENTER_HEIGHT,radian));
        return polygon;
    }

    public Polygon generateTriangle(int x, int y, int angle){
        Polygon polygon = new Polygon();
        polygon.addPoint(rotateX(x , y - POLYGON_HEIGHT, angle), rotateY(x ,y - POLYGON_HEIGHT,angle));
        polygon.addPoint(rotateX(x + POLYGON_WIDTH,y,angle),rotateY(x + POLYGON_WIDTH,y,angle));
        polygon.addPoint(rotateX(x - POLYGON_WIDTH, y, angle), rotateY(x - POLYGON_WIDTH,y,angle));
        return polygon;
    }

    public Polygon generateReversedTriangle(int x, int y, int angle){
        Polygon polygon = new Polygon();
        polygon.addPoint(rotateX(x,y,angle),rotateY(x,y,angle));
        polygon.addPoint(rotateX(x + POLYGON_WIDTH, y - POLYGON_HEIGHT, angle), rotateY(x + POLYGON_WIDTH,y - POLYGON_HEIGHT,angle));
        polygon.addPoint(rotateX(x - POLYGON_WIDTH, y - POLYGON_HEIGHT, angle), rotateY(x - POLYGON_WIDTH,y - POLYGON_HEIGHT,angle));
        return polygon;
    }

    public int rotateX(int x, int y, int radian){
        return (int) ((x - CENTER_PETAL_X) * Math.cos(Math.toRadians(radian)) - (y - CENTER_PETAL_Y) * Math.sin(Math.toRadians(radian)) + CENTER_PETAL_X);
    }

    public int rotateY(int x, int y, int radian){
        return  (int)((x - CENTER_PETAL_X) * Math.sin(Math.toRadians(radian)) + (y - CENTER_PETAL_Y) * Math.cos(Math.toRadians(radian)) + CENTER_PETAL_Y);
    }

    public int getNewLocationX(int x, int width){
        return x + width;
    }

    public int getNewLocationY(int y, int height){
        return y - height;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // paint background

        Graphics2D g2 = (Graphics2D) g;
        Random random = new Random();
        Vector<Polygon> polygons = this.generatePetal();
        for(Polygon polygon : polygons){
            //g2.setColor(new Color(0,0,0));
            g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g2.fillPolygon(polygon);
        }

        polygons = this.generateCenter();
        for(Polygon polygon : polygons){
            //g2.setColor(new Color(0,0,0));
            g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g2.fillPolygon(polygon);
        }
    }


}
