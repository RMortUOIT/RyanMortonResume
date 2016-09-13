#include <SPI.h>
#include <Ethernet.h>


byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};
IPAddress ip(192, 168, 1, 91);

EthernetServer server(80);

const int NUMBER_OF_SAMPLES(10);

long int reactionSet[NUMBER_OF_SAMPLES];

int errors;

int but1(14);
int led1(3);

int but2(15);
int led2(5);

int but3(16);
int led3(6);

int but4(17);
int led4(7);



void setup() {

  pinMode(but1, INPUT);
  pinMode(led1, OUTPUT);

  pinMode(but2, INPUT);
  pinMode(led2, OUTPUT);

  pinMode(but3, INPUT);
  pinMode(led3, OUTPUT);

  pinMode(but4, INPUT);
  pinMode(led4, OUTPUT);

  
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  
  errors = 0;

  digitalWrite(led1, HIGH);
  digitalWrite(led2, HIGH);
  digitalWrite(led3, HIGH);
  digitalWrite(led4, HIGH);
  delay(500);
  digitalWrite(led1, LOW);
  digitalWrite(led2, LOW);
  digitalWrite(led3, LOW);
  digitalWrite(led4, LOW);

  // start the Ethernet connection and the server:
  Ethernet.begin(mac, ip);
  server.begin();
  Serial.print("server is at ");
  Serial.println(Ethernet.localIP());

}

void loop() {
  // listen for incoming clients
  EthernetClient client = server.available();
  if (client) {
    Serial.println("new client");
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        Serial.write(c);
        // if you've gotten to the end of the line (received a newline
        // character) and the line is blank, the http request has ended,
        // so you can send a reply
        if (c == '\n' && currentLineIsBlank) {
          // send a standard http response header
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");  // the connection will be closed after completion of the response
          client.println(); //line is needed to separate header from body
          client.println("transmission received");
          client.println();


          client.stop();
          
          runTest();

          delay(3000);



          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
        } else if (c != '\r') {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }
      }
    }
    // give the web browser time to receive the data
    delay(1);
    // close the connection:
    client.stop();
    Serial.println("client disconnected");
  }

}




void runTest(){

  digitalWrite(led1, HIGH);
  digitalWrite(led2, HIGH);
  digitalWrite(led3, HIGH);
  digitalWrite(led4, HIGH);

  bool startSignal = false;

  while(!startSignal){

    int states[4];

    states[0] = digitalRead(but1);
    states[1] = digitalRead(but2);
    states[2] = digitalRead(but3);
    states[3] = digitalRead(but4);

    if(states[0] == HIGH || states[1] == HIGH 
      || states[2] == HIGH || states[3] == HIGH){

        startSignal = true;

        digitalWrite(led1, LOW);
        digitalWrite(led2, LOW);
        digitalWrite(led3, LOW);
        digitalWrite(led4, LOW);

        errors = 0;
        
    }
    
  }


  //delay 3 seconds to let user get ready
  delay(3000);

  for(int index = 0; index < NUMBER_OF_SAMPLES; index++){

    long int midTime = reactionTest();

    if(midTime == -1){

      errors++;

      index--;
      
    }
    else{

      reactionSet[index] = midTime;
      
    }
    
  }


  //sort reaction set
  sortReactionSet();

  //FOR TESTING
  for(int index = 0; index < NUMBER_OF_SAMPLES; index++){

    Serial.print(reactionSet[index]);
    Serial.print("\n");

  }

  long int totalTime = 0;

  for(int index = 0; index < NUMBER_OF_SAMPLES; index++){

    totalTime += reactionSet[index];
    
  }

  long int avgTime = totalTime/NUMBER_OF_SAMPLES;

  Serial.print("Average Reaction Time: ");
  Serial.print(avgTime);
  Serial.print(" units\n");

  // sample standard deviation
  long long int devSum = 0;

  for(int index = 0; index < NUMBER_OF_SAMPLES; index++){

    devSum += pow( reactionSet[index] - avgTime, 2);

  }

  long int SampleSD = pow(devSum/(NUMBER_OF_SAMPLES-1), 0.5); //34 = n-1 used cause sample

  Serial.print("Sample Standard Deviation: ");
  Serial.print(SampleSD);
  Serial.print(" units\n");


  long int median = reactionSet[NUMBER_OF_SAMPLES/2]; //0-16, 17, 18-34

  double skew = (avgTime - median)/(double)SampleSD;

  Serial.print("Skew: ");
  Serial.print(skew);
  Serial.print(" units\n");

  digitalWrite(led1, HIGH);
  digitalWrite(led2, HIGH);
  digitalWrite(led3, HIGH);
  digitalWrite(led4, HIGH);

  Serial.print("Errors: ");
  Serial.print(errors);
  Serial.print(" units\n");


  String postMessage = "AvgTime=";
  postMessage += avgTime;
  postMessage += "&SSD=" ;
  postMessage += SampleSD;
  postMessage += "&Skew=";
  postMessage += skew;
  postMessage += "&Errors=";
  postMessage += errors;
  
  post(postMessage, 192, 168, 1, 143, 8080);

        
  
  delay(2000);
  digitalWrite(led1, LOW);
  digitalWrite(led2, LOW);
  digitalWrite(led3, LOW);
  digitalWrite(led4, LOW);
        
  errors = 0;
    
}




