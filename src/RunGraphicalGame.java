import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.*;
import processing.core.PImage; //Images
//Audio




public class RunGraphicalGame extends PApplet {
    GameBoard game;
    Display display;
    int startscreen = 1;
    int playButtonX = 180, playButtonY = 350;
    int timer = -1;
    int startRow, startCol, targetRow, targetCol;
    int selectionType;
    int gridW, gridH;
    int redDestT, blueDestT;
    String version = "1.1.0";
    Minim audio;
    AudioPlayer gBGM, tBGM, adminMove, redDest, blueDest, playHover, playClick, levelComplete;
    PImage appIcon, tBG, logo, playButton1, playButton2, playButton3;




    public void settings() {
        size(500, 600);
    }




    public void setup() {
        surface.setTitle("Stumpin' Stones"); //App titlebar name
        surface.setResizable(true);
        appIcon = loadImage("assets/adminselecttile.png");
        surface.setIcon(appIcon);
        tBG = loadImage("assets/wallpaperflare.com_wallpaper.jpg");
        logo = loadImage("assets/Stumpin_ Stones Logo.png");
        playButton1 = loadImage("assets/PlayButton1.png");
        playButton2 = loadImage("assets/PlayButton2.png");
        playButton3 = loadImage("assets/PlayButton3.png");




        audio = new Minim(this);
        tBGM = audio.loadFile("assets/xDeviruchi - Title Theme .wav");
        gBGM = audio.loadFile("assets/xDeviruchi - And The Journey Begins (Loop).wav");
        adminMove = audio.loadFile("assets/Hit 1.mp3");
        playHover = audio.loadFile("assets/MI_SFX 11.mp3");
        playClick = audio.loadFile("assets/MI_SFX 12.mp3");
        redDest = audio.loadFile("assets/Misc 1.wav");
        blueDest = audio.loadFile("assets/Misc 2.wav");
        levelComplete = audio.loadFile("assets/Success 1.wav");




        if(startscreen == 0) {
            // Create a game object
            game = new GameBoard(5, 6);




            // Create the display
            // parameters: (10,10) is upper left of display
            // (400, 400) is the width and height
            display = new Display(this, 0, 0, 500, 600);




            display.setColor(0, color(0, 0, 0)); // SET COLORS FOR PLAYER 1 & 2
            display.setColor(1, color(85, 85, 85));
            display.setColor(2, color(50, 50, 50));
            display.setColor(3, color(255, 0, 0)); //Red
            display.setColor(-3, color(0, 0, 255)); //Blue
            display.setColor(5, color(0, 255, 255));
            display.setColor(6, color(0, 180, 180));
            display.setColor(7, color(0, 180, 180));




      /* GRID PIECE TYPES:
     0: No place
     1: Place available
     2: Destination
     3: Statues
     4: Statue possible moves
     5/-5: Administrator
     6: Admin possible moves
     7: admin possible move on destination
     8/-8: statue on destination
     9/-9: admin on destination
     */
            display.setImage(0, "assets/The Rock.png");
            display.setImage(1, "assets/grasstile.png");
            display.setImage(2, "assets/destinationtile.png");
            display.setImage(3, "assets/redstatuetile.png");
            display.setImage(-3, "assets/bluestatuetile.png");
            display.setImage(5, "assets/admintile.png");
            display.setImage(-5, "assets/adminselecttile.png");
            display.setImage(6, "assets/adminmovetile.png");
            display.setImage(7, "assets/adminmovedesttile.png");
            display.setImage(8, "assets/reddestinationtile.png");
            display.setImage(-8, "assets/bluedestinationtile.png");
            display.setImage(9, "assets/admindesttile.png");
            display.setImage(-9, "assets/admindestselecttile.png");




            display.initializeWithGame(game);
        }
    }




    @Override
    public void draw() {
        image(tBG,-800,-1200);
        if (startscreen == 0) {
            if (!gBGM.isPlaying()) {
                gBGM.rewind();
                gBGM.play();
            }
            display.drawGrid(game.getGrid()); // display the game
            textSize(100);
            fill(135,206,235);
            text(game.levelCount, 430, 90);
            //rect(50,10,200,50);




            if((game.redR == game.destOneR && game.redC == game.destOneC) || (game.redR == game.destTwoR && game.redC == game.destTwoC)) {
                if(redDestT == 0) {
                    //redDest.rewind();
                    //redDest.play();
                    redDestT = 1;
                }
            }else{
                redDestT = 0;
            }
            if((game.blueR == game.destTwoR && game.blueC == game.destTwoC) || (game.blueR == game.destOneR && game.blueC == game.destOneC)){
                if(blueDestT == 0) {
                    //blueDest.rewind();
                    //blueDest.play();
                    blueDestT = 1;
                }
            }else{
                blueDestT = 0;
            }




            if((game.redR == game.destOneR && game.redC == game.destOneC && game.blueR == game.destTwoR && game.blueC == game.destTwoC) || (game.redR == game.destTwoR && game.redC == game.destTwoC && game.blueR == game.destOneR && game.blueC == game.destOneC)) {
                levelComplete.rewind();
                levelComplete.play();
                System.out.println("LEVEL COMPLETED. GENERATING NEW LEVEL");
                game.generateLevel();
            }




        }else{
            tBGM.play();
            image(logo, 50, 100);
            fill(255);
            textSize(15);
            text("Created with Java by Alan & Dean. Ver. "+version,110,580);
            image(playButton1, playButtonX, playButtonY);
            if (mouseX >= 180 && mouseX <= 313 && mouseY >= 350 && mouseY <= 417 && timer == -1) {
                image(playButton2, playButtonX, playButtonY);
                playHover.play();
                if (mousePressed) {
                    playHover.rewind();
                    playClick.play();
                    timer = 0;
                }




            } else {
                playHover.rewind();
                image(playButton1, playButtonX, playButtonY);
            }
            if(timer > -1) {
                if (timer < 25) {
                    image(playButton3, playButtonX, playButtonY);
                    timer++;
                }else{
                    startscreen = 0;
                    tBGM.close();
                    setup();
                }
            }
        }
    }




    public void mouseReleased() {
        if (startscreen == 0) {
            selectionType++;
            if (selectionType == 1) {
                Location loc = display.gridLocationAt(mouseX, mouseY);
                startRow = loc.getRow();
                startCol = loc.getCol();
                game.showPossibleMoves(startRow, startCol, targetRow, targetCol, selectionType);
            }
            if (selectionType == 2) {
                Location loc = display.gridLocationAt(mouseX, mouseY);
                targetRow = loc.getRow();
                targetCol = loc.getCol();
                if (game.grid[targetRow][targetCol] == 6 || game.grid[targetRow][targetCol] == 7) {
                    adminMove.play();
                    adminMove.rewind();
                }
                game.showPossibleMoves(startRow, startCol, targetRow, targetCol, selectionType);
                game.move(startRow, startCol, targetRow, targetCol);
                selectionType = 0;
            }
        }
    }




    // main method to launch this Processing sketch from computer
    public static void main(String[] args) {
        PApplet.main(new String[]{"RunGraphicalGame"});
    }
}
