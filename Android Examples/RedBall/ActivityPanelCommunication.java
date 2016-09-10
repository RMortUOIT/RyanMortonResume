// The panel is the actual screen doing the rendering while the activity is handling everything else
// The panel has two methods which allow the activity to control the state of the ball
// The halt() method allows the activity to stop the ball by setting the speeds to zero and saving their direction
// The unhalt() method allows the activity to tell the panel to resume normal activities
// Note that the activity can also get and set the ball's x and y coords
// These are the methods contained in the panel
public void halt(){

        if(!halted){

            if(xSpeed > 0)
                xPos = true;
            else
                xPos = false;


            if(ySpeed > 0)
                yPos = true;
            else
                yPos = false;


            xSpeed = 0;
            ySpeed = 0;

            halted = true;

        }

    }

    public void unhalt(){

        if(xPos)
            xSpeed = 5;
        else
            xSpeed = -5;


        if(yPos)
            ySpeed = 5;
        else
            ySpeed = -5;

        halted = false;

}



// This method allows the activity to react to a touch event (by the user) and respond accordingly
// If the user has touched the screen where the ball is, the user "grabs" the ball and can control it
// Once the user releases, the activity tells the panel (called GameCanvas) to resume normal movement
@Override
public boolean onTouchEvent(MotionEvent e) {

        //need to get size of background
        BackgroundCanvas GameCanvas = (BackgroundCanvas) findViewById(R.id.backView);
        int CANVAS_WIDTH = GameCanvas.getWidth();
        int CANVAS_HEIGHT = GameCanvas.getHeight();

        int CANVAS_Y = (int) GameCanvas.getY(); // needed because title bar causes vertical offset


        int x = (int) e.getX();
        int y = (int) e.getY();

        int ballX = GameCanvas.getBallX();
        int ballY = GameCanvas.getBallY();

        int totalX = GameCanvas.getTotalWidth();
        int totalY = GameCanvas.getTotalHeight();


        //screen clicked
        if( (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE)&&
                x > ballX  && x < (ballX+100) &&
                y > ballY  && (y + CANVAS_Y) < (ballY+100 + CANVAS_Y)){

                    GameCanvas.halt();

                    GameCanvas.setBallX(x-25);
                    GameCanvas.setBallY(y-25);

        }
        else if(e.getAction() == MotionEvent.ACTION_UP){

            GameCanvas.unhalt();

        }

        return true;
}









