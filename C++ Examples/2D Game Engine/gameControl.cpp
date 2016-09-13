#include "gameControl.h"
#include <cmath>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <iostream>
#include <fstream>
#include <stdio.h>
#include "bot1.h"

using namespace std;

gameControl::gameControl( HWND hwnd, const KeyboardServer& kServer )
:
	gfx( hwnd , 800, 600),
    kbd( kServer ),
	//b1(){}, DONT NEED
	//b1W(){},
	gameState(0),
	gameStateCounter(0)
	

{

	

	//TODO generalize

	char tile;


	int count(0);

	//map 1
	fstream fin1("maps\\map1.txt", fstream::in);
	while (fin1 >> noskipws >> tile) {
		cout << tile; 

		
		map1[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin1.close();

	//map 2
	count = 0;
	fstream fin2("maps\\map2.txt", fstream::in);
	while (fin2 >> noskipws >> tile) {
		cout << tile; 

		
		map2[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin2.close();

	//map 3
	count = 0;
	fstream fin3("maps\\map3.txt", fstream::in);
	while (fin3 >> noskipws >> tile) {
		cout << tile; 

		
		map3[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin3.close();

	//map 4
	count = 0;
	fstream fin4("maps\\map4.txt", fstream::in);
	while (fin4 >> noskipws >> tile) {
		cout << tile; 

		
		map4[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin4.close();

	//map 5
	count = 0;
	fstream fin5("maps\\map5.txt", fstream::in);
	while (fin5 >> noskipws >> tile) {
		cout << tile; 

		
		map5[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}
	
	//map 6
	count = 0;
	fstream fin6("maps\\map6.txt", fstream::in);
	while (fin6 >> noskipws >> tile) {
		cout << tile; 

		
		map6[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin6.close();

	//map 7
	count = 0;
	fstream fin7("maps\\map7.txt", fstream::in);
	while (fin7 >> noskipws >> tile) {
		cout << tile; 

		
		map7[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin7.close();
	
	//map 8
	count = 0;
	fstream fin8("maps\\map8.txt", fstream::in);
	while (fin8 >> noskipws >> tile) {
		cout << tile; 

		
		map8[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin8.close();

	//map 9
	count = 0;
	fstream fin9("maps\\map9.txt", fstream::in);
	while (fin9 >> noskipws >> tile) {
		cout << tile; 

		
		map9[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin9.close();

	//map 10
	count = 0;
	fstream fin10("maps\\map10.txt", fstream::in);
	while (fin10 >> noskipws >> tile) {
		cout << tile; 

		
		map10[count%129][count/129] = tile;   // 129 = WIDTH OF MAP + 1
		

		count++;

	}

	fin10.close();

	// collision matrix initialize
	for(int index1 = 0; index1<128; index1++){

		for(int index2 = 0; index2<12; index2++){

			
			map1CollisionAreas[index1][index2] = false;
			map2CollisionAreas[index1][index2] = false;
			map3CollisionAreas[index1][index2] = false;
			map4CollisionAreas[index1][index2] = false;
			map5CollisionAreas[index1][index2] = false;
			map6CollisionAreas[index1][index2] = false;
			map7CollisionAreas[index1][index2] = false;
			map8CollisionAreas[index1][index2] = false;
			map9CollisionAreas[index1][index2] = false;
			map10CollisionAreas[index1][index2] = false;

		}

	}



	// find collisions for all maps
	for(int index1 = 0; index1<128; index1++){

		for(int index2 = 0; index2<12; index2++){

			if(map1[index1][index2] == 'A' || map1[index1][index2] == 'B'  || map1[index1][index2] == 'C' || map1[index1][index2] == 'D' || map1[index1][index2] == 'M'  || map1[index1][index2] == 'N' || map1[index1][index2] == 'W' || map1[index1][index2] == 'X'  || map1[index1][index2] == 'Y' || map1[index1][index2] == 'Z'){

				map1CollisionAreas[index1][index2] = true;

			}
			if(map2[index1][index2] == 'A' || map2[index1][index2] == 'B'  || map2[index1][index2] == 'C' || map2[index1][index2] == 'D' || map2[index1][index2] == 'M'  || map2[index1][index2] == 'N' || map2[index1][index2] == 'W' || map2[index1][index2] == 'X'  || map2[index1][index2] == 'Y' || map2[index1][index2] == 'Z'){

				map2CollisionAreas[index1][index2] = true;

			}
			if(map3[index1][index2] == 'A' || map3[index1][index2] == 'B'  || map3[index1][index2] == 'C' || map3[index1][index2] == 'D' || map3[index1][index2] == 'M'  || map3[index1][index2] == 'N' || map3[index1][index2] == 'W' || map3[index1][index2] == 'X'  || map3[index1][index2] == 'Y' || map3[index1][index2] == 'Z'){

				map3CollisionAreas[index1][index2] = true;

			}
			if(map4[index1][index2] == 'A' || map4[index1][index2] == 'B'  || map4[index1][index2] == 'C' || map4[index1][index2] == 'D' || map4[index1][index2] == 'M'  || map4[index1][index2] == 'N' || map4[index1][index2] == 'W' || map4[index1][index2] == 'X'  || map4[index1][index2] == 'Y' || map4[index1][index2] == 'Z'){

				map4CollisionAreas[index1][index2] = true;

			}
			if(map5[index1][index2] == 'A' || map5[index1][index2] == 'B'  || map5[index1][index2] == 'C' || map5[index1][index2] == 'D' || map5[index1][index2] == 'M'  || map5[index1][index2] == 'N' || map5[index1][index2] == 'W' || map5[index1][index2] == 'X'  || map5[index1][index2] == 'Y' || map5[index1][index2] == 'Z'){

				map5CollisionAreas[index1][index2] = true;

			}
			if(map6[index1][index2] == 'A' || map6[index1][index2] == 'B'  || map6[index1][index2] == 'C' || map6[index1][index2] == 'D' || map6[index1][index2] == 'M'  || map6[index1][index2] == 'N' || map6[index1][index2] == 'W' || map6[index1][index2] == 'X'  || map6[index1][index2] == 'Y' || map6[index1][index2] == 'Z'){

				map6CollisionAreas[index1][index2] = true;

			}
			if(map7[index1][index2] == 'A' || map7[index1][index2] == 'B'  || map7[index1][index2] == 'C' || map7[index1][index2] == 'D' || map7[index1][index2] == 'M'  || map7[index1][index2] == 'N' || map7[index1][index2] == 'W' || map7[index1][index2] == 'X'  || map7[index1][index2] == 'Y' || map7[index1][index2] == 'Z'){

				map7CollisionAreas[index1][index2] = true;

			}
			if(map8[index1][index2] == 'A' || map8[index1][index2] == 'B'  || map8[index1][index2] == 'C' || map8[index1][index2] == 'D' || map8[index1][index2] == 'M'  || map8[index1][index2] == 'N' || map8[index1][index2] == 'W' || map8[index1][index2] == 'X'  || map8[index1][index2] == 'Y' || map8[index1][index2] == 'Z'){

				map8CollisionAreas[index1][index2] = true;

			}
			if(map9[index1][index2] == 'A' || map9[index1][index2] == 'B'  || map9[index1][index2] == 'C' || map9[index1][index2] == 'D' || map9[index1][index2] == 'M'  || map9[index1][index2] == 'N' || map9[index1][index2] == 'W' || map9[index1][index2] == 'X'  || map9[index1][index2] == 'Y' || map9[index1][index2] == 'Z'){

				map9CollisionAreas[index1][index2] = true;

			}
			if(map10[index1][index2] == 'A' || map10[index1][index2] == 'B'  || map10[index1][index2] == 'C' || map10[index1][index2] == 'D' || map10[index1][index2] == 'M'  || map10[index1][index2] == 'N' || map10[index1][index2] == 'W' || map10[index1][index2] == 'X'  || map10[index1][index2] == 'Y' || map10[index1][index2] == 'Z'){

				map10CollisionAreas[index1][index2] = true;

			}
		}

	}
	
	// randomize colours
	for(int index = 0; index<20; index++){

		b1[index].randomizeColor();

	}

	//sprite uploads here
	LoadSprite(&aTile, "tiles\\Atile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));
	LoadSprite(&bTile, "tiles\\Btile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));//ugly pink key
	LoadSprite(&cTile, "tiles\\Ctile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));
	LoadSprite(&dTile, "tiles\\Dtile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));
	LoadSprite(&mTile, "tiles\\Mtile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));
	LoadSprite(&nTile, "tiles\\Ntile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));
	LoadSprite(&wTile, "tiles\\Wtile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));
	LoadSprite(&xTile, "tiles\\Xtile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));
	LoadSprite(&yTile, "tiles\\Ytile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));
	LoadSprite(&zTile, "tiles\\Ztile.bmp", 50, 50, D3DCOLOR_XRGB(255, 174, 201));


	// craig
	LoadSprite(&craigIDLE, "characters\\craigIDLE.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&craigRUN1, "characters\\craigRUN1.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&craigRUN2, "characters\\craigRUN2.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&craigIDLEleft, "characters\\craigIDLEleft.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&craigRUN1left, "characters\\craigRUN1left.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&craigRUN2left, "characters\\craigRUN2left.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key


	// dave
	LoadSprite(&daveIDLE, "characters\\daveIDLE.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&daveRUN1, "characters\\daveRUN1.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&daveRUN2, "characters\\daveRUN2.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&daveIDLEleft, "characters\\daveIDLEleft.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&daveRUN1left, "characters\\daveRUN1left.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&daveRUN2left, "characters\\daveRUN2left.bmp", 30, 75, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key

	//bottle
	LoadSprite(&bottleUP, "misc\\bottleUP.bmp", 10, 10, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&bottleLT, "misc\\bottleLT.bmp", 10, 10, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&bottleDN, "misc\\bottleDN.bmp", 10, 10, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&bottleRT, "misc\\bottleRT.bmp", 10, 10, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	
	//hp symbol
	LoadSprite(&hpSym, "misc\\hp.bmp", 50, 50, D3DCOLOR_XRGB(255, 255, 255)); //white key

	//victory door
	LoadSprite(&bathroomDoor, "misc\\bathroomDoor.bmp", 50, 100, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key

	//MENU SPRITES	
	LoadSprite(&STsplash, "Menu\\STsplash.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&mainMenu, "Menu\\mainMenu.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&loseSplash, "Menu\\loseSplash.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level1, "Menu\\level1.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level2, "Menu\\level2.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level3, "Menu\\level3.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level4, "Menu\\level4.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level5, "Menu\\level5.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level6, "Menu\\level6.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level7, "Menu\\level7.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level8, "Menu\\level8.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level9, "Menu\\level9.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&level10, "Menu\\level10.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&victory, "Menu\\victory.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&tutorial, "Menu\\tutorial.bmp", 800, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key

	// level background sprites
	LoadSprite(&background1, "maps\\background1.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background2, "maps\\background2.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background3, "maps\\background3.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background4, "maps\\background4.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background5, "maps\\background5.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background6, "maps\\background6.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background7, "maps\\background7.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background8, "maps\\background8.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background9, "maps\\background9.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key
	LoadSprite(&background10, "maps\\background10.bmp", 3600, 600, D3DCOLOR_XRGB(255, 174, 201)); //ugly pink key

}


gameControl::~gameControl(){

	// free any sprites with freeSprite(&name)
	freeSprite(&xTile);
	freeSprite(&craigIDLE);
	freeSprite(&craigRUN1);
	freeSprite(&craigRUN2);
	freeSprite(&craigIDLEleft);
	freeSprite(&craigRUN1left);
	freeSprite(&craigRUN2left);
	freeSprite(&daveIDLE);
	freeSprite(&daveRUN1);
	freeSprite(&daveRUN2);
	freeSprite(&daveIDLEleft);
	freeSprite(&daveRUN1left);
	freeSprite(&daveRUN2left);
	freeSprite(&bottleUP);
	freeSprite(&bottleRT);
	freeSprite(&bottleDN);
	freeSprite(&bottleLT);
	freeSprite(&STsplash);
	freeSprite(&mainMenu);
	freeSprite(&bathroomDoor);
	freeSprite(&loseSplash);
	freeSprite(&level1);
	freeSprite(&level2);
	freeSprite(&level3);
	freeSprite(&level4);
	freeSprite(&level5);
	freeSprite(&level6);
	freeSprite(&level7);
	freeSprite(&level8);
	freeSprite(&level9);
	freeSprite(&level10);
	freeSprite(&victory);
	freeSprite(&background1);
	freeSprite(&background2);
	freeSprite(&background3);
	freeSprite(&background4);
	freeSprite(&background5);
	freeSprite(&background6);
	freeSprite(&background7);
	freeSprite(&background8);
	freeSprite(&background9);
	freeSprite(&background10);
	freeSprite(&tutorial);
	freeSprite(&hpSym);

}




void gameControl::createSpriteInstance( AnimatedSpriteTemplate* templat, AnimatedSpriteInstance* instance){

	instance->templat = templat;
	instance->currentFrameExposure = 0;
	instance->currentFrame = 0;
	instance->x = 0.0f;
	instance->y = 0.0f;



}

void gameControl::updateAnimation(AnimatedSpriteInstance* instance){
	
	instance->currentFrameExposure++;
	if( instance->currentFrameExposure >= instance->templat->frameDuration){

		instance->currentFrame++;
		instance->currentFrame %= instance->templat->nFrames;

		instance->currentFrameExposure = 0;


	}

}

void gameControl::drawSpriteInstance(AnimatedSpriteInstance* instance){

	gfx.DrawSprite((int)instance->x, (int)instance->y, &instance->templat->frames[instance->currentFrame]   );


}











void gameControl::nextFrame()
{

	gfx.beginFrame();
	composeFrame();
	gfx.endFrame();
}


void gameControl::composeFrame(){

	if(gameState == 0){ // intro splash

		gfx.DrawSprite(0, 0, &STsplash);

		gameStateCounter++;
		if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

			gameState = 1; // CHANGES TO IN GAME

			gameStateCounter = 0;

			menuState = 0; // sets the menu to default
		
		}


	}

	else if(gameState == 1){ // menu

		gfx.DrawSprite(0, 0, &mainMenu);

		if(gameStateCounter == 0)// menu audio
			PlaySound(TEXT("audio\\menuTheme.wav"), NULL, SND_ASYNC | SND_LOOP);

		drawBrackets(menuState);

		// menu control
		if(kbd.UpIsPressed() && gameStateCounter == 30){

			gameStateCounter = 0;

			if(menuState != 0) // 0 is currently MIN STATE
				menuState--;

		}
		else if(kbd.DownIsPressed() && gameStateCounter == 30){

			gameStateCounter = 0;

			if(menuState != 2)  // 2 is currently MAX STATE
				menuState++;

		}

		if(gameStateCounter < 30)
			gameStateCounter++;


		// selection
		if(kbd.EnterIsPressed() && gameStateCounter == 30){

			gameStateCounter = 0;

			if(menuState == 0){   // START GAME

				gameState = 4;
				level = 1;


			}
			else if(menuState == 1){

				gameState = 5;

			}
			else if(menuState == 2){

				PostQuitMessage(0);

			}
		}
	}

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	//
	//         START OF IN GAME ACTIONS
	//
	//  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

	else if(gameState == 2){

		if(gameStateCounter == 0)
			PlaySound(TEXT("audio\\inGameTheme.wav"), NULL, SND_ASYNC | SND_LOOP);

		if (gameStateCounter < 1)
			gameStateCounter++;

		xPlayerSpeed = 0; 
		hit = false;

		xBackground = xCamera/2;

		if(xBackground < 0)
			xBackground = 0;
		if(xBackground > 2800)
			xBackground = 2800;

		//render background CHANGE TO INCORPERATE DIFFERENT LEVEL BACKGROUNDS
		switch (level){

		case 1:
			gfx.DrawSprite(xBackground - xCamera, 0, &background1);
			break;
		case 2:
			gfx.DrawSprite(xBackground - xCamera, 0, &background2);
			break;
		case 3:
			gfx.DrawSprite(xBackground - xCamera, 0, &background3);
			break;
		case 4:
			gfx.DrawSprite(xBackground - xCamera, 0, &background4);
			break;
		case 5:
			gfx.DrawSprite(xBackground - xCamera, 0, &background5);
			break;
		case 6:
			gfx.DrawSprite(xBackground - xCamera, 0, &background6);
			break;
		case 7:
			gfx.DrawSprite(xBackground - xCamera, 0, &background7);
			break;
		case 8:
			gfx.DrawSprite(xBackground - xCamera, 0, &background8);
			break;
		case 9:
			gfx.DrawSprite(xBackground - xCamera, 0, &background9);
			break;
		case 10:
			gfx.DrawSprite(xBackground - xCamera, 0, &background10);
			break;
		}

		//render tiles that are behind player
		for(int index1 = 0; index1<128; index1++){

			for(int index2 = 0; index2<12; index2++){

				switch(currentMap[index1][index2]){
				case 'A':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &aTile);
					break;
				case 'B':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &bTile);
					break;
				case 'C':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &cTile);
					break;
				case 'D':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &dTile);
					break;
				case 'M':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &mTile);
					break;
				case 'N':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &nTile);
					break;
				case 'W':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &wTile);
					break;
				case 'X':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &xTile);
					break;
				case 'Y':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &yTile);
					break;
				case 'Z':
					gfx.DrawSprite(index1*50 - xCamera, index2*50, &zTile);
					break;

				}

			}

		}

		//render victory door
		gfx.DrawSprite(xBDoor - xCamera, yBDoor, &bathroomDoor);
	

		if(yPlayerSpeed < 5 )
			yPlayerSpeed++; // gravity


		boolean playerOnFloor = false;
		boolean collisionRight = false;
		boolean collisionLeft = false;

		// player space reader
		for(int index1 = 0; index1<128; index1++){

			for(int index2 = 0; index2<12; index2++){
	
				if(currentMapCollisionAreas[index1][index2]){

					int colMid = 0;

					colMid = rectCollide(xPlayer, yPlayer, wPlayer, hPlayer, index1*50 - xCamera, index2*50, 50, 50);

					if(colMid == 1){ // horizontal collision
						if(yPlayerSpeed > 0){
				
							y -= yPlayerSpeed;
							yPlayerSpeed = 0;

						}

						playerOnFloor = true;
				
					
					}
					if(colMid == 2){ // player hits ceiling
						if(yPlayerSpeed < 0){
							y -= yPlayerSpeed;
							yPlayerSpeed = 0;
						}

					}
					if(colMid == 3){  // vertical collision
					
						collisionLeft = true;

					}
					if(colMid == 4){
					
						collisionRight = true;

					}

				}

			}

		}



		if(kbd.UpIsPressed()){ //jump
			if(playerOnFloor)
				yPlayerSpeed = -20;


		}
		if(kbd.DownIsPressed()){

			// crouch animation

		}
		if(kbd.LeftIsPressed()){
			if(!collisionLeft){
				
				if(xPlayer > 200)
					xPlayerSpeed = -3;
				else
					xCamera -=3;
			
			}
			strideCount++;

			if(strideCount == 30){

				strideCount = 10;

			}

			isLeft = true;

		}
	
		if(kbd.RightIsPressed()){

			if(!collisionRight){
				
				if(xPlayer < 600)
					xPlayerSpeed = 3;
				else
					xCamera += 3;

			}
			strideCount++;

			if(strideCount == 30){

				strideCount = 10;

			}

			isLeft = false;

		}
		if(!kbd.RightIsPressed() && !kbd.LeftIsPressed())
			strideCount = 0;

		xPlayer += xPlayerSpeed; 
		yPlayer += yPlayerSpeed; 

		//character render
		if(isLeft){
			if(strideCount >= 0 && strideCount <= 10)
				gfx.DrawSprite(xPlayer, yPlayer, &craigIDLEleft);
			else if(strideCount > 10 && strideCount <= 20)
				gfx.DrawSprite(xPlayer, yPlayer, &craigRUN1left);
			else if(strideCount > 20 && strideCount <= 30)
				gfx.DrawSprite(xPlayer, yPlayer, &craigRUN2left);
		}
		else{
			if(strideCount >= 0 && strideCount <= 10)
				gfx.DrawSprite(xPlayer, yPlayer, &craigIDLE);
			else if(strideCount > 10 && strideCount <= 20)
				gfx.DrawSprite(xPlayer, yPlayer, &craigRUN1);
			else if(strideCount > 20 && strideCount <= 30)
				gfx.DrawSprite(xPlayer, yPlayer, &craigRUN2);
		}



		// bot control ADD 1 LOOP TO GENERALIZE

		for(int index = 0; index < 20; index++){
			if(b1[index].ySpeed < 5) // bot gravity
				b1[index].ySpeed++;


			for(int index1 = 0; index1<128; index1++){
	
				for(int index2 = 0; index2<12; index2++){
	
					if(currentMapCollisionAreas[index1][index2]){

						int colMid = 0;

						colMid = rectCollide(b1[index].x, b1[index].y, b1[index].w, b1[index].h, index1*50, index2*50, 50, 50); // -xCamera???

						if(colMid == 1){ // horizontal collision
							if(b1[index].ySpeed > 0){
				
								//b1.y -= b1.ySpeed;
								b1[index].ySpeed = 0;

							}
				
					
						}
						if(colMid == 2){ // player hits ceiling
							if(b1[index].ySpeed < 0){
								b1[index].y -= b1[index].ySpeed;
								b1[index].ySpeed = 0;
							}

						}
						if(colMid == 3){  // vertical collision
					
						b1[index].isLeft = false;

						}
						if(colMid == 4){
					
							b1[index].isLeft = true;

						}

					}

				}

			}

		}

		// b1 weapon control and movement

		for(int index = 0; index < 20; index++){
			if(b1[index].throwCount == 300)
				b1W[index] = b1[index].throwWeapon();

			b1[index].move();
			b1W[index].move();

			if(b1[index].isLeft){
				if(b1[index].strideCount < 10)
					gfx.DrawSprite(b1[index].x - xCamera, b1[index].y, &daveRUN1left, D3DCOLOR_XRGB(34, 177, 76), b1[index].random);
				else
					gfx.DrawSprite(b1[index].x - xCamera, b1[index].y, &daveRUN2left, D3DCOLOR_XRGB(34, 177, 76), b1[index].random);
			}
			else{
				if(b1[index].strideCount < 10)
					gfx.DrawSprite(b1[index].x - xCamera, b1[index].y, &daveRUN1, D3DCOLOR_XRGB(34, 177, 76), b1[index].random);
				else
					gfx.DrawSprite(b1[index].x - xCamera, b1[index].y, &daveRUN2, D3DCOLOR_XRGB(34, 177, 76), b1[index].random);
			}
	
		}

		// bot weapon rendering
		for(int index = 0; index < 20; index++){
			if(b1W[index].spinCount < 10)
				gfx.DrawSprite(b1W[index].x - xCamera, b1W[index].y, &bottleUP);
			else if(b1W[index].spinCount >= 10 && b1W[index].spinCount < 20)
				gfx.DrawSprite(b1W[index].x - xCamera, b1W[index].y, &bottleRT);
			else if(b1W[index].spinCount >= 20 && b1W[index].spinCount < 30)
				gfx.DrawSprite(b1W[index].x - xCamera, b1W[index].y, &bottleDN);
			else if(b1W[index].spinCount >= 30 && b1W[index].spinCount <= 40)
				gfx.DrawSprite(b1W[index].x - xCamera, b1W[index].y, &bottleLT);

		}

		// check player/bot collisions
		for(int index = 0; index < 20; index++){
			if(rectCollide(b1[index].x - xCamera, b1[index].y, b1[index].w, b1[index].h, xPlayer, yPlayer, wPlayer, hPlayer) != 0 || rectCollide(b1W[index].x - xCamera, b1W[index].y, b1W[index].w, b1W[index].h, xPlayer, yPlayer, wPlayer, hPlayer) != 0){
		
				if(hitCount == 0)
					hit = true;


			}
		}
		if(hit){
			//gfx.drawRect(50, 50, 50, 50, D3DCOLOR_XRGB(0, 255, 0)); // for testing

			hitCount = 1;

			hp--;

		}

		if(hitCount != 0)
			hitCount++;
		if(hitCount == 120)
			hitCount = 0;



		drawHealthBar(hp);

		if(hp == 0 || yPlayer > 900){ // no hp or player fell to death, inst lose
			gameState = 3;
			gameStateCounter = 0;
		}

		//check for victory
		int winCheck(0);

		winCheck = rectCollide(xPlayer, yPlayer, wPlayer, hPlayer, xBDoor - xCamera, yBDoor, 50, 100);

		// ADD MORE STATES HERE
		// RESET ALL GAME VALUES FOR LEVEL 2
		
		
		if(winCheck !=0){
				
			gameState = 4;
			gameStateCounter = 0;
			level++;

		}


	}

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	//
	//         END OF IN GAME ACTIONS
	//
	//  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX


	else if(gameState == 3){ // lose splash

		PlaySound(NULL, NULL, SND_SYNC); // stops sound

		gfx.DrawSprite(0, 0, &loseSplash);

		gameStateCounter++;

		if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

			gameState = 1; // CHANGES TO IN GAME

			gameStateCounter = 0;

			menuState = 0; // sets the menu to default
		
		}



	}

	else if(gameState == 4){

		// randomize colours HAPPENS FOR ALL LEVELS

		if(gameStateCounter == 0)
			PlaySound(NULL, NULL, SND_SYNC); // stops sound


		for(int index = 0; index<20; index++){

			b1[index].randomizeColor();

		}




		if(level == 1){

			gfx.DrawSprite(0, 0, &level1);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;


				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 1
				setMap(map1);
				setCollision(map1CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}

			}


		}
		else if( level == 2){

			gfx.DrawSprite(0, 0, &level2);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 2
				setMap(map2);
				setCollision(map2CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}
		else if( level == 3){

			gfx.DrawSprite(0, 0, &level3);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 3
				setMap(map3);
				setCollision(map3CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}

		else if( level == 4){

			gfx.DrawSprite(0, 0, &level4);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 4
				setMap(map4);
				setCollision(map4CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}

		else if( level == 5){

			gfx.DrawSprite(0, 0, &level5);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 5
				setMap(map5);
				setCollision(map5CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}

		else if( level == 6){

			gfx.DrawSprite(0, 0, &level6);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 6
				setMap(map6);
				setCollision(map6CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}
		else if( level == 7){

			gfx.DrawSprite(0, 0, &level7);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 7
				setMap(map7);
				setCollision(map7CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}

		else if( level == 8){

			gfx.DrawSprite(0, 0, &level8);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 8
				setMap(map8);
				setCollision(map8CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}

		else if( level == 9){

			gfx.DrawSprite(0, 0, &level9);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 9
				setMap(map9);
				setCollision(map9CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}

		else if( level == 10){

			gfx.DrawSprite(0, 0, &level10);

			gameStateCounter++;
			if(gameStateCounter == 300){  //CHANGE THIS NUMBER TO CONTROL TIME OF SPLASH

				gameState = 2; // CHANGES TO IN GAME
	
				gameStateCounter = 0;
			
			
			
				x = 80;
				y = 400;
				xspeed = 1;
				yspeed = 1;
				xPlayer = 80;
				yPlayer = 400;
				wPlayer = 30;
				hPlayer = 75;
				xPlayerSpeed = 0;
				yPlayerSpeed = 0;
				strideCount = 0;
				isLeft = false;
				hit = false;
				hitCount = 1;
				hp = 5;

				xBDoor = 6250;
				yBDoor = 450;

				xCamera = 0;

				// map 10
				setMap(map10);
				setCollision(map10CollisionAreas);

				// dynamically set bot spawn locations
				for(int index = 0; index < 20; index++){
					
					if(index == 1){
						b1[index].x = 400; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else if(index == 2){
						b1[index].x = 700; //spaces bots evenly
						b1[index].y = 275; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
					else{
						b1[index].x = b1[index-1].x + 300; //spaces bots evenly
						b1[index].y = -100; // spawns bots above map, they fall into the map
						b1W[index].x = b1[index].x + 5; // b1.x + 5
						b1W[index].y = b1[index].y + 25; // b1.y + 25
					}
				}
			}


		}

		else if(level == 11){ // victory achieved

			gfx.DrawSprite(0, 0, &victory);

			if(gameStateCounter == 5)
				PlaySound(TEXT("audio\\victory.wav"), NULL, SND_ASYNC | SND_LOOP);

			if(gameStateCounter < 6)
				gameStateCounter++;

			if(kbd.EnterIsPressed()){
				gameState = 1;
				menuState = 0;
				gameStateCounter = 0;
			}

		}

	}

	else if(gameState == 5){

		PlaySound(NULL, NULL, SND_SYNC); // stops sound

		gfx.DrawSprite(0, 0, &tutorial);

		if(gameStateCounter < 60)
			gameStateCounter++;


		if(kbd.EnterIsPressed() && gameStateCounter == 60){
			
			gameStateCounter = 0;

			gameState = 1;
			menuState = 0;

		}




	}

}












int gameControl::rectCollide(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2){

	boolean isColliding = false; // assume false prove true
	boolean leftCollide = false;  // direction from point of view of MOVING OBJECT not tile
	boolean rightCollide = false;
	boolean floorCollide = false;
	boolean ceilingCollide = false;

	int returnCode(0);

	if( (x2+w2)>=x1 && x2 <=(x1+w1) ){

		if( (y2+h2)>=y1 && y2 <=(y1+h1) ){

			isColliding = true;

		}
	}

	if( (x2+w2)>=x1 && (x2+w2-5) <=(x1+w1) ){

		if( (y2+h2-5)>=y1 && (y2+5) <=(y1+h1) ){

			leftCollide = true;

		}
	}

	if( (x2+5)>=x1 && x2 <=(x1+w1) ){

		if( (y2+h2-5)>=y1 && (y2+5) <=(y1+h1) ){

			rightCollide = true;

		}
	}

	if( (x2+w2-5)>=x1 && (x2+5) <=(x1+w1) ){

		if( (y2+5)>=y1 && y2 <=(y1+h1) ){

			floorCollide = true;

		}
	}

	if( (x2+w2-5)>=x1 && (x2+5) <=(x1+w1) ){

		if( (y2+h2)>=y1 && (y2+h2-5) <=(y1+h1) ){

			ceilingCollide = true;

		}
	}

	if(isColliding){

		if(leftCollide)
			returnCode = 3;
		if(rightCollide)
			returnCode = 4;
		if(ceilingCollide)
			returnCode = 2;
		if(floorCollide)
			returnCode = 1;
	}
	

	return returnCode;
	
}


void gameControl::drawHealthBar(int H){ // dummy bar for now make sprites

	if(H == 5){

		gfx.DrawSprite(550, 0, &hpSym);
		gfx.DrawSprite(600, 0, &hpSym);
		gfx.DrawSprite(650, 0, &hpSym);
		gfx.DrawSprite(700, 0, &hpSym);
		gfx.DrawSprite(750, 0, &hpSym);

	}
	if(H == 4){

		gfx.DrawSprite(600, 0, &hpSym);
		gfx.DrawSprite(650, 0, &hpSym);
		gfx.DrawSprite(700, 0, &hpSym);
		gfx.DrawSprite(750, 0, &hpSym);

	}
	if(H == 3){

		gfx.DrawSprite(650, 0, &hpSym);
		gfx.DrawSprite(700, 0, &hpSym);
		gfx.DrawSprite(750, 0, &hpSym);

	}
	if(H == 2){

		gfx.DrawSprite(700, 0, &hpSym);
		gfx.DrawSprite(750, 0, &hpSym);

	}
	if(H == 1){

		gfx.DrawSprite(750, 0, &hpSym);

	}






}



void gameControl::drawBrackets(int MS){

	D3DCOLOR c(D3DCOLOR_XRGB(0, 255, 0)); // CHANGE MENU BRACKET COLOURS HERE

	if( MS == 0){ // brackets around PLAY

		gfx.drawRect(50, 215, 10, 30, c);
		gfx.drawRect(200, 215, 10, 30, c);

	}
	else if( MS == 1){ // brackets around TUTORIAL

		gfx.drawRect(50, 275, 10, 30, c);
		gfx.drawRect(200, 275, 10, 30, c);

	}
	else if( MS == 2){ // brackets around EXIT

		gfx.drawRect(50, 345, 10, 30, c);
		gfx.drawRect(200, 345, 10, 30, c);

	}

}

// sets current map and collisions to selected map

void gameControl::setMap(char inMap[128][12]){

	for(int index1 = 0; index1<128; index1++){

		for(int index2 = 0; index2<12; index2++){

			currentMap[index1][index2] = inMap[index1][index2];

		}
	}
}

void gameControl::setCollision(boolean inMapCollisions[128][12]){

	for(int index1 = 0; index1<128; index1++){

		for(int index2 = 0; index2<12; index2++){

			currentMapCollisionAreas[index1][index2] = inMapCollisions[index1][index2];

		}
	}
}