package BlueRidingHood.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.*;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets {

    /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage[] maps = new BufferedImage[2];

    public static BufferedImage[] playerLeftStand = new BufferedImage[4];
    public static BufferedImage[] playerLeftRun = new BufferedImage[6];
    public static BufferedImage[] playerLeftRetractSword = new BufferedImage[9];
    public static BufferedImage[] playerLeftDrawSword = new BufferedImage[9];
    public static BufferedImage[] playerLeftAttackSword = new BufferedImage[17];
    public static BufferedImage[] playerLeftAttackIce = new BufferedImage[8];

    public static BufferedImage[] playerLeftShieldStand = new BufferedImage[4];
    public static BufferedImage[] playerLeftShieldRun = new BufferedImage[6];
    public static BufferedImage[] playerLeftShieldRetractSword = new BufferedImage[9];
    public static BufferedImage[] playerLeftShieldDrawSword = new BufferedImage[9];
    public static BufferedImage[] playerLeftShieldAttackSword = new BufferedImage[17];
    public static BufferedImage[] playerLeftShieldAttackIce = new BufferedImage[8];

    public static BufferedImage[] playerRightStand = new BufferedImage[4];
    public static BufferedImage[] playerRightRun = new BufferedImage[6];
    public static BufferedImage[] playerRightRetractSword = new BufferedImage[9];
    public static BufferedImage[] playerRightDrawSword = new BufferedImage[9];
    public static BufferedImage[] playerRightAttackSword = new BufferedImage[17];
    public static BufferedImage[] playerRightAttackIce = new BufferedImage[8];

    public static BufferedImage[] playerRightShieldStand = new BufferedImage[4];
    public static BufferedImage[] playerRightShieldRun = new BufferedImage[6];
    public static BufferedImage[] playerRightShieldRetractSword = new BufferedImage[9];
    public static BufferedImage[] playerRightShieldDrawSword = new BufferedImage[9];
    public static BufferedImage[] playerRightShieldAttackSword = new BufferedImage[17];
    public static BufferedImage[] playerRightShieldAttackIce = new BufferedImage[8];

    public static BufferedImage[] bear1Left = new BufferedImage[3];
    public static BufferedImage[] bear1Right = new BufferedImage[3];
    public static BufferedImage[] bear1Up = new BufferedImage[3];
    public static BufferedImage[] bear1Down = new BufferedImage[3];

    public static BufferedImage[] bear2Left = new BufferedImage[3];
    public static BufferedImage[] bear2Right = new BufferedImage[3];
    public static BufferedImage[] bear2Up = new BufferedImage[3];
    public static BufferedImage[] bear2Down = new BufferedImage[3];

    public static BufferedImage[] fox2Left = new BufferedImage[2];
    public static BufferedImage[] fox2Right = new BufferedImage[2];
    public static BufferedImage[] fox2Up = new BufferedImage[2];
    public static BufferedImage[] fox2Down = new BufferedImage[2];

    public static BufferedImage[] fox1Left = new BufferedImage[2];
    public static BufferedImage[] fox1Right = new BufferedImage[2];
    public static BufferedImage[] fox1Up = new BufferedImage[2];
    public static BufferedImage[] fox1Down = new BufferedImage[2];

    public static BufferedImage[] zaWalfoLeft = new BufferedImage[7];
    public static BufferedImage[] zaWalfoRight = new BufferedImage[7];
    public static BufferedImage[] zaWalfoUp = new BufferedImage[4];
    public static BufferedImage[] zaWalfoDown = new BufferedImage[4];

    public static BufferedImage[] wolfLeft = new BufferedImage[7];
    public static BufferedImage[] wolfRight = new BufferedImage[7];
    public static BufferedImage[] wolfUp = new BufferedImage[4];
    public static BufferedImage[] wolfDown = new BufferedImage[4];

    
    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init() {
        /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        //SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));

        SpriteSheet player = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/blue2.png"));
        SpriteSheet playerShield = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/blue_shield2.png"));
        SpriteSheet bear1 = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/urs1.png"));
        SpriteSheet bear2 = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/urs2.png"));
        SpriteSheet fox1 = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/vulpe1.png"));
        SpriteSheet fox2 = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/vulpe2.png"));
        SpriteSheet zaWalfo = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/zawalfo.png"));
        SpriteSheet wolf = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/garda.png"));

        // TODO: 23.03.2022 butoane 
        // TODO: 23.03.2022 atac foc gheata 
        
        maps[0] = ImageLoader.LoadImage("/textures/map/firstmap2.png");
        maps[1] = ImageLoader.LoadImage("/textures/map/secondmap2.png");

        int i=0;
        //player
        {
            for (int j = 0; j < 4; ++j, ++i) {
                playerLeftStand[j] = player.crop(i, 0, 48, 48);
                playerLeftShieldStand[j] = playerShield.crop(i, 0, 48, 48);
                playerRightStand[j] = getFlippedImage(playerLeftStand[j]);
                playerRightShieldStand[j] = getFlippedImage(playerLeftShieldStand[j]);
            }

            for (int j = 0; j < 6; ++j, ++i) {
                playerLeftRun[j] = player.crop(i, 0, 48, 48);
                playerLeftShieldRun[j] = playerShield.crop(i, 0, 48, 48);
                playerRightRun[j] = getFlippedImage(playerLeftRun[j]);
                playerRightShieldRun[j] = getFlippedImage(playerLeftShieldRun[j]);
            }

            for (int j = 0; j < 9; ++j, ++i) {
                playerLeftDrawSword[j] = player.crop(i, 0, 48, 48);
                playerLeftShieldDrawSword[j] = playerShield.crop(i, 0, 48, 48);

                playerRightDrawSword[j] = getFlippedImage(playerLeftDrawSword[j]);
                playerRightShieldDrawSword[j] = getFlippedImage(playerLeftShieldDrawSword[j]);
            }

            i -= 4;

            for (int j = 0; j < 9; ++j, ++i) {
                playerLeftRetractSword[j] = player.crop(i, 0, 48, 48);
                playerLeftShieldRetractSword[j] = playerShield.crop(i, 0, 48, 48);

                playerRightRetractSword[j] = getFlippedImage(playerLeftRetractSword[j]);
                playerRightShieldRetractSword[j] = getFlippedImage(playerLeftShieldRetractSword[j]);
            }

            for (int j = 0; j < 17; ++j, ++i) {
                playerLeftAttackSword[j] = player.crop(i, 0, 48, 48);
                playerLeftShieldAttackSword[j] = playerShield.crop(i, 0, 48, 48);

                playerRightAttackSword[j] = getFlippedImage(playerLeftAttackSword[j]);
                playerRightShieldAttackSword[j] = getFlippedImage(playerLeftShieldAttackSword[j]);
            }

            for (int j = 0; j < 8; ++j, ++i) {
                playerLeftAttackIce[j] = player.crop(i, 0, 48, 48);
                playerLeftShieldAttackIce[j] = playerShield.crop(i, 0, 48, 48);

                playerRightAttackIce[j] = getFlippedImage(playerLeftAttackIce[j]);
                playerRightShieldAttackIce[j] = getFlippedImage(playerLeftShieldAttackIce[j]);
            }
        }
        //zawalfo&wolf
        {
            for(int j=0;j<3;++j)
            {
                zaWalfoDown[j] = zaWalfo.crop(j,i,32,32);
                wolfDown[j] = wolf.crop(j,i,32,32);
            }
            ++i;

            for(int j=0;j<7;++j)
            {
                zaWalfoLeft[j] = zaWalfo.crop(j,i,32,32);
                wolfLeft[j] = wolf.crop(j,i,32,32);
            }
            ++i;

            for(int j=0;j<7;++j)
            {
                zaWalfoRight[j] = zaWalfo.crop(j,i,32,32);
                wolfRight[j] = wolf.crop(j,i,32,32);
            }
            ++i;

            for(int j=0;j<3;++j)
            {
                zaWalfoUp[j] = zaWalfo.crop(j,i,32,32);
                wolfUp[j] = wolf.crop(j,i,32,32);
            }

        }
        //fox1&2
        {
            for(int j=0;j<2;++j)
            {
                fox1Down[j] = fox1.crop(j,i,48,32);
                fox2Down[j] = fox2.crop(j,i,48,32);
            }
            ++i;

            for(int j=0;j<2;++j)
            {
                fox1Left[j] = fox1.crop(j,i,48,32);
                fox2Left[j] = fox2.crop(j,i,48,32);
            }
            ++i;

            for(int j=0;j<2;++j)
            {
                fox1Right[j] = fox1.crop(j,i,48,32);
                fox2Right[j] = fox2.crop(j,i,48,32);
            }
            ++i;

            for(int j=0;j<2;++j)
            {
                fox1Up[j] = fox1.crop(j,i,48,32);
                fox2Up[j] = fox2.crop(j,i,48,32);
            }

        }
        //bear1&2
        {
            for(int j=0;j<2;++j)
            {
                bear1Down[j] = bear1.crop(j,i,64,48);
                bear2Down[j] = bear2.crop(j,i,64,48);
            }
            ++i;

            for(int j=0;j<2;++j)
            {
                bear1Left[j] = bear1.crop(j,i,64,48);
                bear2Left[j] = bear2.crop(j,i,64,48);
            }
            ++i;

            for(int j=0;j<2;++j)
            {
                bear1Right[j] = bear1.crop(j,i,64,48);
                bear2Right[j] = bear2.crop(j,i,64,48);
            }
            ++i;

            for(int j=0;j<2;++j)
            {
                bear1Up[j] = bear1.crop(j,i,64,48);
                bear2Up[j] = bear2.crop(j,i,64,48);
            }

        }

    }

    public static BufferedImage getFlippedImage(BufferedImage image) {
        BufferedImage flipped = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                image.getType());
        AffineTransform tran = AffineTransform.getTranslateInstance(image.getWidth(), 0);
        AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
        tran.concatenate(flip);

        Graphics2D g = flipped.createGraphics();
        g.setTransform(tran);
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return flipped;
    }
}
