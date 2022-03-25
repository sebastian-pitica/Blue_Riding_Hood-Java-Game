package BlueRidingHood;

import BlueRidingHood.Entities.Player;
import BlueRidingHood.GameWindow.GameWindow;
import BlueRidingHood.Graphics.Animation;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Objects;

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
    private BufferStrategy bufferStrategy;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private Graphics graphics;          /*!< Referinta catre un context grafic.*/

    private Player player;
    //todo other entities
    private Tile tile; /*!< variabila membra temporara. Este folosita in aceasta etapa doar pentru a desena ceva pe ecran.*/ //todo check it

    private Animation currentPlayerAnimation;
    //todo possible other animations

    private boolean displayRect = false;
    private long shieldStartTime;

    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public Game() {
        /// Obiectul GameWindow este creat insa fereastra nu este construita
        /// Acest lucru va fi realizat in metoda init() prin apelul
        /// functiei BuildGameWindow();
        gameWindow = new GameWindow();
        /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
    }

    private boolean checkActualPosition()//todo alta versiune cu posibila corecti
    {
        int xdjIdeal=(player.matrixX+1)*48;
        int ydjIdeal=(player.matrixY+1)*48;
        return true;
    }

    private void displayPlayerPosition()
    {
        System.out.print("\nx: "+player.xCoord+", y: "+player.yCoord+"\nmatx: "+player.matrixX+", maty: "+player.matrixY+"\n");
    }

    private void updatePlayerPositionInMatrix()
    {
        player.matrixX = player.xCoord / 48;
        player.matrixY = player.yCoord / 48;
    }

    private void stepVertical(char sign)
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput final de matrice
    {
        updatePlayerPositionInMatrix();
        if(sign=='+') {
            boolean test = Map.map1.canAdvance(player.matrixX, player.matrixY + 1);
            if (test) {
                player.yCoord += player.stepSize;
            }
        }
        else {
            boolean test = Map.map1.canAdvance(player.matrixX, player.matrixY - 1);
            if (test) {
                player.yCoord -= player.stepSize;
            }
            else
            {
                if(player.yCoord>player.matrixY*48)
                {
                    player.yCoord -= player.stepSize;
                }
            }
        }
        updatePlayerPositionInMatrix();
    }

    private void stepHorizontal(char sign)
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput final de matrice
    {
        updatePlayerPositionInMatrix();
        if(sign=='+')
        {
            boolean test = Map.map1.canAdvance(player.matrixX+1, player.matrixY);
            if (test) {
                player.xCoord += player.stepSize;}
        }
        else
        {
            boolean test = Map.map1.canAdvance(player.matrixX-1, player.matrixY);
            if (test) {
                player.xCoord -= player.stepSize;}
            else
            {
                if(player.xCoord>player.matrixX*48)
                {
                    player.xCoord -= player.stepSize;
                }
            }
        }
        updatePlayerPositionInMatrix();
    }

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
        Assets.Init();
        player = new Player(0,Map.map1.startY()*48,0,Map.map1.startY(),4,2);
        currentPlayerAnimation = player.rightStand;
        //todo init pentru alte entitati
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()//todo check but not touch
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
    public synchronized void StartGame() //todo check but not touch
    {
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

    private boolean timeToStopAnimation(long startTime, String animation)
    {
        //todo for all timed animation
        long nowTime = System.nanoTime();

        if((nowTime - startTime)/5 >= 1000000000) //for shield animation switch case
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame() {
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

    }//todo check utility

    private void animationStartTimeHandler() //todo for all animations
    {
        if(gameWindow.keyboardInputManager.shieldActivated)
        {
            player.shieldActive = true;
            shieldStartTime = System.nanoTime();
        }
    }

    private void aniomationStopTimeHandler() //todo for all animations
    {
        if(timeToStopAnimation(shieldStartTime, "shield"))
        {
            player.shieldActive = false;
        }
    }

    private void playerInputHandler()
    {
        if (gameWindow.keyboardInputManager.up) {
            stepVertical('-');
            displayPlayerPosition();

        }
        if (gameWindow.keyboardInputManager.down) {
            stepVertical('+');
            displayPlayerPosition();

        }
        if (gameWindow.keyboardInputManager.left) {
            stepHorizontal('-');
            displayPlayerPosition();
        }
        if (gameWindow.keyboardInputManager.right) {
            stepHorizontal('+');
            displayPlayerPosition();
        }

        if(gameWindow.keyboardInputManager.reset)//todo add to documentatie
        {
            player.yCoord=Map.map1.startY()*48;
            player.xCoord=0;

        }

    }

    private Animation currentAnimationHandler()
    {
        Animation result = null;

        if (gameWindow.keyboardInputManager.up || gameWindow.keyboardInputManager.down) {
            if(Objects.equals(gameWindow.keyboardInputManager.lastMovementDirection, "left"))
            {
                if(!player.shieldActive) {
                    result = player.leftRun;
                }
                else
                {
                    result = player.leftShieldRun;
                }
            }
            else
            {
                if(Objects.equals(gameWindow.keyboardInputManager.lastMovementDirection, "right"))
                {
                    if(!player.shieldActive) {
                        result = player.rightRun;
                    }
                    else
                    {
                        result = player.rightShieldRun;
                    }
                }
            }
        }

        if (gameWindow.keyboardInputManager.left) {
            if(!player.shieldActive) {
                result = player.leftRun;
            }
            else
            {
                result = player.leftShieldRun;
            }
        }
        if (gameWindow.keyboardInputManager.right) {
            if(!player.shieldActive) {
                result = player.rightRun;
            }
            else
            {
                result = player.rightShieldRun;
            }
        }

        if(gameWindow.keyboardInputManager.reset || !gameWindow.keyboardInputManager.anyMovementKeyPressed())//todo add to documentatie
        {
            if(Objects.equals(gameWindow.keyboardInputManager.lastMovementDirection, "left"))
            {
                if(!player.shieldActive) {
                    result = player.leftStand;
                }
                else
                {
                    result = player.leftShieldStand;
                }
            }
            else
            {
                if(Objects.equals(gameWindow.keyboardInputManager.lastMovementDirection, "right"))
                {
                    if(!player.shieldActive) {
                        result = player.rightStand;
                    }
                    else
                    {
                        result = player.rightShieldStand;
                    }
                }
            }
        }

        return result;
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {

        if(gameWindow !=null) {
            displayRect = gameWindow.keyboardInputManager.rectangular;
            playerInputHandler();
            currentPlayerAnimation = currentAnimationHandler();
            currentPlayerAnimation.runAnimation();
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw() {
        /// Returnez bufferStrategy pentru canvasul existent
        bufferStrategy = gameWindow.GetCanvas().getBufferStrategy();
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
        graphics = bufferStrategy.getDrawGraphics();
        /// Se sterge ce era
        graphics.clearRect(0, 0, gameWindow.GetWndWidth(), gameWindow.GetWndHeight());


        //System.out.println(window.windowFrame.isFocused());
        //System.out.println(window.windowFrame.isActive());
        //System.out.println(window.keyboardInputManager == window.windowFrame.getKeyListeners()[0]);
        gameWindow.windowFrame.requestFocusInWindow();


        //graphics.drawRect(0,0,1440,768);
        //graphics.setColor(Color.black);
        //graphics.fillRect(0,0,1440,768);
        //playerPosition();

        aniomationStopTimeHandler();
        graphics.drawImage(Assets.maps[0], 0, 0,1440, 768, null);
        currentPlayerAnimation.drawAnimation(graphics,player.xCoord,player.yCoord,Tile.TILE_HEIGHT,Tile.TILE_WIDTH);
        animationStartTimeHandler();

        if(displayRect) {
            graphics.drawRect(player.xCoord, player.yCoord, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        }
        //graphics.fillRect(player.xCoord,player.yCoord,Tile.TILE_WIDTH,Tile.TILE_HEIGHT);
        //graphics.drawRect(0 * Tile.TILE_WIDTH, 10 * 48, 48, Tile.TILE_HEIGHT);


        // end operatie de desenare
        /// Se afiseaza pe ecran
        bufferStrategy.show();

        /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
        /// elementele grafice ce au fost desenate pe canvas).
        graphics.dispose();
    }

}