void post(String message, int ip1, int ip2, 
  int ip3, int ip4, int port){

  Serial.println("Sending Response");

  IPAddress server(ip1, ip2, ip3, ip4);

  EthernetClient client;
  
  if (client.connect(server, port)) {
    client.println("POST /?Result=Reaction HTTP/1.1");
    client.println("Host: 192.168.1.143");
    client.println("User-Agent: Arduino/1.0");
    client.println("Connection: close");
    client.print("Content-Length: ");
    client.println(message.length());
    client.println();
    client.println(message);
  }
 
}





long int reactionTest(){

  long int result = -1;

  bool tooSoon = false;

  long int midCounter = 0;

  long int target = random(20000, 100000);
  
  while(midCounter < target){

    int states[4];

    states[0] = digitalRead(but1);
    states[1] = digitalRead(but2);
    states[2] = digitalRead(but3);
    states[3] = digitalRead(but4);

    if(states[0] == HIGH || states[1] == HIGH 
      || states[2] == HIGH || states[3] == HIGH){

        Serial.println(states[0]);
        Serial.println(states[1]);
        Serial.println(states[2]);
        Serial.println(states[3]);
        
        tooSoon = true;
        
    }

    midCounter++;
    
  }
  

  if(!tooSoon){
  
    int choice = random(0, 4);
  
    int LED = led1;
  
    if(choice == 1)
      LED = led2;
    else if(choice == 2)
      LED = led3;
    else if(choice == 3)
      LED = led4;
    
    digitalWrite(LED, HIGH);
  
    bool responded = false;
  
    long int reactionTime = 0;
  
    while(!responded){
  
      reactionTime++;
  
      int states[4];
  
      states[0] = digitalRead(but1);
      states[1] = digitalRead(but2);
      states[2] = digitalRead(but3);
      states[3] = digitalRead(but4);
  
      for(int index = 0; index < 4; index++){
  
        if(index == choice){
          //correct response
          if(states[index] == HIGH){
  
            digitalWrite(LED, LOW);
  
            responded = true;
  
            result = reactionTime;
  
            delay(500);
            
          }
  
        }
        else{
          //improper input
          if(states[index] == HIGH){
            
             responded = true;
  
             //error signal
            digitalWrite(led1, HIGH);
            digitalWrite(led2, HIGH);
            digitalWrite(led3, HIGH);
            digitalWrite(led4, HIGH);
            delay(500);
            digitalWrite(led1, LOW);
            digitalWrite(led2, LOW);
            digitalWrite(led3, LOW);
            digitalWrite(led4, LOW);
            delay(500);
            digitalWrite(led1, HIGH);
            digitalWrite(led2, HIGH);
            digitalWrite(led3, HIGH);
            digitalWrite(led4, HIGH);
            delay(500);
            digitalWrite(led1, LOW);
            digitalWrite(led2, LOW);
            digitalWrite(led3, LOW);
            digitalWrite(led4, LOW);
            delay(500);
            digitalWrite(led1, HIGH);
            digitalWrite(led2, HIGH);
            digitalWrite(led3, HIGH);
            digitalWrite(led4, HIGH);
            delay(500);
            digitalWrite(led1, LOW);
            digitalWrite(led2, LOW);
            digitalWrite(led3, LOW);
            digitalWrite(led4, LOW);
            delay(500);
            
            
          }
  
          
        }
  
  
        
      }
      
    }

  }

  Serial.print("Reaction Time: ");
  Serial.print(result);
  Serial.print(" units\n");

  return result;

}








//for now just use O(n2) selection sorter
void sortReactionSet(){

  int i;
  int j;

  int minAddress;

  for(j = 0; j < (NUMBER_OF_SAMPLES-1); j++){

    minAddress = j;

    for(i = j+1; i < NUMBER_OF_SAMPLES; i++){

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

  long int swapped[NUMBER_OF_SAMPLES];

  for(int index = 0; index < NUMBER_OF_SAMPLES; index++){

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

  for(int index = 0; index < NUMBER_OF_SAMPLES; index++){

    reactionSet[index] = swapped[index];
    
  }
  
}

