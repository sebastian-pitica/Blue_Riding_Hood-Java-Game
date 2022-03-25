package BlueRidingHood;

import BlueRidingHood.GameWindow.GameWindow;
import BlueRidingHood.Graphics.Animator;
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
    private GameWindow window;        /*!< Fereastra in care se va desena tabla jocului*/
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

    private int playerXCoord, playerYCoord, playerMatrixX, playerMatrixY;
    private int playerStepSize;

    private Tile tile; /*!< variabila membra temporara. Este folosita in aceasta etapa doar pentru a desena ceva pe ecran.*/

    private Animator playerStandAnimation, playerLeftRun, playerRightRun, actualAnimation;

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
        window = new GameWindow();
        /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
    }

    private boolean checkActualPosition()//todo alta versiune
    {
        int xdjIdeal=(playerMatrixX+1)*48;
        int ydjIdeal=(playerMatrixY+1)*48;
        return true;
    }

    //todo functie care upgradeaza x si y in matrice in functie de pasi

    private void playerPosition()
    {
        System.out.print("\nx: "+playerXCoord+", y: "+playerYCoord+"\nmatx: "+playerMatrixX+", maty: "+playerMatrixY+"\n");
        System.out.println("Correct?: "+checkActualPosition());
        System.out.println("x%48= "+playerXCoord%48);
        System.out.println("x/48= "+playerXCoord/48);
        System.out.println("y%48= "+playerYCoord%48);
        System.out.println("y/48= "+playerYCoord/48);
    }

    private void updatePosition()
    {
        playerMatrixX = playerXCoord / 48;
        playerMatrixY = playerYCoord / 48;
    }


    private void stepVertical(char sign)  //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput final de matrice
    {
        updatePosition();

//TODO UPDATE COORD
        if(sign=='+') {
            boolean test = Map.map1.canAdvance(playerMatrixX, playerMatrixY + 1);
            if (test) {
                playerYCoord += playerStepSize;
            }
        }
        else {
            boolean test = Map.map1.canAdvance(playerMatrixX, playerMatrixY - 1);
            if (test) {
                playerYCoord -= playerStepSize;
            }
            else
            {
                if(playerYCoord>playerMatrixY*48)
                {
                    playerYCoord -= playerStepSize;
                }
            }
        }
        updatePosition();
    }

    private void stepHorizontal(char sign)
    {
        updatePosition();
        if(sign=='+')
        {
            boolean test = Map.map1.canAdvance(playerMatrixX+1, playerMatrixY);
            if (test) {
                playerXCoord += playerStepSize;}
        }
        else
        {
            boolean test = Map.map1.canAdvance(playerMatrixX-1, playerMatrixY);
            if (test) {
                playerXCoord -= playerStepSize;}
            else
            {
                if(playerXCoord>playerMatrixX*48)
                {
                    playerXCoord -= playerStepSize;
                }
            }
        }
        updatePosition();
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame() {
        window = new GameWindow();
        /// Este construita fereastra grafica.
        window.BuildGameWindow();
        /// Se incarca toate elementele grafice (dale)
        Assets.Init();
        playerYCoord = playerMatrixY = Map.map1.startY();
        playerYCoord*=48;
        playerXCoord = playerMatrixX =  0;
        playerStepSize = 1;

        playerStandAnimation = new Animator(6,Assets.playerRightStand);
        playerLeftRun = new Animator(3,Assets.playerLeftRun);
        playerRightRun = new Animator(3,Assets.playerRightRun);
        actualAnimation = playerStandAnimation;
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run() {
        /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

        /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
        /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

        /// Atat timp timp cat threadul este pornit Update() & Draw()
        int i=0;
        while (runState == true) { // TODO: 24.03.2022 check ce mai e pe aici
            /// Se obtine timpul curent
            curentTime = System.nanoTime();
            /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if ((curentTime - oldTime) > timeFrame) {
                /// Actualizeaza pozitiile elementelor
                if((curentTime - oldTime) > timeFrame)
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
    public synchronized void StartGame() {
        if (runState == false) {
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
        if (runState == true) {
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
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {//todo just one movement key
        //todo display rect button
        if(window!=null) {
            if (window.keyboardInputManager.up) {
                stepVertical('-');
                playerPosition();
                if(Objects.equals(window.keyboardInputManager.lastMovementDirection, "left"))
                {
                    actualAnimation = playerLeftRun;
                }
                else
                {
                    if(Objects.equals(window.keyboardInputManager.lastMovementDirection, "right"))
                    {
                        actualAnimation = playerRightRun ;
                    }
                }
            }
            if (window.keyboardInputManager.down) {
                stepVertical('+');
                playerPosition();
                if(Objects.equals(window.keyboardInputManager.lastMovementDirection, "left"))
                {
                    actualAnimation = playerLeftRun;
                }
                else
                {
                    if(Objects.equals(window.keyboardInputManager.lastMovementDirection, "right"))
                    {
                        actualAnimation = playerRightRun ;
                    }
                }
            }
            if (window.keyboardInputManager.left) {
                stepHorizontal('-');
                playerPosition();
                actualAnimation = playerLeftRun;
            }
            if (window.keyboardInputManager.right) {
                stepHorizontal('+');
                playerPosition();
                actualAnimation = playerRightRun ;
            }

            if(window.keyboardInputManager.reset)//todo add to documentatie
            {
                playerYCoord=Map.map1.startY()*48;
                playerXCoord=0;
                actualAnimation = playerStandAnimation;
            }

            if(!window.keyboardInputManager.anyMovementKeyPressed())
            {
                actualAnimation = playerStandAnimation;
            }

            actualAnimation.runAnimation();
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw() {
        /// Returnez bufferStrategy pentru canvasul existent
        bufferStrategy = window.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if (bufferStrategy == null) {
            /// Se executa doar la primul apel al metodei Draw()
            try {
                /// Se construieste tripul buffer
                window.GetCanvas().createBufferStrategy(3);
                return;
            } catch (Exception e) {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
        /// Se obtine contextul grafic curent in care se poate desena.
        graphics = bufferStrategy.getDrawGraphics();
        /// Se sterge ce era
        graphics.clearRect(0, 0, window.GetWndWidth(), window.GetWndHeight());

        /// operatie de desenare
        // ...............

        // TODO: 24.03.2022!!!!!!

        //System.out.println("\n\n");
        //System.out.println(window.windowFrame.isFocused());
        //System.out.println(window.windowFrame.isActive());
        window.windowFrame.requestFocusInWindow();
        //System.out.println(window.keyboardInputManager == window.windowFrame.getKeyListeners()[0]);


        //graphics.drawRect(0,0,1440,768);
        //graphics.setColor(Color.black);
        //graphics.fillRect(0,0,1440,768);
        //playerPosition();
        graphics.drawImage(Assets.maps[0], 0, 0,1440, 768, null);
        actualAnimation.drawAnimation(graphics,playerXCoord,playerYCoord,Tile.TILE_HEIGHT,Tile.TILE_WIDTH);
        graphics.drawRect(playerXCoord,playerYCoord,Tile.TILE_WIDTH,Tile.TILE_HEIGHT);
        //graphics.fillRect(playerXCoord,playerYCoord,Tile.TILE_WIDTH,Tile.TILE_HEIGHT);
        //graphics.drawRect(0 * Tile.TILE_WIDTH, 10 * 48, 48, Tile.TILE_HEIGHT);


        // end operatie de desenare
        /// Se afiseaza pe ecran
        bufferStrategy.show();

        /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
        /// elementele grafice ce au fost desenate pe canvas).
        graphics.dispose();
    }

}
