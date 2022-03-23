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

    public static BufferedImage[] playerLeft = new BufferedImage[48];
    public static BufferedImage[] playerRight = new BufferedImage[48];

    public static BufferedImage[] playerLeftShield = new BufferedImage[48];
    public static BufferedImage[] playerRightShield = new BufferedImage[48];

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

    public static BufferedImage[] zaWalfo = new BufferedImage[2];
    public static BufferedImage[] wolf = new BufferedImage[2];

    /* public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage mountain;
    public static BufferedImage townGrass;
    public static BufferedImage townGrassDestroyed;
    public static BufferedImage townSoil;
    public static BufferedImage water;
    public static BufferedImage rockUp;
    public static BufferedImage rockDown;
    public static BufferedImage rockLeft;
    public static BufferedImage rockRight;
    public static BufferedImage tree;
*/

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init() {
        /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        //SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        SpriteSheet player = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/blue.png"));
        SpriteSheet playerShield = new SpriteSheet(ImageLoader.LoadImage("/textures/characters/blue_shield.png"));
        maps[0] = ImageLoader.LoadImage("/textures/map/firstmap2.png");
        maps[1] = ImageLoader.LoadImage("/textures/map/secondmap2.png");

        for(int i=0;i<48;++i)
        {
            playerRight[i] = player.crop(i,0,96,96);
            playerLeft[i] = getFlippedImage(playerRight[i]);
            playerRightShield[i] = player.crop(i,0,96,96);
            playerLeftShield[i] = getFlippedImage(playerRightShield[i]);
        }

        /// Se obtin subimaginile corespunzatoare elementelor necesare.
        /*grass = sheet.crop(0, 0);
        soil = sheet.crop(1, 0);
        water = sheet.crop(2, 0);
        mountain = getFlippedImage(water);
        townGrass = sheet.crop(0, 1);
        townGrassDestroyed = sheet.crop(1, 1);
        townSoil = sheet.crop(2, 1);
        tree = sheet.crop(3, 1);
        rockUp = sheet.crop(2, 2);
        rockDown = sheet.crop(3, 2);
        rockLeft = sheet.crop(0, 3);
        rockRight = sheet.crop(1, 3);
    */}

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
