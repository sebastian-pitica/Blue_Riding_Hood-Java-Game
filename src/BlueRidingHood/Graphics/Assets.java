package BlueRidingHood.Graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets {

    public static final BufferedImage[] maps = new BufferedImage[2];

    public static final BufferedImage[] playerRightStand = new BufferedImage[4];
    public static final BufferedImage[] playerRightRun = new BufferedImage[6];
    public static final BufferedImage[] playerRightRetractSword = new BufferedImage[9];
    public static final BufferedImage[] playerRightDrawSword = new BufferedImage[9];
    public static final BufferedImage[] playerRightAttack = new BufferedImage[17];

    public static final BufferedImage[] playerRightShieldStand = new BufferedImage[4];
    public static final BufferedImage[] playerRightShieldRun = new BufferedImage[6];
    public static final BufferedImage[] playerRightShieldRetractSword = new BufferedImage[9];
    public static final BufferedImage[] playerRightShieldDrawSword = new BufferedImage[9];
    public static final BufferedImage[] playerRightShieldAttack = new BufferedImage[17];

    public static final BufferedImage[] playerLeftStand = new BufferedImage[4];
    public static final BufferedImage[] playerLeftRun = new BufferedImage[6];
    public static final BufferedImage[] playerLeftRetractSword = new BufferedImage[9];
    public static final BufferedImage[] playerLeftDrawSword = new BufferedImage[9];
    public static final BufferedImage[] playerLeftAttack = new BufferedImage[17];

    public static final BufferedImage[] playerLeftShieldStand = new BufferedImage[4];
    public static final BufferedImage[] playerLeftShieldRun = new BufferedImage[6];
    public static final BufferedImage[] playerLeftShieldRetractSword = new BufferedImage[9];
    public static final BufferedImage[] playerLeftShieldDrawSword = new BufferedImage[9];
    public static final BufferedImage[] playerLeftShieldAttack = new BufferedImage[17];

    public static final BufferedImage[] bear1Left = new BufferedImage[3];
    public static final BufferedImage[] bear1Right = new BufferedImage[3];
    public static final BufferedImage[] bear1Up = new BufferedImage[3];
    public static final BufferedImage[] bear1Down = new BufferedImage[3];

    public static final BufferedImage[] bear2Left = new BufferedImage[3];
    public static final BufferedImage[] bear2Right = new BufferedImage[3];
    public static final BufferedImage[] bear2Up = new BufferedImage[3];
    public static final BufferedImage[] bear2Down = new BufferedImage[3];

    public static final BufferedImage[] fox2Left = new BufferedImage[2];
    public static final BufferedImage[] fox2Right = new BufferedImage[2];
    public static final BufferedImage[] fox2Up = new BufferedImage[2];
    public static final BufferedImage[] fox2Down = new BufferedImage[2];

    public static final BufferedImage[] fox1Left = new BufferedImage[2];
    public static final BufferedImage[] fox1Right = new BufferedImage[2];
    public static final BufferedImage[] fox1Up = new BufferedImage[2];
    public static final BufferedImage[] fox1Down = new BufferedImage[2];

    public static final BufferedImage[] zaWalfoLeft = new BufferedImage[3];
    public static final BufferedImage[] zaWalfoRight = new BufferedImage[3];
    public static final BufferedImage[] zaWalfoUp = new BufferedImage[4];
    public static final BufferedImage[] zaWalfoDown = new BufferedImage[4];

    public static final BufferedImage[] wolfLeft = new BufferedImage[3];
    public static final BufferedImage[] wolfRight = new BufferedImage[3];
    public static final BufferedImage[] wolfUp = new BufferedImage[4];
    public static final BufferedImage[] wolfDown = new BufferedImage[4];

     public static final BufferedImage[] coin = new BufferedImage[8];

    // TODO: butoane gui


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
        {//todo taiat player si lup
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

            i = getRef1(player, playerShield, i, playerRightDrawSword, playerRightShieldDrawSword, playerLeftDrawSword, playerLeftShieldDrawSword);

            i -= 4;

            i = getRef1(player, playerShield, i, playerRightRetractSword, playerRightShieldRetractSword, playerLeftRetractSword, playerLeftShieldRetractSword);

            for (int j = 0; j < 17; ++j, ++i) {
                playerRightAttack[j] = player.crop(i, 0, 48, 48);
                playerRightShieldAttack[j] = playerShield.crop(i, 0, 48, 48);

                playerLeftAttack[j] = getFlippedImage(playerRightAttack[j]);
                playerLeftShieldAttack[j] = getFlippedImage(playerRightShieldAttack[j]);
            }

        }

        //zawalfo&wolf sprites load
        {
            i=0;
            i = getRef4(zaWalfo, wolf, i, zaWalfoDown, wolfDown, zaWalfoLeft, wolfLeft);
            ++i;

            i = getRef4(zaWalfo, wolf, i, zaWalfoRight, wolfRight, zaWalfoUp, wolfUp);

        }

        //fox1&2 sprites load
        {
            i=0;
            i = getRef2(fox1, fox2, i, fox1Down, fox2Down,fox1Left, fox2Left);
            ++i;

            i = getRef2(fox1, fox2, i, fox1Up, fox2Up, fox1Right, fox2Right);

        }

        //bear1&2 sprites load
        {
            i=0;
            i = getRef3(bear1, bear2, i, bear1Down, bear2Down, bear1Left, bear2Left);
            ++i;

            i = getRef3(bear1, bear2, i, bear1Up, bear2Up, bear1Right, bear2Right);

        }

        //coin sprites load
        {
            i=0;
            for(int j=0;j<8;++j)
            {
                coin[j] = coinSheet.crop(j,i,16,16);
            }
        }



    }

    private static int getRef4(SpriteSheet zaWalfo, SpriteSheet wolf, int i, BufferedImage[] zaWalfoDown, BufferedImage[] wolfDown, BufferedImage[] zaWalfoLeft, BufferedImage[] wolfLeft) {
        for(int j=0;j<3;++j)
        {
            zaWalfoDown[j] = zaWalfo.crop(j,i,32,32);
            wolfDown[j] = wolf.crop(j,i,32,32);
        }
        ++i;

        for(int j=0;j<3;++j)
        {
            zaWalfoLeft[j] = zaWalfo.crop(j,i,32,32);
            wolfLeft[j] = wolf.crop(j,i,32,32);
        }
        return i;
    }

    private static int getRef3(SpriteSheet bear1, SpriteSheet bear2, int i, BufferedImage[] bear1Down, BufferedImage[] bear2Down, BufferedImage[] bear1Left, BufferedImage[] bear2Left) {
        for(int j=0;j<3;++j)
        {
            bear1Down[j] = bear1.crop(j,i,64,48);
            bear2Down[j] = bear2.crop(j,i,64,48);
        }
        ++i;

        for(int j=0;j<3;++j)
        {
            bear1Left[j] = bear1.crop(j,i,64,48);
            bear2Left[j] = bear2.crop(j,i,64,48);
        }
        return i;
    }

    private static int getRef2(SpriteSheet fox1, SpriteSheet fox2, int i, BufferedImage[] fox1Down, BufferedImage[] fox2Down, BufferedImage[] fox1Left, BufferedImage[] fox2Left) {
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
        return i;
    }

    private static int getRef1(SpriteSheet player, SpriteSheet playerShield, int i, BufferedImage[] playerRightDrawSword, BufferedImage[] playerRightShieldDrawSword, BufferedImage[] playerLeftDrawSword, BufferedImage[] playerLeftShieldDrawSword) {
        for (int j = 0; j < 9; ++j, ++i) {
            playerRightDrawSword[j] = player.crop(i, 0, 48, 48);
            playerRightShieldDrawSword[j] = playerShield.crop(i, 0, 48, 48);

            playerLeftDrawSword[j] = getFlippedImage(playerRightDrawSword[j]);
            playerLeftShieldDrawSword[j] = getFlippedImage(playerRightShieldDrawSword[j]);
        }
        return i;
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
