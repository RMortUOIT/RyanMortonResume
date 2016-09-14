package mobileclasstesting.mazegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


public class MazeCanvas extends View {

    final int SPEED = 10;
    private int tileSize = 100;

    private boolean loadedMaze;

    private Handler handler;

    char tiles[][];

    private Bitmap Aimage;
    private Bitmap Bimage;
    private Bitmap Cimage;
    private Bitmap Dimage;
    private Bitmap Eimage;
    private Bitmap Fimage;
    private Bitmap Gimage;
    private Bitmap Himage;
    private Bitmap Iimage;
    private Bitmap Jimage;
    private Bitmap Kimage;
    private Bitmap Limage;
    private Bitmap Mimage;
    private Bitmap Nimage;
    private Bitmap Oimage;
    private Bitmap Pimage;

    private Bitmap dotImage;

    private Bitmap goalImage;
    private Bitmap playerImage;

    private int TOTAL_HEIGHT;
    private int TOTAL_WIDTH;

    private int cameraX;
    private int cameraY;

    private int playerX;
    private int playerY;

    private boolean assist;
    private boolean tracker[][]; // mirrors tiles, flags to true if player has explored this area


    //smooth animation ints
    private int playerXSlide;
    private int playerYSlide;

    private int cameraXSlide;
    private int cameraYSlide;


    private Runnable animationThread = new Runnable() {
        @Override
        public void run() {
            // redraw
            invalidate();
        }
    };

    public MazeCanvas(Context context) {
        super(context);

        loadedMaze = false;

        cameraX = 0;
        cameraY = 0;

        playerX = 0;
        playerY = 0;
    }

    public MazeCanvas(Context context, AttributeSet attribs) {
        super(context, attribs);

        loadedMaze = false;

        cameraX = 0;
        cameraY = 0;

        playerX = 0;
        playerY = 0;

    }





    private void initializeTiles() {

        handler = new Handler();

        // create bitmap of tiles
        Aimage = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        Bimage = BitmapFactory.decodeResource(getResources(), R.drawable.b);
        Cimage = BitmapFactory.decodeResource(getResources(), R.drawable.c);
        Dimage = BitmapFactory.decodeResource(getResources(), R.drawable.d);
        Eimage = BitmapFactory.decodeResource(getResources(), R.drawable.e);
        Fimage = BitmapFactory.decodeResource(getResources(), R.drawable.f);
        Gimage = BitmapFactory.decodeResource(getResources(), R.drawable.g);
        Himage = BitmapFactory.decodeResource(getResources(), R.drawable.h);
        Iimage = BitmapFactory.decodeResource(getResources(), R.drawable.i);
        Jimage = BitmapFactory.decodeResource(getResources(), R.drawable.j);
        Kimage = BitmapFactory.decodeResource(getResources(), R.drawable.k);
        Limage = BitmapFactory.decodeResource(getResources(), R.drawable.l);
        Mimage = BitmapFactory.decodeResource(getResources(), R.drawable.m);
        Nimage = BitmapFactory.decodeResource(getResources(), R.drawable.n);
        Oimage = BitmapFactory.decodeResource(getResources(), R.drawable.o);
        Pimage = BitmapFactory.decodeResource(getResources(), R.drawable.p);

        dotImage = BitmapFactory.decodeResource(getResources(), R.drawable.dot);

        goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.goal);
        playerImage = BitmapFactory.decodeResource(getResources(), R.drawable.ball);


        // scale bitmap of tiles
        Aimage = Bitmap.createScaledBitmap(Aimage, tileSize, tileSize, false);
        Bimage = Bitmap.createScaledBitmap(Bimage, tileSize, tileSize, false);
        Cimage = Bitmap.createScaledBitmap(Cimage, tileSize, tileSize, false);
        Dimage = Bitmap.createScaledBitmap(Dimage, tileSize, tileSize, false);
        Eimage = Bitmap.createScaledBitmap(Eimage, tileSize, tileSize, false);
        Fimage = Bitmap.createScaledBitmap(Fimage, tileSize, tileSize, false);
        Gimage = Bitmap.createScaledBitmap(Gimage, tileSize, tileSize, false);
        Himage = Bitmap.createScaledBitmap(Himage, tileSize, tileSize, false);
        Iimage = Bitmap.createScaledBitmap(Iimage, tileSize, tileSize, false);
        Jimage = Bitmap.createScaledBitmap(Jimage, tileSize, tileSize, false);
        Kimage = Bitmap.createScaledBitmap(Kimage, tileSize, tileSize, false);
        Limage = Bitmap.createScaledBitmap(Limage, tileSize, tileSize, false);
        Mimage = Bitmap.createScaledBitmap(Mimage, tileSize, tileSize, false);
        Nimage = Bitmap.createScaledBitmap(Nimage, tileSize, tileSize, false);
        Oimage = Bitmap.createScaledBitmap(Oimage, tileSize, tileSize, false);
        Pimage = Bitmap.createScaledBitmap(Pimage, tileSize, tileSize, false);

        dotImage = Bitmap.createScaledBitmap(dotImage, tileSize, tileSize, false);

