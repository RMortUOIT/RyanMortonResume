#include "D3DGraphics.h"
#include "bot1.h"
#include "weapon.h"
#include <ctime>


bot1::bot1(){

	x = 0;
	y = 0;

	w = 30;
	h = 75;

	isLeft = true;

	throwCount = 0;

	ySpeed = 0;

	strideCount = 0;

	srand ( time(NULL) ); // random shirt colour
	random = D3DCOLOR_XRGB(rand() % 255 + 1,rand() % 255 + 1, rand() % 255 + 1);

}

bot1::bot1(int xStart, int yStart){

	x = xStart;
	y = yStart;

	w = 30;
	h = 75;

	isLeft = true;

	throwCount = 0;

	ySpeed = 0;

	strideCount = 0;

	

}

void bot1::move(){

	if(isLeft){
		x -= BOTSPEED;
		y += ySpeed;
	}
	else{
		x += BOTSPEED;
		y += ySpeed;
	}

	throwCount++;
	if(throwCount == 301)
		throwCount = 0;


	strideCount++;
	if(strideCount == 20)
		strideCount = 0;

}

weapon bot1::throwWeapon(){

	weapon output(x+5, y+26, -3);

	if(!isLeft)
		output.xSpeed = 3;
	
	return output;

}

// add randomize shirt colour function here
void bot1::randomizeColor(){

	 
	random = D3DCOLOR_XRGB(rand() % 255 + 1,rand() % 255 + 1, rand() % 255 + 1);

}