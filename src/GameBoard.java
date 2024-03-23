import java.util.ArrayList;




public class GameBoard {
    RunGraphicalGame runGame;
    public int[][] grid;                 // the grid that stores the pieces
    int redR, redC;
    int blueR, blueC;
    int redStartR, redStartC;
    int blueStartR, blueStartC;
    int redCheckR, redCheckC;
    int blueCheckR, blueCheckC;
    int gridR, gridC;
    int redPlaced, bluePlaced, destinationPlaced, adminPlaced, piecePlace;
    int destR = -1;
    int destC = -1;
    int destRR = -1, destRC = -1;
    int destBR = -1, destBC = -1;
    int adminOR, adminOC;
    int adminRandMovesNum;
    int adminMoveDir;
    int destOneR, destOneC;
    int destTwoR, destTwoC;
    int[] adminPosMoveDir = new int[4];
    int ranMoveNum = 1;
    int randPieceMoving;
    int holeRanNum;
    int levelCount = 1;




    public GameBoard(int width, int height) {
        grid = new int[height][width];
        generateLevel();
    }




    // Make the requested move at (row, col) by changing the grid.
    // returns false if no move was made, true if the move was successful.
    public boolean move(int startRow, int startCol, int targetRow, int targetCol) {
        if (targetRow == startRow && targetCol == startCol) {
            System.out.println("DEBUGGING: You did not move.");
        }
        if(grid[startRow][startCol] == 5 || grid[startRow][startCol] == 9) {
            if ((targetRow == startRow - 1 && targetCol == startCol) || (targetRow == startRow + 1 && targetCol == startCol) || (targetCol == startCol + 1 && targetRow == startRow) || (targetCol == startCol - 1 && targetRow == startRow)) {
                if (isInGrid(targetRow, targetCol) && (grid[targetRow][targetCol] == 1 || grid[targetRow][targetCol] == 2 || grid[targetRow][targetCol] == 7 || grid[targetRow][targetCol] == 9)) {
                    System.out.println("DEBUGGING: You moved from (" + startRow + ", " + startCol + ") to (" + targetRow + ", " + targetCol + ").");
                    if(grid[targetRow][targetCol] == 7) {
                        destR = targetRow;
                        destC = targetCol;
                    }




                    if(grid[targetRow][targetCol] == 7) {
                        grid[targetRow][targetCol] = 9;
                    }else{
                        grid[targetRow][targetCol] = 5;
                    }




                    if(destR >= 0 && destC >= 0 && startRow == destR && startCol == destC) {
                        grid[startRow][startCol] = 2;
                    }else{
                        grid[startRow][startCol] = 1;
                    }




                    if (redR >= 0) {
                        redStartR = redR;
                    }
                    if (redC >= 0) {
                        redStartC = redC;
                    }
                    redCheckR = redR;
                    redCheckC = redC;




                    redCheckR += targetRow - startRow;
                    redCheckC += targetCol - startCol;




                    if (isInGrid(redCheckR, redCheckC)) {
                        if (grid[redCheckR][redCheckC] == 1 || grid[redCheckR][redCheckC] == 2) {
                            if(destRR >= 0 && destRC >= 0 && destRR == redR && destRC == redC) {
                                grid[destRR][destRC] = 2;
                            }else{
                                if ((redR == destOneR && redC == destOneC) || (redR == destTwoR && redC == destTwoC)) {
                                    grid[redR][redC] = 2;
                                }else {
                                    grid[redR][redC] = 1;
                                }
                            }
                            redR += targetRow - startRow;
                            redC += targetCol - startCol;




                            if(grid[redCheckR][redCheckC] == 2){
                                destRR = redCheckR;
                                destRC = redCheckC;
                            }




                            if (grid[redR][redC] == 2) {
                                grid[redR][redC] = 8;
                            }else{
                                grid[redR][redC] = 3;
                            }
                        }
                    }




                    if (blueR >= 0) {
                        blueStartR = blueR;
                    }
                    if (blueC >= 0) {
                        blueStartC = blueC;
                    }
                    blueCheckR = blueR;
                    blueCheckC = blueC;




                    blueCheckR += startRow - targetRow;
                    blueCheckC += startCol - targetCol;




                    if (isInGrid(blueCheckR, blueCheckC)) {
                        if (grid[blueCheckR][blueCheckC] == 1 || grid[blueCheckR][blueCheckC] == 2) {
                            if(destBR >= 0 && destBC >= 0 && destBR == blueR && destBC == blueC) {
                                grid[destBR][destBC] = 2;
                            }else{
                                grid[blueR][blueC] = 1;
                            }
                            blueR += startRow - targetRow;
                            blueC += startCol - targetCol;




                            if(grid[blueCheckR][blueCheckC] == 2){
                                destBR = blueCheckR;
                                destBC = blueCheckC;
                            }




                            if (grid[blueR][blueC] == 2) {
                                grid[blueR][blueC] = -8;
                            }else{
                                grid[blueR][blueC] = -3;
                            }
                        }
                    }




                    if((redR == destOneR && redC == destOneC && blueR == destTwoR && blueC == destTwoC) || (redR == destTwoR && redC == destTwoC && blueR == destOneR && blueC == destOneC)) {
                        System.out.println("LEVEL COMPLETED. GENERATING NEW LEVEL");
                        levelCount++;
                        generateLevel();
                    }else if(redR == destOneR && redR == destOneC) {
                    }else if(redR == destTwoR && redR == destTwoC) {
                    }else if(blueR == destTwoR && blueC == destTwoC) {
                    }else if(blueR == destOneR && blueC == destOneC) {
                    }
                }




            }
        }
        // check if move is not valid.  If so, return false.




        return true; // if move was valid, return true
    }




