package BlueRidingHood.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animator {

    private int speed;
    private int frames;
    private int index= 0;
    private int count= 0;

    private BufferedImage[] images; //contine toate imaginile unei animatii
    private BufferedImage currentImg; //tine evidenta imaginii la care facem referire

    public Animator(int speed,BufferedImage... args){
        this.speed=speed;
        images=new BufferedImage[args.length];
        for(int i=0;i<args.length;i++){
            images[i]=args[i];
        }
        frames=args.length;
    }

    public void runAnimation(){
        index++;
        if(index>speed){
            index=0;
            nextFrame();
        }
    }


    private void nextFrame(){
        for(int i=0;i<frames;i++){
            if(count==i)
                currentImg=images[i];
        }
        count++;

        if(count>frames)
            count=0;
    }

    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY){
        g.drawImage(currentImg,x,y,scaleX,scaleY,null);
    }
}
