//these methods are the cornerstone algorithms of the maze generator
//the maze itself is a matrix of nodes that start unconnected and these algorithms connect
//in order to form a tree like maze (no loops allowed)
//first the generator produces the path to the exit (the victory path)
//then the generator fills in the unconnected nodes with dead ends


// O(n) LRUD method
private MazeNode[][] genVictoryPath(int width, int height){

        //generate empty nodes
        MazeNode nodes[][] = genEmpty(width, height);

        //generate LRUD path

        //must have at least width-1 rights, add one more each time a left is added
        int LCount = (int)(Math.random()*width + (width/2));
        int UCount = (int)(Math.random()*height + (height/2));

        int RCount = width - 1 + LCount;
        int DCount = height - 1 + UCount;

        int x = 0;
        int y = 0;

        CoordStack rawStack = new CoordStack();
        rawStack.push(new int[]{0, 0}); // push 0,0 onto the stack

        char prevDir = 'X';


        while( (LCount + UCount + RCount + DCount) > 0){

            //too many rights with nothing else, path has snapped to bottom
            if( (LCount + UCount + RCount + DCount) == RCount && RCount > 10 ){

                //add extra directions
                int LAdd = (int)(Math.random()*5);
                LCount += LAdd;
                RCount += LAdd;

                int UAdd = (int)((Math.random()*(height/2)) + 1);
                UCount += UAdd;
                DCount += UAdd;

            }


            //too many downs with nothing else, path has snapped to right
            if( (LCount + UCount + RCount + DCount) == DCount && DCount > 10 ){

                //add extra directions
                int LAdd = (int)(Math.random()*(width/2) + 1);
                LCount += LAdd;
                RCount += LAdd;

                int UAdd = (int)(Math.random()*5);
                UCount += UAdd;
                DCount += UAdd;

            }


            String options = "";

            if(LCount > 0 && x > 0 && prevDir != 'R' && y != 0 && y != height-1)
                options += 'L';

            if(RCount > 0 && x < width-1 && prevDir != 'L')
                options += 'R';

            if(UCount > 0 && y > 0 && prevDir != 'D' && x != 0 && x != width-1)
                options += 'U';

            if(DCount > 0 && y < height-1 && prevDir != 'U')
                options += 'D';

            options = genRandomOptions(options);

            //System.out.println("Coords: " + x + ", " + y);
            //System.out.println("Counts: L=" + LCount + "   R=" + RCount
            //		+ "   U=" + UCount + "   D=" + DCount);



            //pick first option
            char decision = options.charAt(0);

            prevDir = decision;

            if(decision == 'L'){
                x--;
                LCount--;

            }

            if(decision == 'R'){
                x++;
                RCount--;
            }

            if(decision == 'U'){
                y--;
                UCount--;
            }

            if(decision == 'D'){
                y++;
                DCount--;
            }


            rawStack.push(new int[]{x, y});

            //prematurely found exit, break
            if(x == width-1 && y == height-1){

                break;

            }

        }

        //fix loops

        //create new stack with loops taken out
        CoordStack fixedStack = new CoordStack();

        while(rawStack.size() > 0){

            int currentCoords[] = rawStack.pop(); //coords are out of stack, if stack still contains there is a loop

            //if there is a loop, ingore all the coords between equal coords
            while(rawStack.contains(currentCoords)){

                rawStack.pop(); //pop coords to nowhere

            }

            fixedStack.push(currentCoords); //push unlooped coords onto fixed stack

        }


        //dump coordstack path onto nodes
        nodes = convertStackToCode(nodes, fixedStack);

        return nodes;

    }



    //connect the rest of the unconnected nodes, start from bottom right to make long dead ends
    private MazeNode[][] genDeadEnds(MazeNode[][] nodes){

        for(int indexW = nodes.length-1; indexW >=0; indexW--){

            for(int indexH = nodes[0].length-1; indexH >= 0; indexH--){

                if(nodes[indexW][indexH].getCode() == 'P'){

                    CoordStack stack = new CoordStack();
                    stack.push(new int[]{indexW, indexH});

                    nodes = createDeadEnd(nodes, stack);

                }

            }

        }

        return nodes;

    }