        goalImage = Bitmap.createScaledBitmap(goalImage, tileSize, tileSize, false);
        playerImage = Bitmap.createScaledBitmap(playerImage, tileSize, tileSize, false);

    }



    private void nextFrame() {

        //dot mapping
        if(assist){

            tracker[playerX][playerY] = true;

        }

        //map shifting
        if((playerX-cameraX) > 7){

            cameraX += 5;
            cameraXSlide += 5*tileSize;

        }

        if((playerX - cameraX) < 2){

            cameraX -= 5;
            cameraXSlide -= 5*tileSize;

        }


        if((playerY-cameraY) > 7){

            cameraY += 5;
            cameraYSlide += 5*tileSize;

        }

        if((playerY - cameraY) < 2){

            cameraY -= 5;
            cameraYSlide -= 5*tileSize;

        }




        //player sliding
        if(playerXSlide !=0){

            int slideSpeed = tileSize/10;

            if(Math.abs(playerXSlide - slideSpeed) < slideSpeed)
                playerXSlide = 0;
            else if(playerXSlide < 0)
                playerXSlide += slideSpeed;
            else if(playerXSlide > 0)
                playerXSlide -= slideSpeed;

        }

        if(playerYSlide !=0){

            int slideSpeed = tileSize/10;

            if(Math.abs(playerYSlide - slideSpeed) < slideSpeed)
                playerYSlide = 0;
            else if(playerYSlide < 0)
                playerYSlide += slideSpeed;
            else if(playerYSlide > 0)
                playerYSlide -= slideSpeed;

        }



        //maze sliding
        if(cameraXSlide !=0){

            int slideSpeed = tileSize/3;

            if(Math.abs(cameraXSlide - slideSpeed) < slideSpeed)
                cameraXSlide = 0;
            else if(cameraXSlide < 0)
                cameraXSlide += slideSpeed;
            else if(cameraXSlide > 0)
                cameraXSlide -= slideSpeed;

        }

        if(cameraYSlide !=0){

            int slideSpeed = tileSize/3;

            if(Math.abs(cameraYSlide - slideSpeed) < slideSpeed)
                cameraYSlide = 0;
            else if(cameraYSlide < 0)
                cameraYSlide += slideSpeed;
            else if(cameraYSlide > 0)
                cameraYSlide -= slideSpeed;

        }




    }

    @Override
    protected void onDraw(Canvas canvas) {

        TOTAL_HEIGHT = this.getHeight();
        TOTAL_WIDTH = this.getWidth();


        // fill background white
        canvas.drawRGB(255, 255, 255);

        if(loadedMaze){

            int Xstart;
            int Ystart;

            if(cameraY >=0)
                Ystart = cameraY;
            else
                Ystart = 0;

            if(cameraX >=0)
                Xstart = cameraX;
            else
                Xstart = 0;


            //render tiles
            for(int indexH = Ystart; indexH < tiles[0].length  && indexH < cameraY+20; indexH++){

                for(int indexW = Xstart; indexW < tiles.length && indexW < cameraX+20; indexW++) {

                    if(tiles[indexW][indexH] == 'A'){

                        canvas.drawBitmap(Aimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'B'){

                        canvas.drawBitmap(Bimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'C'){

                        canvas.drawBitmap(Cimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'D'){

                        canvas.drawBitmap(Dimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'E'){

                        canvas.drawBitmap(Eimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'F'){

                        canvas.drawBitmap(Fimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'G'){

                        canvas.drawBitmap(Gimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'H'){

                        canvas.drawBitmap(Himage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'I'){

                        canvas.drawBitmap(Iimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'J'){

                        canvas.drawBitmap(Jimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'K'){

                        canvas.drawBitmap(Kimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'L'){

                        canvas.drawBitmap(Limage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'M'){

                        canvas.drawBitmap(Mimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'N'){

                        canvas.drawBitmap(Nimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'O'){

                        canvas.drawBitmap(Oimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }
                    else if(tiles[indexW][indexH] == 'P'){

                        canvas.drawBitmap(Pimage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                    }

                    //add tracking dots
                    if(assist){

                        if(tracker[indexW][indexH]){

                            canvas.drawBitmap(dotImage, (indexW - cameraX)*tileSize + cameraXSlide, (indexH - cameraY)*tileSize + cameraYSlide, null);

                        }

                    }



                }

            }

            //draw the character & goal
            canvas.drawBitmap(playerImage, (playerX-cameraX)*tileSize + playerXSlide + cameraXSlide, (playerY-cameraY)*tileSize + playerYSlide + cameraYSlide, null);
            canvas.drawBitmap(goalImage, ((tiles.length-1)-cameraX)*tileSize + cameraXSlide, ((tiles[0].length-1)-cameraY)*tileSize + cameraYSlide, null);

        }
        else{

            //do nothing since there is no maze yet

        }


        // delay before next frame
        handler.postDelayed(animationThread, SPEED);
        nextFrame();

    }

    public void setTileSize(int size){

        tileSize = size;

        initializeTiles();

    }

    //get maze from activity
    public void setMaze(char given[][]){

        tiles = given;

        loadedMaze = true;

    }

    public void setPlayerLocation(int x, int y){

        playerX = x;
        playerY = y;

    }

    public void slideLeft(){

        playerXSlide += tileSize;

    }

    public void slideRight(){

        playerXSlide -= tileSize;

    }

    public void slideUp(){

        playerYSlide += tileSize;

    }

    public void slideDown(){

        playerYSlide -= tileSize;

    }



    public void setAssistOn(){

        assist = true;

        tracker = new boolean[tiles.length][tiles[0].length];

        for(int indexW = 0; indexW < tracker.length; indexW++){

            for(int indexH = 0; indexH < tracker[0].length; indexH++){

                tracker[indexW][indexH] = false;

            }

        }

    }

    public void setTracker(boolean newTracker[][]){

        tracker = newTracker;

    }


    public boolean[][] getTracker(){

        if(tracker == null)
            return new boolean[0][0];
        else
            return tracker;

    }


    public boolean getAssist(){

        return assist;

    }


}
