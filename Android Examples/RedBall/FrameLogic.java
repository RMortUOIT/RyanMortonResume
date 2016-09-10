//This is the frame-by-frame logic that moves the ball around the screen
//Every frame the ball's x and y coordinates are incremented by the respective speeds
//If the ball makes contact with any edge of the screen, the appropriate speed is inverted
//Note that if the ball is currently being "grabbed" by the user both speeds are 0
private void nextFrame() {

        if(ballX >= (TOTAL_WIDTH-50) || ballX <= 0){

            xSpeed *= -1;

        }

        if(ballY >= (TOTAL_HEIGHT-50) || ballY <= 0){

            ySpeed *= -1;

        }



        if(ballX <= -5)
            ballX += 100;

        if(ballX >= TOTAL_WIDTH-45)
            ballX -= 100;


        if(ballY <= -5)
            ballY += 100;

        if(ballY >= TOTAL_HEIGHT-45)
            ballY -= 100;



        ballX += xSpeed;
        ballY += ySpeed;


}