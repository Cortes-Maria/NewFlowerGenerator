package ImageGenerator;

import lib.ICONSTANTS;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;

public class ImageGenerator extends JPanel implements ICONSTANTS {
    private JButton nextGeneration;
    private Controller controller;

    public ImageGenerator(){
        this.setBackground(Color.BLACK);
        controller = new Controller();
        nextGeneration = new JButton("Next Generation");
        this.add(nextGeneration);

        nextGeneration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextGenerationButton();
                repaint();
            }
        });
    }

    public void nextGenerationButton(){
        System.out.println("Population control: Petal");
        controller.printMainColor(controller.getPetalDistributionTable().getDistribution().getMainColor());
        controller.getPetalGenetic().PopulationControl();

        System.out.println("Population control: Center");
        controller.printMainColor(controller.getCenterDistributionTable().getDistribution().getMainColor());
        controller.getCenterGenetic().PopulationControl();

    }


    public Vector<Polygon> generatePetal(int radian){
        Vector<Polygon> polygons = new Vector<>();

        Random random = new Random();
        int x = CENTER_PETAL_X;
        int y = CENTER_PETAL_Y;
        int addToX = POLYGON_WIDTH;
        int addToY = POLYGON_WIDTH;

        int moveLocationY = getNewLocationY(y,addToY);

        for(int j=0; j<LINES_PER_PETAL; j++){
            for(int i=0; i<LINE_IN_PETAL ;i++){
                polygons.add(this.generateReversedTriangle(x,y,radian));
                polygons.add(this.generateTriangle(x, getNewLocationY(y,addToY),radian));

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

    public Color getControllerColor(){

        return null;
    }

    public Vector<Color> generateControllerColors(){
        Vector<Color> colors = new Vector<>();

        while (colors.size() <= POLYGON_PER_PETAL){
            for(Color color : controller.getPetalGenetic().getFitPopulationDistribution().keySet()){
                int i = (int) (controller.getPetalGenetic().getFitPopulationDistribution().get(color).get(PERCENTAGE_INDEX)*CENTER_QUANTITY);
                //System.out.println(controller.getPetalGenetic().getFitPopulationDistribution().get(color).get(PERCENTAGE_INDEX));
                while(i >= 0){
                    colors.add(color);
                    i--;
                }
            }
        }
        System.out.println();
        return colors;
    }

    public Vector<Color> generateControllerCenter(){
        Vector<Color> colors = new Vector<>();
        while (colors.size() <= CENTER_QUANTITY){
            for(Color color : controller.getCenterGenetic().getFitPopulationDistribution().keySet()){
                int i = (int) (controller.getCenterGenetic().getFitPopulationDistribution().get(color).get(PERCENTAGE_INDEX)*CENTER_QUANTITY);
                //System.out.println(controller.getCenterGenetic().getFitPopulationDistribution().get(color).get(PERCENTAGE_INDEX));
                while(i >= 0){
                    colors.add(color);
                    i--;
                }
            }
        }
        return colors;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // paint background
        Graphics2D g2 = (Graphics2D) g;
        Random random = new Random();
        Vector<Color> PetalColors = this.generateControllerColors();
        int radian = 0;
        while(radian<=360){
            Vector<Polygon> polygons = this.generatePetal(radian);
            int i = 0;
            for(Polygon polygon : polygons){
                g2.setColor(PetalColors.elementAt(i));
                g2.fillPolygon(polygon);
                i++;
            }
            radian += TWO_PI_RADIAN/PETAL_QUANTITY;
        }
        radian = 0;
        Vector<Color> CenterColors = this.generateControllerCenter();
        int i = 0;
        while(radian<360){
            g2.setColor(CenterColors.elementAt(i));
            g2.fillPolygon(generateDiamond(CENTER_PETAL_X,CENTER_PETAL_Y,radian));
            i++;
            radian += TWO_PI_RADIAN/CENTER_QUANTITY;
        }
    }

    public JButton getNextGeneration() {
        return nextGeneration;
    }

    public void setNextGeneration(JButton nextGeneration) {
        this.nextGeneration = nextGeneration;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}