    public void showPossibleMoves(int startRow, int startCol, int targetRow, int targetCol, int selectionType){
        if ((grid[startRow][startCol] == 5 || grid[startRow][startCol] == 9 || grid[startRow][startCol] == -5) && selectionType == 1) { //Show possible moves
            if(grid[startRow][startCol] == 9) {
                grid[startRow][startCol] = -9;
            }if(grid[startRow][startCol] == 5){
                grid[startRow][startCol] = -5;
            }
            if (startRow != 0) {
                if (grid[startRow - 1][startCol] == 1) {
                    grid[startRow - 1][startCol] = 6;
                }if(grid[startRow - 1][startCol] == 2) {
                    grid[startRow - 1][startCol] = 7;
                }
            }
            if (startRow != grid.length-1) {
                if (grid[startRow + 1][startCol] == 1) {
                    grid[startRow + 1][startCol] = 6;
                }if(grid[startRow + 1][startCol] == 2){
                    grid[startRow + 1][startCol] = 7;
                }
            }
            if (startCol != 0) {
                if (grid[startRow][startCol - 1] == 1) {
                    grid[startRow][startCol - 1] = 6;
                }if(grid[startRow][startCol - 1] == 2){
                    grid[startRow][startCol - 1] = 7;
                }
            }




            if(startCol != grid[startRow].length-1) {
                if (grid[startRow][startCol + 1] == 1) {
                    grid[startRow][startCol + 1] = 6;
                }if(grid[startRow][startCol + 1] == 2){
                    grid[startRow][startCol + 1] = 7;
                }
            }
        }
        if(selectionType != 1){ //Hide possible moves
            if(grid[startRow][startCol] == -9) {
                grid[startRow][startCol] = 9;
            }if(grid[startRow][startCol] == -5){
                grid[startRow][startCol] = 5;
            }
            for(int h = 0; h < grid.length; h++){
                for(int w = 0; w < grid[h].length; w++){
                    if (grid[h][w] == 6) {
                        grid[h][w] = 1;
                    }if (grid[h][w] == 7) {
                        if((targetRow == startRow && targetCol == startCol) || (h != targetRow || w != targetCol)) {
                            grid[h][w] = 2;
                        }
                    }
                }
            }
        }
    }




