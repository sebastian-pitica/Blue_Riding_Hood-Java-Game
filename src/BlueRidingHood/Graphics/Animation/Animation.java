package BlueRidingHood.Graphics.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {
    //clasa preluata din proiectul SunnyLand, relizat de Paval Mihaela Irina (2019-2020), cu acord de utilizare

    private final int speed;
    private final int frames;
    private int index= 0;
    private int count= 0;

    private final BufferedImage[] images;
    private BufferedImage currentImg;

    public Animation(int speed, BufferedImage[] arg){
        this.speed=speed;

        images=new BufferedImage[arg.length];

        System.arraycopy(arg, 0, images, 0, arg.length);
        frames=arg.length;
    }

    public void runAnimation()
    {
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
