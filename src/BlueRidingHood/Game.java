package BlueRidingHood;

import BlueRidingHood.Entities.Player;
import BlueRidingHood.GameWindow.GameWindow;
import BlueRidingHood.Graphics.Animation;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.InputManager.KeyboardInputManager;
import BlueRidingHood.Tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private Graphics graphics;          /*!< Referinta catre un context grafic.*/
    private KeyboardInputManager keyboardInputManager;
    private JFrame gameWindowFrame;

    private Player player;
    //todo other entities
    private Tile tile; /*!< variabila membra temporara. Este folosita in aceasta etapa doar pentru a desena ceva pe ecran.*/ //todo check it
    private Map currentMap;
    private Image currentMapImage;
    private Animation currentPlayerAnimation;
    private Animation[] coinsAnimations;
    //todo other entities animations: monede, inamici, atacuri

    private boolean displayRect = false;
    private boolean grid = false;
    //flag pentru check afisare rect informativ
    private long shieldStartTime;
    //todo possible other time animations: atacuri, etc

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

        keyboardInputManager = gameWindow.getKeyboardInputManager();
        gameWindowFrame = gameWindow.getWindowFrame();
        //preiau referinta la fereastra si keyboard input manager

        gameWindowFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent e) {StopGame();System.exit(0);}});
        //setez ca la inchiderea ferestrei sa se apeleze functia de StopGame() si System.exit(0)

        Assets.Init();

        coinsAnimationsInit();

        currentMap = Map.map1;
        currentMapImage = Assets.maps[0];

        //todo add gui & others for start
        player = new Player(0, currentMap.startY()*Tile.TILE_HEIGHT,0, currentMap.startY(),4,2);
        currentPlayerAnimation = player.rightStand; //animatia default

        //todo init pentru alte entitati
    }

    private void  coinsAnimationsInit()
    {
        coinsAnimations = new Animation[28]; //28 de monede per harta
        for (int i = 0; i < 28;++i)
        {
            coinsAnimations[i] = new Animation(6, Assets.coin);
        }
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()
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
    public synchronized void StartGame()
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

    }//todo check utility

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {
        if(gameWindow !=null)
        //daca exista referinta la fereastra
        {
            displayRect = keyboardInputManager.rectangular; //verific daca e nevoie sa afisez rect
            grid = keyboardInputManager.grid;
            playerInputHandler(); //prelucrez inputul de la jucator
            currentPlayerAnimation = currentAnimationHandler(); //preiau animatia curenta
            currentPlayerAnimation.runAnimation(); //pornesc animatia
            runCoinAnimations();
        }
    }

    private void playerInputHandler()
            //functie care se ocupa cu prelucrarea inputului de la jucator
    {
        if(!player.attackActive) {
            //restrictionez miscarile si atacul simultan
            if (keyboardInputManager.left || keyboardInputManager.right)
            //restrictionez miscarile simultane pe cele doua axead
            {
                if (keyboardInputManager.left) {
                    stepHorizontal('-');
                    displayPlayerPosition();
                }
                if (keyboardInputManager.right) {
                    if(! currentMap.end(player.matrixX, player.matrixY)) {
                        stepHorizontal('+');
                        displayPlayerPosition();
                    }else
                    {
                        win();
                    }
                }
            } else {
                if (keyboardInputManager.up) {
                    stepVertical('-');
                    displayPlayerPosition();

                }
                if (keyboardInputManager.down) {
                    stepVertical('+');
                    displayPlayerPosition();

                }
            }
        }
        //reset pozitie jucator
        if(keyboardInputManager.reset)
        {
            player.yCoord= currentMap.startY()*Tile.TILE_HEIGHT;
            player.xCoord=0;

        }

        if(keyboardInputManager.quit)//todo close window + close game
        {
            StopGame();
            System.exit(0);
        }

    }

    private void win()
            //functie care determina ce se intampla dupa ce se petrece win la nivelul curent
            //todo modificare criterii de win
    {
        System.out.println("Win!");

        if(currentMap.mapNr==1) {
            currentMap = Map.map2;
            currentMapImage = Assets.maps[1];
            currentPlayerAnimation = player.rightStand;
            player.yCoord = currentMap.startY() * Tile.TILE_HEIGHT;
            player.xCoord = 0;
        }
        else
        {
            StopGame();
            System.exit(0);
        }
    }

    private void stepVertical(char sign)
            //todo add la documentatie corectia
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePlayerPositionInMatrix();
        if(sign=='+') { //deplasare in jos
            boolean test =  currentMap.canAdvance(player.matrixX, player.matrixY + 1);
            if (test) {
                player.yCoord += player.stepSize;
            }
            else
            {   //corectie deplasare in jos
                //cat timp coordonata actuala y e mai mica ca cea ideala
                if(player.yCoord<player.matrixY*Tile.TILE_HEIGHT)
                {
                    player.yCoord += player.stepSize;
                }
            }

        }
        else { //deplasare in sus
            boolean test =  currentMap.canAdvance(player.matrixX, player.matrixY - 1);
            if (test) {
                player.yCoord -= player.stepSize;
            }
            else
            {   //corectie deplasare in sus
                //cat timp coordonata actuala y e mai mare decat cea ideala
                if(player.yCoord>player.matrixY*Tile.TILE_HEIGHT)
                {
                    player.yCoord -= player.stepSize;
                }
            }
        }
        updatePlayerPositionInMatrix();
    }

    private void stepHorizontal(char sign)
            //todo add la documentatie corectia
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePlayerPositionInMatrix();
        if(sign=='+') //deplasare la dreapta
        {
            boolean test =  currentMap.canAdvance(player.matrixX+1, player.matrixY);
            if (test) {
                player.xCoord += player.stepSize;}
            else
            {
                //corectie deplasare la dreapta
                //cat timp pozitia reala pe x este mai mica decat cea ideala
                if(player.xCoord<player.matrixX*Tile.TILE_HEIGHT)
                {
                    player.xCoord += player.stepSize;
                }
            }
        }
        else
        {//deplasare la stanga
            boolean test =  currentMap.canAdvance(player.matrixX-1, player.matrixY);
            if (test) {
                player.xCoord -= player.stepSize;}
            else
            {
                //corectie deplasare la stanga
                //cat timp pozitia reala pe x este mai marre de cea ideala
                if(player.xCoord>player.matrixX*Tile.TILE_HEIGHT)
                {
                    player.xCoord -= player.stepSize;
                }
            }
        }
        updatePlayerPositionInMatrix();
    }

    private void updatePlayerPositionInMatrix()
    //updateaza pozitia jucatorului in coordonate matriceale pe baza coordonatelor x, y
    {
        player.matrixX = (player.xCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
        player.matrixY = (player.yCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;

        if( currentMap.canKill(player.matrixX, player.matrixY))
        //todo kill the player
            //daca jucatorul ajunge pe un camp insta kill este teleportat pe pozitia initiala
        {
            player.yCoord= currentMap.startY()*Tile.TILE_HEIGHT;
            player.xCoord=0;

            updatePlayerPositionInMatrix();
        }
        //todo other interactions
    }

    private void displayPlayerPosition()
    //afiseaza pozitia jcuatorului in coordonate x, y si in coordonate matriceale
    {
        System.out.print("\nx: "+player.xCoord+", y: "+player.yCoord+"\nmatx: "+player.matrixX+", maty: "+player.matrixY+"\n");
    }

    private Animation currentAnimationHandler()
    {
        //todo add la documentatie deficitul de animatie
        //pentru urcare/coborare (deplasare pe axa y) nu exista spriteuri
        //in aceste cazuri animatia de deplasare este cea a ultimei directii pe orizontala
        Animation result = null;

        if (keyboardInputManager.up || keyboardInputManager.down) {

            if(Objects.equals(keyboardInputManager.lastHorizontalDirection, "left"))
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
                if(Objects.equals(keyboardInputManager.lastHorizontalDirection, "right"))
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

        if (keyboardInputManager.left) {
            if(!player.shieldActive) {
                result = player.leftRun;
            }
            else
            {
                result = player.leftShieldRun;
            }
        }
        if (keyboardInputManager.right) {
            if(!player.shieldActive) {
                result = player.rightRun;
            }
            else
            {
                result = player.rightShieldRun;
            }
        }

        if(keyboardInputManager.reset || !keyboardInputManager.anyMovementKeyPressed())
            //daca s-a facut reset sau nu s-a apasat nici o tasta de miscare
            //todo add to documentatie explicatii
        {
            if(Objects.equals(keyboardInputManager.lastHorizontalDirection, "left"))
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
                if(Objects.equals(keyboardInputManager.lastHorizontalDirection, "right"))
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

        if(keyboardInputManager.swordAttack) {
            //todo add explicatii documentatie
            //daca este apasata tasta de atac
            if (player.attackActive) {
                //daca atacul este in desfasurare
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "left")) {
                    if (!player.shieldActive) {
                        result = player.leftAttackSword;
                    } else {
                        result = player.leftShieldAttackSword;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "right")) {
                        if (!player.shieldActive) {
                            result = player.rightAttackSword;
                        } else {
                            result = player.rightShieldAttackSword;
                        }
                    }
                }
            } else {
                //daca atacul nu este in desfasurare
                player.attackActive = true;
                //atacul este acum in desfasurare
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "left")) {
                    if (!player.shieldActive) {
                        result = player.leftDrawSword;
                    } else {
                        result = player.leftShieldDrawSword;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "right")) {
                        if (!player.shieldActive) {
                            result = player.rightDrawSword;
                        } else {
                            result = player.rightShieldDrawSword;
                        }
                    }
                }
            }
        }
        else
        {
            //daca nu a fost apasata tasta de atac, dar atacul este in desfasurare
            if(player.attackActive) {
                player.attackActive = false;
                //atacul este incheiat
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "left")) {
                    if (!player.shieldActive) {
                        result = player.leftRetractSword;
                    } else {
                        result = player.leftShieldRetractSword;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "right")) {
                        if (!player.shieldActive) {
                            result = player.rightRetractSword;
                        } else {
                            result = player.rightShieldRetractSword;
                        }
                    }
                }
            }
        }

        return result;
    }

    private void runCoinAnimations() {
        for (int i = 0; i < 28;++i)
        {
           coinsAnimations[i].runAnimation();
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
        gameWindowFrame.requestFocusInWindow();

        //check window areea
        //System.out.println(window.windowFrame.isFocused());
        //System.out.println(window.windowFrame.isActive());
        //System.out.println(window.keyboardInputManager == window.windowFrame.getKeyListeners()[0]);
        //gameWindow.windowFrame.requestFocusInWindow();

        aniomationStopTimeHandler(); //verific daca trebuie oprit o posibila animatie in curs
        graphics.drawImage(currentMapImage, 0, 0,gameWindow.GetWndWidth(), gameWindow.GetWndHeight(), null);
        currentPlayerAnimation.drawAnimation(graphics,player.xCoord,player.yCoord-12,Tile.TILE_HEIGHT,Tile.TILE_WIDTH);
        drawCoinAnimations(graphics);
        //-12 corectie pozitie sprite pentru a fi pe o pozitiie centrala de afisare
        animationStartTimeHandler(); //verific daca trebuie pornita vreo animatie


        if(grid) {//grid on
            for (int x = 0; x < gameWindow.GetWndWidth(); x += Tile.TILE_HEIGHT)
                for (int y = 0; y < gameWindow.GetWndHeight(); y += Tile.TILE_HEIGHT) {
                    graphics.drawRect(x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
                }
        }

        if(displayRect) { //afisez chenarul daca este necesar
            graphics.drawRect(player.xCoord, player.yCoord, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            graphics.drawRect(player.xCoord+Tile.TILE_HEIGHT/2-5, player.yCoord+Tile.TILE_HEIGHT/2-5,10 ,10);
        }
        //graphics.fillRect(player.xCoord,player.yCoord,Tile.TILE_WIDTH,Tile.TILE_HEIGHT);
        //graphics.drawRect(0 * Tile.TILE_WIDTH, 10 * Tile.TILE_HEIGHT, Tile.TILE_HEIGHT, Tile.TILE_HEIGHT);


        // end operatie de desenare
        /// Se afiseaza pe ecran
        bufferStrategy.show();

        /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
        /// elementele grafice ce au fost desenate pe canvas).
        graphics.dispose();
    }

    private void animationStartTimeHandler() //todo for all animations
    {
        if(keyboardInputManager.shieldActivated) //daca scutul a fost activat
        {
            player.shieldActive = true; //schimb statusul de activ al scutului pentru player
            shieldStartTime = System.nanoTime(); //preiau timpul de inceput
        }
    }

    private void aniomationStopTimeHandler() //todo for all animations
    {
        if(timeToStopAnimation(shieldStartTime, "shield")) //verific daca este nevoie sa opresc animatia scutului
        {
            player.shieldActive = false;
        }
    }

    private boolean timeToStopAnimation(long startTime, String animation)
    {
        //todo for all timed animation
        long nowTime = System.nanoTime(); //masor timpul actual

        //verific daca e mai mare ca 5 secunde
        if((nowTime - startTime)/5 >= 1000000000) //for shield animation switch case
        //todo variabila cu limita de secunde
            {
            return true;
        }
        else
        {
            return false;
        }

    }

    private void drawCoinAnimations(Graphics g) //todo coin disapear
    {
        for(int i=0;i<28;++i)
        {
            int y = currentMap.getCoinPositionAtIndex(i)/100;
            int x = currentMap.getCoinPositionAtIndex(i)%100;
            coinsAnimations[i].drawAnimation(g,x*Tile.TILE_WIDTH+Tile.TILE_WIDTH/2-8,
                    y* Tile.TILE_HEIGHT+Tile.TILE_HEIGHT/2-8,16,16);
        }
    }

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
