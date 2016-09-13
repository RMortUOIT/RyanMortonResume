#include <LiquidCrystal.h>
//#include <time>

LiquidCrystal lcd(12, 11, 5, 4, 3, 13);

int switchState = 0;
int switchState2 = 0;

long int reactionSet[35];

int errors;



void setup() {

  lcd.begin(16, 2);

  //red
  pinMode(6, OUTPUT);
  pinMode(7, INPUT);
  
  //green
  pinMode(8, OUTPUT);
  pinMode(9, INPUT);

  Serial.begin(9600);

  errors = 0;

}

void loop() {

  lcd.clear();
  lcd.print("Press To Begin");

  digitalWrite(6, HIGH);
  digitalWrite(8, HIGH);

  bool startSignal = false;

  while(!startSignal){

    switchState = digitalRead(7);
    switchState2 = digitalRead(9);

    if(switchState == HIGH || switchState2 == HIGH){

      startSignal = true;

      digitalWrite(6, LOW);
      digitalWrite(8, LOW);

      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("Testing In");
      lcd.setCursor(0, 1);
      lcd.print("Progress . . . ");

      errors = 0;
      
    }
    
  }

  delay(5000);

  // do 35 times for statistical accuracy

  //reactionSet[35];
  
  for(int index = 0; index < 35; index++){


    //TODO: need to randomize whether red or green
    int choice = random(1, 3);

    //red light
    if(choice == 1){

      digitalWrite(6, HIGH);
  
      bool responded = false;
    
      long int reactionTime = 0;
    
      while(!responded){
    
        reactionTime++;
        
        switchState = digitalRead(7);
        switchState2 = digitalRead(9);
  
        //switching to while to make this while button held down?
        if(switchState == HIGH){
    
          digitalWrite(6, LOW);
    
          responded = true;
  
          switchState = digitalRead(7);
  
          delay(500);
          
        }
        else if(switchState2 == HIGH){

          errors++;

          index--;

          responded = true;
  
          //error signal
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          
        }
        
      }
  
      Serial.print("Reaction Time: ");
      Serial.print(reactionTime);
      Serial.print(" units\n");
  
      reactionSet[index] = reactionTime;
  
      //must scan for premature/out of turn clicks
      //delay(1000);
  
      long int midCounter = 0;
      
      //make random target between 100000 and 400000
      long int target = random(20000, 100000);
  
      while(midCounter < target){
  
        switchState = digitalRead(7);
        switchState2 = digitalRead(9);
  
        //switching to while to make this while button held down?
        if(switchState == HIGH || switchState2 == HIGH){
  
          errors++;

          index--;
  
          //error signal
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
  
        }
  
        midCounter++;
  
      }
     
    }
    else{ //green

      digitalWrite(8, HIGH);
  
      bool responded = false;
    
      long int reactionTime = 0;
    
      while(!responded){
    
        reactionTime++;
        
        switchState = digitalRead(9);
        switchState2 = digitalRead(7);
  
        //switching to while to make this while button held down?
        if(switchState == HIGH){
    
          digitalWrite(8, LOW);
    
          responded = true;
  
          switchState = digitalRead(9);
  
          delay(500);
          
        }
        else if(switchState2 == HIGH){

          errors++;

          index--;

          responded = true;
  
          //error signal
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          
        }
        
      }
  
      Serial.print("Reaction Time: ");
      Serial.print(reactionTime);
      Serial.print(" units\n");
  
      reactionSet[index] = reactionTime;
  
      //must scan for premature/out of turn clicks
      //delay(1000);
  
      long int midCounter = 0;
      
      //make random target between 100000 and 400000
      long int target = random(20000, 100000);
  
      while(midCounter < target){
  
        switchState = digitalRead(9);
        switchState2 = digitalRead(7);
  
        //switching to while to make this while button held down?
        if(switchState == HIGH || switchState2 == HIGH){
  
          errors++;

          index--;
  
          //error signal
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
          digitalWrite(6, HIGH);
          digitalWrite(8, HIGH);
          delay(500);
          digitalWrite(6, LOW);
          digitalWrite(8, LOW);
          delay(500);
  
        }
  
        midCounter++;
  
      }

    }
    
  }

  //sort reaction set
  sortReactionSet();

  //FOR TESTING
  for(int index = 0; index < 35; index++){

    Serial.print(reactionSet[index]);
    Serial.print("\n");

  }

  long int totalTime = 0;

  for(int index = 0; index < 35; index++){

    totalTime += reactionSet[index];
    
  }
  
  long int avgTime = totalTime/35;

  Serial.print("Average Reaction Time: ");
  Serial.print(avgTime);
  Serial.print(" units\n");

  // sample standard deviation
  long long int devSum = 0;

  for(int index = 0; index < 35; index++){

    devSum += pow( reactionSet[index] - avgTime, 2);

  }

  long int SampleSD = pow(devSum/34, 0.5); //34 = n-1 used cause sample

  Serial.print("Sample Standard Deviation: ");
  Serial.print(SampleSD);
  Serial.print(" units\n");


  long int median = reactionSet[17]; //0-16, 17, 18-34

  double skew = (avgTime - median)/(double)SampleSD;

  Serial.print("Skew: ");
  Serial.print(skew);
  Serial.print(" units\n");

  boolean clearTest = false;
  int state = 1;

  digitalWrite(6, HIGH);
  digitalWrite(8, HIGH);

  while(!clearTest){

    if(state == 1){

      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("AVG");
      lcd.setCursor(0, 1);
      lcd.print(avgTime);
      
      delay(5000);

      state = 2;
      
    }
    else if(state == 2){

      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("S value");
      lcd.setCursor(0, 1);
      lcd.print(SampleSD);
      
      delay(5000);

      state = 3;

    }
    else if(state == 3){

      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("skew");
      lcd.setCursor(0, 1);
      lcd.print(skew);
      
      delay(5000);

      state = 4;

    }
    else if(state == 4){

      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("errors");
      lcd.setCursor(0, 1);
      lcd.print(errors);
      
      delay(5000);

      state = 1;

    }

    switchState = digitalRead(7);
    switchState2 = digitalRead(9);
  
    if(switchState == HIGH || switchState2 == HIGH){

      errors = 0;
      
      digitalWrite(6, LOW);
      digitalWrite(8, LOW);
      delay(100);
      digitalWrite(6, HIGH);
      digitalWrite(8, HIGH);
      
      clearTest = true;

      lcd.clear();
      lcd.print("Resetting Test");
      
    }
    
  }
    
  delay(3000);
  

}

//for now just use O(n2) selection sorter
void sortReactionSet(){

  int i;
  int j;

  int minAddress;

  for(j = 0; j < 34; j++){

    minAddress = j;

    for(i = j+1; i < 35; i++){

      if(reactionSet[i] < reactionSet[minAddress]){

        minAddress = i;
        
      }

    }

    if(minAddress != j){

      swap(j, minAddress);
      
    }
    
  }
  
}




void swap(int i, int j){

  long int swapped[35];

  for(int index = 0; index < 35; index++){

    if(index == i){

      swapped[index] = reactionSet[j];
      
    }
    else if(index == j){

      swapped[index] = reactionSet[i];
      
    }
    else{

      swapped[index] = reactionSet[index];
      
    }

  }

  for(int index = 0; index < 35; index++){

    reactionSet[index] = swapped[index];
    
  }
  
}