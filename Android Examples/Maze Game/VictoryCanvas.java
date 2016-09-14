package mobileclasstesting.mazegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class VictoryCanvas extends View {

    final int SPEED = 10;

    private int TOTAL_WIDTH;
    private int TOTAL_HEIGHT;

    private fireWork fireWorks[];

    private Handler handler;

    private boolean loaded;

    private Bitmap RedLarge;
    private Bitmap BlueLarge;
    private Bitmap GreenLarge;
    private Bitmap YellowLarge;
    private Bitmap PurpleLarge;

    private Bitmap RedSmall;
    private Bitmap BlueSmall;
    private Bitmap GreenSmall;
    private Bitmap YellowSmall;
    private Bitmap PurpleSmall;



    private Runnable animationThread = new Runnable() {
        @Override
        public void run() {
            // redraw
            invalidate();
        }
    };

    public VictoryCanvas(Context context) {
        super(context);

        loaded = false;

        fireWorks = new fireWork[0];

    }

    public VictoryCanvas(Context context, AttributeSet attribs) {
        super(context, attribs);

        loaded = false;

        fireWorks = new fireWork[0];

    }





    private void initializeTiles() {

        handler = new Handler();

        // create bitmap of tiles
        RedLarge = BitmapFactory.decodeResource(getResources(), R.drawable.red);
        BlueLarge = BitmapFactory.decodeResource(getResources(), R.drawable.blue);
        GreenLarge = BitmapFactory.decodeResource(getResources(), R.drawable.green);
        YellowLarge = BitmapFactory.decodeResource(getResources(), R.drawable.yellow);
        PurpleLarge = BitmapFactory.decodeResource(getResources(), R.drawable.purple);


        // scale bitmap of tiles
        RedLarge = Bitmap.createScaledBitmap(RedLarge, TOTAL_WIDTH / 20, TOTAL_WIDTH / 20, false);
        BlueLarge = Bitmap.createScaledBitmap(BlueLarge, TOTAL_WIDTH / 20, TOTAL_WIDTH / 20, false);
        GreenLarge = Bitmap.createScaledBitmap(GreenLarge, TOTAL_WIDTH / 20, TOTAL_WIDTH / 20, false);
        YellowLarge = Bitmap.createScaledBitmap(YellowLarge, TOTAL_WIDTH / 20, TOTAL_WIDTH / 20, false);
        PurpleLarge = Bitmap.createScaledBitmap(PurpleLarge, TOTAL_WIDTH / 20, TOTAL_WIDTH / 20, false);

        RedSmall = Bitmap.createScaledBitmap(RedLarge, TOTAL_WIDTH / 40, TOTAL_WIDTH / 40, false);
        BlueSmall = Bitmap.createScaledBitmap(BlueLarge, TOTAL_WIDTH / 40, TOTAL_WIDTH / 40, false);
        GreenSmall = Bitmap.createScaledBitmap(GreenLarge, TOTAL_WIDTH / 40, TOTAL_WIDTH / 40, false);
        YellowSmall = Bitmap.createScaledBitmap(YellowLarge, TOTAL_WIDTH / 40, TOTAL_WIDTH / 40, false);
        PurpleSmall = Bitmap.createScaledBitmap(PurpleLarge, TOTAL_WIDTH / 40, TOTAL_WIDTH / 40, false);

    }



    private void nextFrame() {

        //firework creation
        if(loaded){



            if(fireWorks.length < 10) {
                addRocket();

                Log.i("GAME ACTION", "ADDED FIREWORK");

            }

            //gotta take care here because length changes inside for loop
            for(int index = 0; index < fireWorks.length; index++){

                fireWorks[index].move();

                //firework went out
                if(!fireWorks[index].isAlive()){

                    if(fireWorks[index].isRocket()){

                        burst(fireWorks[index].getX(), fireWorks[index].getY());

                    }

                    fireWorks = removeFireWork(fireWorks, index);
                    Log.i("GAME ACTION", "REMOVED FIREWORK, COUNT = " + fireWorks.length);
                    index--; //need this to keep from skipping next firework

                }

            }

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(loaded){

            for(int index = 0; index < fireWorks.length; index++){

                if(fireWorks[index].isRocket()){

                    switch(fireWorks[index].getColour()){

                        case "red":
                            canvas.drawBitmap(RedSmall, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;
                        case "blue":
                            canvas.drawBitmap(BlueSmall, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;
                        case "green":
                            canvas.drawBitmap(GreenSmall, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;
                        case "yellow":
                            canvas.drawBitmap(YellowSmall, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;
                        case "purple":
                            canvas.drawBitmap(PurpleSmall, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;

                    }

                }
                else{

                    switch(fireWorks[index].getColour()){

                        case "red":
                            canvas.drawBitmap(RedLarge, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;
                        case "blue":
                            canvas.drawBitmap(BlueLarge, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;
                        case "green":
                            canvas.drawBitmap(GreenLarge, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;
                        case "yellow":
                            canvas.drawBitmap(YellowLarge, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;
                        case "purple":
                            canvas.drawBitmap(PurpleLarge, fireWorks[index].getX(), fireWorks[index].getY(), null);
                            break;

                    }

                }

            }

        }

        // delay before next frame
        handler.postDelayed(animationThread, SPEED);
        nextFrame();

    }


    //create a starburst
    private void burst(int x, int y){

        Log.i("GAME ACTION", "BURST");

        fireWork burst1 = new fireWork(false, ((int)(Math.random()*40 - 20)), ((int)(Math.random()*20 - 10)), x, y, ((int)(Math.random()*150)), "red");
        fireWork burst2 = new fireWork(false, ((int)(Math.random()*40 - 20)), ((int)(Math.random()*20 - 10)), x, y, ((int)(Math.random()*150)), "blue");
        fireWork burst3 = new fireWork(false, ((int)(Math.random()*40 - 20)), ((int)(Math.random()*20 - 10)), x, y, ((int)(Math.random()*150)), "green");
        fireWork burst4 = new fireWork(false, ((int)(Math.random()*40 - 20)), ((int)(Math.random()*20 - 10)), x, y, ((int)(Math.random()*150)), "yellow");
        fireWork burst5 = new fireWork(false, ((int)(Math.random()*40 - 20)), ((int)(Math.random()*20 - 10)), x, y, ((int)(Math.random()*150)), "purple");

        fireWorks = addFireWork(fireWorks, burst1);
        fireWorks = addFireWork(fireWorks, burst2);
        fireWorks = addFireWork(fireWorks, burst3);
        fireWorks = addFireWork(fireWorks, burst4);
        fireWorks = addFireWork(fireWorks, burst5);

    }



    private void addRocket(){

        int xPos = (int)(Math.random()*TOTAL_WIDTH);
        int lifeSpan = (int)(Math.random()*(TOTAL_HEIGHT/20));

        String colour = "red";
        int colourCode = (int)(Math.random()*5);

        switch(colourCode){

            case 0: colour = "red";
                break;
            case 1: colour = "blue";
                break;
            case 2: colour = "green";
                break;
            case 3: colour = "purple";
                break;
            case 4: colour = "yellow";
                break;

        }

        fireWork raw = new fireWork(true, 0, -20, xPos, TOTAL_HEIGHT-50, lifeSpan, colour);

        fireWorks = addFireWork(fireWorks, raw);

    }







    public void setDemensions(int w, int h){

        TOTAL_HEIGHT = h;
        TOTAL_WIDTH = w;

        initializeTiles();

        loaded = true;

    }








    /////////////////////////////////
    // Firework related tools
    ///////////////////////////////
    public fireWork[] removeFireWork(fireWork[] old, int location){

        fireWork result[] = new fireWork[old.length-1];

        for(int index = 0; index < old.length; index++){

            if(index < location){

                result[index] = old[index];

            }
            else if(index > location){

                result[index-1] = old[index];

            }

        }

        return result;

    }


    public fireWork[] addFireWork(fireWork[] old, fireWork newFW){

        fireWork result[] = new fireWork[old.length+1];

        for(int index = 0; index < old.length; index++){

            result[index] = old[index];

        }

        result[old.length] = newFW;

        return result;

    }



    //firework object
    private static class fireWork{

        private int xS;
        private int yS;

        private int x;
        private int y;

        private int lifeSpan;
        private boolean alive;

        private String colour;

        private boolean rocket;

        public fireWork(boolean makeRocket, int xSpeed, int ySpeed, int xPos, int yPos, int LS, String c){

            xS = xSpeed;
            yS = ySpeed;

            x = xPos;
            y = yPos;

            lifeSpan = LS;

            rocket = makeRocket;

            colour = c;

            alive = true;

        }

        private String getColour(){

            return colour;

        }

        boolean isRocket(){

            return rocket;

        }

        boolean isAlive(){

            return alive;

        }


        private void move(){

            x += xS;
            y += yS;

            lifeSpan--;

            if(lifeSpan == 0)
                alive = false;

            if(!rocket)
                yS++;

        }

        public int getX(){

            return x;

        }

        public int getY(){

            return y;

        }

    }





}
