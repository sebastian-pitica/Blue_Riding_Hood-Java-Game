package BlueRidingHood.Game;

import BlueRidingHood.Entities.Player;
import BlueRidingHood.GameWindow.GameWindow;
import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Animation.AnimationHandler;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.InputManager.KeyboardInputManager;
import BlueRidingHood.InputManager.PlayerInputHandler;
import BlueRidingHood.Map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable {
    private GameWindow gameWindow;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean runState;   /*!< Flag ce starea firului de executie.*/
    private Thread gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private KeyboardInputManager keyboardInputManager;
    private JFrame gameWindowFrame;
    private PlayerInputHandler inputHandler;
    private AnimationHandler animationHandler;

    private Player player;
    private Map currentMap;
    private Image currentMapImage;
    private Animation currentPlayerAnimation;

    private boolean displayRect = false;
    //flag pentru check afisare rect informativ
    private boolean grid = false;

    final private int corectiePlayerCoord = 12;
    private static Game game = null;

    public static Game provideGame()
    {
        if(game == null)
        {
            game = new Game();
        }
        return game;
    }

    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    protected Game() {
        /// Obiectul GameWindow este creat insa fereastra nu este construita
        /// Acest lucru va fi realizat in metoda init() prin apelul
        /// functiei BuildGameWindow();
        gameWindow = new GameWindow();
        /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
    }//todo dont touch

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame() {
        gameWindow = new GameWindow();
        /// Este construita fereastra grafica.
        gameWindow.BuildGameWindow();
        /// Se incarca toate elementele grafice (dale)

        keyboardInputManager = KeyboardInputManager.provideKeyboardInputManager();
        gameWindowFrame = gameWindow.getWindowFrame();
        //preiau referinta la fereastra si keyboard input manager
        gameWindowFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent e) {StopGame();System.exit(0);}});
        //setez ca la inchiderea ferestrei sa se apeleze functia de StopGame() si System.exit(0)

        Assets.Init();

        currentMap = Map.getCurrentMap();
        player = new Player(0, currentMap.startY()*Tile.TILE_HEIGHT,0, currentMap.startY(),4,4);
        currentPlayerAnimation = player.rightStand; //animatia default
        animationHandler = new AnimationHandler(player);
        inputHandler = new PlayerInputHandler(player);
        //todo add gui & others for start

    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run() //todo dont touch
    {
        /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

        /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
        /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

        /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState) {
            /// Se obtine timpul curent
            curentTime = System.nanoTime();
            /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if ((curentTime - oldTime) > timeFrame) {
                /// Actualizeaza pozitiile elementelor
                Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }

    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame()//todo dont touch
    {
        System.out.println("Game started!");
        if (!runState) {
            /// Se actualizeaza flagul de stare a threadului
            runState = true;
            /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
            /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
            /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        } else {
            /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame() {
        System.out.println("Game stoped!");
        if (runState) {
            /// Actualizare stare thread
            runState = false;
            /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try {
                /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
            } catch (InterruptedException ex) {
                /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        } else {
            /// Thread-ul este oprit deja.
            return;
        }

        //todo
        //System.exit(0);

    }//todo check utility

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {
        if(gameWindow !=null)
        //daca exista referinta la fereastra
        {
            //todo vezi daca poti scapa
            displayRect = keyboardInputManager.rectangular; //verific daca e nevoie sa afisez rect
            grid = keyboardInputManager.grid;

            inputHandler.handler(); //prelucrez inputul de la jucator

            currentMap = Map.getCurrentMap();
            currentMapImage = Assets.maps[currentMap.getMapNr()-1];

            currentPlayerAnimation = animationHandler.currentPlayerAnimation(); //preiau animatia curenta
            currentPlayerAnimation.runAnimation(); //pornesc animatia
            animationHandler.runCoinAnimations();
            //todo other animations
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw() {
        /// Returnez bufferStrategy pentru canvasul existent
        /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
        BufferStrategy bufferStrategy = gameWindow.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if (bufferStrategy == null) {
            /// Se executa doar la primul apel al metodei Draw()
            try {
                /// Se construieste tripul buffer
                gameWindow.GetCanvas().createBufferStrategy(3);
                return;
            } catch (Exception e) {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
        /// Se obtine contextul grafic curent in care se poate desena.
        Graphics graphics = bufferStrategy.getDrawGraphics();
        /// Se sterge ce era
        graphics.clearRect(0, 0, gameWindow.GetWndWidth(), gameWindow.GetWndHeight());
        gameWindowFrame.requestFocusInWindow();

        animationHandler.aniomationStopTimeHandler(); //verific daca trebuie oprit o posibila animatie in curs
        graphics.drawImage(currentMapImage, 0, 0,gameWindow.GetWndWidth(), gameWindow.GetWndHeight(), null);
        currentPlayerAnimation.drawAnimation(graphics,player.xCoord,player.yCoord-corectiePlayerCoord,Tile.TILE_HEIGHT,Tile.TILE_WIDTH);
        animationHandler.drawCoinAnimations(graphics);
        animationHandler.animationStartTimeHandler(); //verific daca trebuie pornita vreo animatie


        if(grid) {//grid on //todo vezi daca poti scapa de ele de aici
            for (int x = 0; x < gameWindow.GetWndWidth(); x += Tile.TILE_HEIGHT)
                for (int y = 0; y < gameWindow.GetWndHeight(); y += Tile.TILE_HEIGHT) {
                    graphics.drawRect(x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
                }
        }

        if(displayRect) { //afisez chenarul daca este necesar
            graphics.drawRect(player.xCoord, player.yCoord, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            graphics.drawRect(player.xCoord+Tile.TILE_HEIGHT/2-5, player.yCoord+Tile.TILE_HEIGHT/2-5,10 ,10);
        }

        // end operatie de desenare
        /// Se afiseaza pe ecran
        bufferStrategy.show();

        /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
        /// elementele grafice ce au fost desenate pe canvas).
        graphics.dispose();
    }

    @Deprecated
    private boolean canAdvance(int x, int y, int corner)
    //idee de functie pentru alte verificari corectii
    {
        int realX1, realY1, realX2, realY2;
        realY1=realY2=realX2=realX1=0;
        switch (corner){
            case 1:
            {
                realX1 = x; realY1=y;
                realX2 = x+Tile.TILE_HEIGHT; realY2=y;
            }
                //up case
            case 2:
            {
                realX1 = x+Tile.TILE_HEIGHT; realY1=y;
                realX2 = realX1; realY2=y+Tile.TILE_HEIGHT;
            }
                //right case
            case 3:
            {
                realX1 = x+Tile.TILE_HEIGHT; realY1=y+Tile.TILE_HEIGHT;
                realX2 = x; realY2=realY1;
            }
                //down case
            case 4:
            {
                realX1 = x; realY1=y+Tile.TILE_HEIGHT;
                realX2 = x; realY2=y;
            }
                //left case
            default:
        }

        System.out.println("x1: "+realX1+", y1: "+realY1+"\nx2: "+realX2+", y2: "+realY2);
        System.out.println("mx1: "+realX1/Tile.TILE_HEIGHT+", my1: "+realY1/Tile.TILE_HEIGHT+"\nmx2: "+realX2/Tile.TILE_HEIGHT+", my2: "+realY2/Tile.TILE_HEIGHT);

        return false;

    }


}
