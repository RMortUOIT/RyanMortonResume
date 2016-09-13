#include "weapon.h"
#include "D3DGraphics.h"


weapon::weapon(){

	x = 0;
	y = 0;

	w = 10;
	h = 10;

	xSpeed = 3;

	spinCount = 0;

}


weapon::weapon(int xStart, int yStart, int xStartSpeed){

	x = xStart;
	y = yStart;

	w = 10;
	h = 10;

	xSpeed = xStartSpeed;

	spinCount = 0;

}

void weapon::move(){

	x += xSpeed;

	spinCount++;

	if(spinCount == 40)
		spinCount = 0;


}