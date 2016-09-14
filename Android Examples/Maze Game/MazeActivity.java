package mobileclasstesting.mazegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MazeActivity extends AppCompatActivity {

    private char tiles[][];

    private int playerX;
    private int playerY;

    private int width;
    private int height;

    private String code;

    saveDBhelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        Intent requestIntent = this.getIntent();

        helper = new saveDBhelper(this);

        boolean tracker[][] = null;

        width = Integer.parseInt(requestIntent.getStringExtra("width"));
        height = Integer.parseInt(requestIntent.getStringExtra("height"));

        code = requestIntent.getStringExtra("mazeCode");

        String assistRaw = requestIntent.getStringExtra("assist");

        String source = requestIntent.getStringExtra("source");

        //map was loaded need player x and y and tracker data
        if(source.equals("load")){

            playerX = Integer.parseInt(requestIntent.getStringExtra("playerX"));
            playerY = Integer.parseInt(requestIntent.getStringExtra("playerY"));

            if(assistRaw.equals("true")){

                String trackerRaw = requestIntent.getStringExtra("tracker");

                tracker = new boolean[width][height];

                for(int indexH = 0; indexH < tracker[0].length; indexH++){

                    for(int indexW = 0; indexW < tracker.length; indexW++){

                        if(trackerRaw.charAt((indexH*width) + indexW) == '1')
                            tracker[indexW][indexH] = true;
                        else
                            tracker[indexW][indexH] = false;

                    }

                }



            }

        }
        else{

            playerX = 0;
            playerY = 0;

        }

        tiles = createMap(code, width, height);

        MazeCanvas mazeCanvas = (MazeCanvas)findViewById(R.id.mazeScreen);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int tileSize = displaymetrics.widthPixels/10; // maze should be 10x10 showing tiles

        mazeCanvas.setTileSize(tileSize);

        mazeCanvas.setMaze(tiles);

        if(assistRaw.equals("true")){

            mazeCanvas.setAssistOn();

        }


        if(tracker != null){

            mazeCanvas.setTracker(tracker);

        }


        mazeCanvas.setPlayerLocation(playerX, playerY);


        Button leftButton = (Button)findViewById(R.id.leftButton);
        Button rightButton = (Button)findViewById(R.id.rightButton);
        Button upButton = (Button)findViewById(R.id.upButton);
        Button downButton = (Button)findViewById(R.id.downButton);

        Button saveButton = (Button)findViewById(R.id.saveButton);

        leftButton.setOnClickListener(new leftAction());
        rightButton.setOnClickListener(new rightAction());
        upButton.setOnClickListener(new upAction());
        downButton.setOnClickListener(new downAction());

        saveButton.setOnClickListener(new saveAction());

        setOptions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maze, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class upAction implements View.OnClickListener {

        public void onClick(View view) {

            playerY--;

            MazeCanvas mazeCanvas = (MazeCanvas)findViewById(R.id.mazeScreen);
            mazeCanvas.setPlayerLocation(playerX, playerY);
            mazeCanvas.slideUp();

            setOptions();

            checkVictory();

        }

    }

    public class downAction implements View.OnClickListener {

        public void onClick(View view) {

            playerY++;

            MazeCanvas mazeCanvas = (MazeCanvas)findViewById(R.id.mazeScreen);
            mazeCanvas.setPlayerLocation(playerX, playerY);
            mazeCanvas.slideDown();

            setOptions();

            checkVictory();

        }

    }

    public class leftAction implements View.OnClickListener {

        public void onClick(View view) {

            playerX--;

            MazeCanvas mazeCanvas = (MazeCanvas)findViewById(R.id.mazeScreen);
            mazeCanvas.setPlayerLocation(playerX, playerY);
            mazeCanvas.slideLeft();

            setOptions();

            checkVictory();

        }

    }

    public class rightAction implements View.OnClickListener {

        public void onClick(View view) {

            playerX++;

            MazeCanvas mazeCanvas = (MazeCanvas)findViewById(R.id.mazeScreen);
            mazeCanvas.setPlayerLocation(playerX, playerY);
            mazeCanvas.slideRight();

            setOptions();

            checkVictory();

        }

    }



    public class saveAction implements View.OnClickListener {

        public void onClick(View view) {

            MazeCanvas mazeCanvas = (MazeCanvas)findViewById(R.id.mazeScreen);

            boolean tracker[][] = mazeCanvas.getTracker();
            String trackerRaw = "";

            if(tracker.length > 0) {

                for (int indexH = 0; indexH < tracker[0].length; indexH++) {

                    for (int indexW = 0; indexW < tracker.length; indexW++) {

                        if (tracker[indexW][indexH])
                            trackerRaw += '1';
                        else
                            trackerRaw += '0';

                    }

                }

            }

            boolean assist = mazeCanvas.getAssist();
            String assistRaw = "";

            if(assist)
                assistRaw = "true";
            else
                assistRaw = "false";


            String saveRaw[] = new String[7];

            saveRaw[0] = code;
            saveRaw[1] = trackerRaw;
            saveRaw[2] = assistRaw;
            saveRaw[3] = "" + playerX;
            saveRaw[4] = "" + playerY;
            saveRaw[5] = "" + width;
            saveRaw[6] = "" + height;

            helper.updateRecord(saveRaw);

            //tell the user the file saved
            Toast.makeText(getApplicationContext(), "Game saved", Toast.LENGTH_LONG).show();

        }

    }





    private void setOptions(){

        String options = possibleDirections();
        String nons = nonOptions(options);

        Button leftButton = (Button)findViewById(R.id.leftButton);
        Button rightButton = (Button)findViewById(R.id.rightButton);
        Button upButton = (Button)findViewById(R.id.upButton);
        Button downButton = (Button)findViewById(R.id.downButton);

        for(int index = 0; index < options.length(); index++){

            if(options.charAt(index) == 'L')
                leftButton.setEnabled(true);
            else if(options.charAt(index) == 'R')
                rightButton.setEnabled(true);
            else if(options.charAt(index) == 'U')
                upButton.setEnabled(true);
            else if(options.charAt(index) == 'D')
                downButton.setEnabled(true);

        }

        for(int index = 0; index < nons.length(); index++){

            if(nons.charAt(index) == 'L')
                leftButton.setEnabled(false);
            else if(nons.charAt(index) == 'R')
                rightButton.setEnabled(false);
            else if(nons.charAt(index) == 'U')
                upButton.setEnabled(false);
            else if(nons.charAt(index) == 'D')
                downButton.setEnabled(false);

        }

    }



    private void checkVictory(){

        //player has found the bottom right
        if(playerX == (tiles.length-1) && playerY == (tiles[0].length-1)){

            Intent victoryIntent = new Intent(MazeActivity.this, VictoryActivity.class);

            startActivity(victoryIntent);

            finish();

        }

    }


    ////////////////////////////////////////
    // TOOLS
    ///////////////////////////////////////
    private char[][] createMap(String raw, int width, int height){

        char tiles[][] = new char[width][height];

        for(int indexH = 0; indexH < height; indexH++){

            for(int indexW = 0; indexW < width; indexW++){

                tiles[indexW][indexH] = raw.charAt((width*indexH) + indexW);

            }

        }

        return tiles;

    }

    private String possibleDirections(){

        char tile = tiles[playerX][playerY];

        String result = "";

        switch (tile){

            case 'A': result = "LRUD";
                break;
            case 'B': result = "RUD";
                break;
            case 'C': result = "LUD";
                break;
            case 'D': result = "LRD";
                break;
            case 'E': result = "LRU";
                break;
            case 'F': result = "UD";
                break;
            case 'G': result = "RD";
                break;
            case 'H': result = "RU";
                break;
            case 'I': result = "LD";
                break;
            case 'J': result = "LU";
                break;
            case 'K': result = "LR";
                break;
            case 'L': result = "L";
                break;
            case 'M': result = "R";
                break;
            case 'N': result = "U";
                break;
            case 'O': result = "D";
                break;
            default: result = "";
                break;

        }

        return result;

    }


    private String nonOptions(String givens){

        String nons = "LRUD";

        for(int index = 0; index < givens.length(); index++){

            nons = removeLetter(givens.charAt(index), nons);

        }

        return nons;

    }

    private String removeLetter(char letter, String raw){

        String result = "";

        for(int index = 0; index < raw.length(); index++){

            if(!(raw.charAt(index) == letter))
                result += raw.charAt(index);

        }

        return result;

    }



}