    public void generateLevel(){
        System.out.println("====================");
        System.out.println("RANDOM LEVEL GENERATION DEBUGGING:");
        int adminPosMoveNum = 0;
        int adminPosMoveNumCheck = 0;
        ranMoveNum = 1;
        randPieceMoving = 0;




        for(int h = 0; h < grid.length; h++){ //Placing 5x5 grid
            for(int w = 0; w < grid[0].length; w++){
                grid[h][w] = 1;
            }
        }




        for(destinationPlaced = 0; destinationPlaced < 2;) { //Create Destinations
            gridR = (int) (Math.random() * 6);
            gridC = (int) (Math.random() * 5);
            if (grid[gridR][gridC] == 1) {
                grid[gridR][gridC] = 2;
                if(destinationPlaced == 0){
                    destOneC = gridC;
                    destOneR = gridR;
                    destinationPlaced++;
                }else{
                    destTwoR = gridR;
                    destTwoC = gridC;
                    destinationPlaced++;
                }
            }
        }




        redR = destOneR;
        redC = destOneC;
        blueR = destTwoR;
        blueC = destTwoC;




        for(adminPlaced = 0; adminPlaced == 0;) { //Create Admin
            System.out.println("check admin loc if valid");
            gridR = (int) (Math.random() * 6);
            gridC = (int) (Math.random() * 5);
            if (grid[gridR][gridC] == 1) {
                adminOR = gridR;
                adminOC = gridC;
                grid[adminOR][adminOC] = 5;
                adminPlaced++;
            }
        }




        System.out.println("Level Solution:");




        holeRanNum = (int)(Math.random()*10+1);
        for(int ranCounter = 0; ranCounter < holeRanNum;){ //Create holes in # random places
            gridR = (int)(Math.random()*6);
            gridC = (int)(Math.random()*5);
            if(grid[gridR][gridC] == 1) {
                ranCounter++;
                grid[gridR][gridC] = 0;
            }
        }




        adminRandMovesNum = (int)(Math.random()*10+1); //Move admin randomly, with statues moving accordingly
        System.out.println("# of moves: "+adminRandMovesNum);
        for(int adminStartMoves = 0; adminStartMoves < adminRandMovesNum; adminStartMoves++){ //Move in # random directions
            adminPosMoveDir[0] = 0;
            adminPosMoveDir[1] = 0;
            adminPosMoveDir[2] = 0;
            adminPosMoveDir[3] = 0;
            if ((adminOR - 1) >= 0) {
                if (grid[adminOR - 1][adminOC] == 1 || grid[adminOR - 1][adminOC] == 2) { //Checking directions for allowed moves
                    adminPosMoveDir[0] = 1;
                }
            }
            if ((adminOR + 1) < grid.length) {
                if (grid[adminOR + 1][adminOC] == 1 || grid[adminOR + 1][adminOC] == 2) {
                    adminPosMoveDir[1] = 1;
                }
            }
            if ((adminOC - 1) >= 0) {
                if (grid[adminOR][adminOC - 1] == 1 || grid[adminOR][adminOC - 1] == 2) {
                    adminPosMoveDir[2] = 1;
                }
            }
            if ((adminOC + 1) < grid[0].length) {
                if (grid[adminOR][adminOC + 1] == 1 || grid[adminOR][adminOC + 1] == 2) {
                    adminPosMoveDir[3] = 1;
                }
            }




            for(int i = 0; i < 3; i++) {
                if(adminPosMoveDir[i] == 1){
                    adminPosMoveNum++;
                }
                if(i == 3){
                    adminPosMoveNumCheck = 1;
                }
            }




            if (adminPosMoveNumCheck == 1 && adminPosMoveNum != 4) {
                System.out.println("LEVEL CANNOT BE COMPLETED. GENERATING NEW LEVEL");
                generateLevel();
            }




            for(int o = 0; o == 0;) {
                adminMoveDir = (int) (Math.random() * 3); //Move in a random available direction
                if ((adminOR == destOneR && adminOC == destOneC) || (adminOR == destTwoR && adminOC == destTwoC)) {
                    grid[adminOR][adminOC] = 2;
                }else{
                    grid[adminOR][adminOC] = 1;
                }
                if (adminMoveDir == 0) {
                    if (adminPosMoveDir[0] == 1) {
                        System.out.println(ranMoveNum+": "+"Move Admin DOWN");
                        ranMoveNum++;
                        adminOR--;
                        o++;
                    }
                }
                if (adminMoveDir == 1) {
                    if (adminPosMoveDir[1] == 1) {
                        System.out.println(ranMoveNum+": "+"Move Admin UP");
                        ranMoveNum++;
                        adminOR++;
                        o++;
                    }
                }
                if (adminMoveDir == 2) {
                    if (adminPosMoveDir[2] == 1) {
                        System.out.println(ranMoveNum+": "+"Move Admin RIGHT");
                        ranMoveNum++;
                        adminOC--;
                        o++;
                    }
                }
                if (adminMoveDir == 3) {
                    if (adminPosMoveDir[3] == 1) {
                        System.out.println(ranMoveNum+": "+"Move Admin LEFT");
                        ranMoveNum++;
                        adminOC++;
                        o++;
                    }
                }
                if(grid[adminOR][adminOC] == 1){
                    grid[adminOR][adminOC] = 5;
                }if(grid[adminOR][adminOC] == 2){
                    grid[adminOR][adminOC] = 9;
                }
            }




            redCheckR = redR;
            redCheckC = redC;




            if (adminMoveDir == 0) {
                redCheckR--;
            }if (adminMoveDir == 1) {
                redCheckR++;
            }if (adminMoveDir == 2) {
                redCheckC--;
            }if (adminMoveDir == 3) {
                redCheckC++;
            }
            if (isInGrid(redCheckR, redCheckC)) {
                if (grid[redCheckR][redCheckC] == 1 || grid[redCheckR][redCheckC] == 2) {
                    if ((redR == destOneR && redC == destOneC) || (redR == destTwoR && redC == destTwoC)) {
                        grid[redR][redC] = 2;
                    }else{
                        grid[redR][redC] = 1;
                    }
                    if (adminMoveDir == 0) {
                        redR--;
                    }if (adminMoveDir == 1) {
                        redR++;
                    }if (adminMoveDir == 2) {
                        redC--;
                    }if (adminMoveDir == 3) {
                        redC++;
                    }
                    if(grid[redR][redC] == 1){
                        grid[redR][redC] = 3;
                    }if(grid[redR][redC] == 2){
                        grid[redR][redC] = 8;
                    }
                }
            }


            blueCheckR = blueR;
            blueCheckC = blueC;


            if (adminMoveDir == 0) {
                blueCheckR++;
            }if (adminMoveDir == 1) {
                blueCheckR--;
            }if (adminMoveDir == 2) {
                blueCheckC++;
            }if (adminMoveDir == 3) {
                blueCheckC--;
            }
            if (isInGrid(blueCheckR, blueCheckC)) {
                if (grid[blueCheckR][blueCheckC] == 1 || grid[blueCheckR][blueCheckC] == 2) {
                    if ((blueR == destOneR && blueC == destOneC) || (blueR == destTwoR && blueC == destTwoC)) {
                        grid[blueR][blueC] = 2;
                    }else{
                        grid[blueR][blueC] = 1;
                    }
                    if (adminMoveDir == 0) {
                        blueR++;
                    }if (adminMoveDir == 1) {
                        blueR--;
                    }if (adminMoveDir == 2) {
                        blueC++;
                    }if (adminMoveDir == 3) {
                        blueC--;
                    }
                    if(grid[blueR][blueC] == 1){
                        grid[blueR][blueC] = -3;
                    }if(grid[blueR][blueC] == 2){
                        grid[blueR][blueC] = -8;
                    }
                }
            }
            if(adminStartMoves == adminRandMovesNum-1){
                randPieceMoving = 1;
            }
        }
        if(randPieceMoving == 1) { //Display pieces
            if((redR == destOneR && redC == destOneC && blueR == destTwoR && blueC == destTwoC) || (redR == destTwoR && redC == destTwoC && blueR == destOneR && blueC == destOneC)) {
                System.out.println("LEVEL ALREADY COMPLETED. GENERATING NEW LEVEL");
                generateLevel();
            }else{
                if(grid[adminOR][adminOC] == 2){
                    grid[adminOR][adminOC] = 9;
                }else{
                    grid[adminOR][adminOC] = 5;
                }
                if (grid[redR][redC] == 2 || grid[redR][redC] == 8) {
                    destRR = redR;
                    destRC = redC;
                    grid[redR][redC] = 8;
                } else {
                    grid[redR][redC] = 3;
                }
                if (grid[blueR][blueC] == 2 || grid[blueR][blueC] == -8) {
                    destBR = blueR;
                    destBC = blueC;
                    grid[blueR][blueC] = -8;
                } else {
                    grid[blueR][blueC] = -3;
                }
                System.out.println("====================");
                randPieceMoving = 0;
            }
        }
    }








    //Return true if the game is over. False otherwise.
    public boolean isGameOver() {




        /*** YOU COMPLETE THIS METHOD ***/




        return false;
    }




    public int[][] getGrid() {
        return grid;
    }




    // Return true if the row and column in location loc are in bounds for the grid
    public boolean isInGrid(int startRow, int startCol) {
        if(startRow >= 0 && startRow < grid.length && startCol >= 0 && startCol < grid[0].length){
            return true;
        }




        return false;
    }
}
