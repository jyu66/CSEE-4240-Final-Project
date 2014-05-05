#include <SoftwareSerial.h>
#include <XBee.h>

//create XBee object
XBee xbee = XBee();
//create Software Serial object
SoftwareSerial ss (2,3);
uint8_t buffer[72];

float bodyTemp;
float airTemp;
int forceReading;
int pirVal;


int air_Pin=0;
int body_Pin=1;
int force_Pin=2;
int pirPin = 4;

void setup(){
  Serial.begin(9600);
  ss.begin(9600);
  xbee.setSerial(ss);
  pinMode(pirPin,INPUT);
  
}

void sendData(){
  //int reading = analogRead(A0); 

  buffer[0] = airTemp;
  buffer[1] = bodyTemp;
  buffer[2] = forceReading;
  buffer[3] = pirVal;

  XBeeAddress64 addr64 = XBeeAddress64(0,0);
  ZBTxRequest zbTx = ZBTxRequest(addr64, buffer, 8);
  xbee.send(zbTx);
}

//get temperature of both air and human body
void getTemp(){
  //air is pin 0
  //body is pin 1
    float tempAir = analogRead(air_Pin);
    float tempBod = analogRead(body_Pin);
    
    float airVolt = tempAir*(5.0f/1023.0f);
    float bodVolt = tempBod*(5.0f/1023.0f);
    
    airTemp = airVolt*100-50;
    bodyTemp = bodVolt*100-50;
}//end getTemp

//gets force currently being applied to body.
void getForce(){
    forceReading = analogRead(force_Pin);
   //erial.println(forceReading);
}

void hasMotion(){
    pirVal = digitalRead(pirPin);
    if (pirVal ==LOW){
      Serial.println(pirVal);
      Serial.println("MOTION DETECTED"); 
    }
}


void loop(){
  getTemp();
  getForce();
  hasMotion();
  sendData();
  delay (5000);
}
