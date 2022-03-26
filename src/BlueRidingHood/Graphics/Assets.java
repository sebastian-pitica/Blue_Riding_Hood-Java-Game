package BlueRidingHood.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.*;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets {

    public static BufferedImage[] maps = new BufferedImage[2];

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

    public static BufferedImage[] coin = new BufferedImage[8];

    // TODO: butoane gui
    // TODO: sprite atacuri
    //todo sprite monede

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
        SpriteSheet coinSheet = new SpriteSheet(ImageLoader.LoadImage("/textures/map/moneda.png"));

        maps[0] = ImageLoader.LoadImage("/textures/map/firstmap2.png");
        maps[1] = ImageLoader.LoadImage("/textures/map/secondmap2.png");

        int i=0;

        //player sprites load
        {
            for (int j = 0; j < 4; ++j, ++i) {
                playerRightStand[j] = player.crop(i, 0, 48, 48);
                playerRightShieldStand[j] = playerShield.crop(i, 0, 48, 48);
                playerLeftStand[j] = getFlippedImage(playerRightStand[j]);
                playerLeftShieldStand[j] = getFlippedImage(playerRightShieldStand[j]);
            }

            for (int j = 0; j < 6; ++j, ++i) {
                playerRightRun[j] = player.crop(i, 0, 48, 48);
                playerRightShieldRun[j] = playerShield.crop(i, 0, 48, 48);
                playerLeftRun[j] = getFlippedImage(playerRightRun[j]);
                playerLeftShieldRun[j] = getFlippedImage(playerRightShieldRun[j]);
            }

            for (int j = 0; j < 9; ++j, ++i) {
                playerRightDrawSword[j] = player.crop(i, 0, 48, 48);
                playerRightShieldDrawSword[j] = playerShield.crop(i, 0, 48, 48);

                playerLeftDrawSword[j] = getFlippedImage(playerRightDrawSword[j]);
                playerLeftShieldDrawSword[j] = getFlippedImage(playerRightShieldDrawSword[j]);
            }

            i -= 4;

            for (int j = 0; j < 9; ++j, ++i) {
                playerRightRetractSword[j] = player.crop(i, 0, 48, 48);
                playerRightShieldRetractSword[j] = playerShield.crop(i, 0, 48, 48);

                playerLeftRetractSword[j] = getFlippedImage(playerRightRetractSword[j]);
                playerLeftShieldRetractSword[j] = getFlippedImage(playerRightShieldRetractSword[j]);
            }

            for (int j = 0; j < 17; ++j, ++i) {
                playerRightAttackSword[j] = player.crop(i, 0, 48, 48);
                playerRightShieldAttackSword[j] = playerShield.crop(i, 0, 48, 48);

                playerLeftAttackSword[j] = getFlippedImage(playerRightAttackSword[j]);
                playerLeftShieldAttackSword[j] = getFlippedImage(playerRightShieldAttackSword[j]);
            }

            for (int j = 0; j < 8; ++j, ++i) {
                playerRightAttackIce[j] = player.crop(i, 0, 48, 48);
                playerRightShieldAttackIce[j] = playerShield.crop(i, 0, 48, 48);

                playerLeftAttackIce[j] = getFlippedImage(playerRightAttackIce[j]);
                playerLeftShieldAttackIce[j] = getFlippedImage(playerRightShieldAttackIce[j]);
            }
        }

        //zawalfo&wolf sprites load
        {
            i=0;
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

        //fox1&2 sprites load
        {
            i=0;
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

        //bear1&2 sprites load
        {
            i=0;
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

        i=0;
        {
            for(int j=0;j<8;++j)
            {
                coin[j] = coinSheet.crop(j,i,16,16);
            }
        }

    }

    public static BufferedImage getFlippedImage(BufferedImage image) {
        //functie ce face flip vertical al unei imagini
        //sursa: https://tousu.in/qa/?qa=1154932/
        BufferedImage flipped = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
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
