#include <SoftwareSerial.h>
#include <XBee.h>

//create XBee object
XBee xbee = XBee();
XBeeResponse response = XBeeResponse();
//create reusable response objects for responses
ZBRxResponse rx = ZBRxResponse();
ZBRxIoSampleResponse ioSample = ZBRxIoSampleResponse();
//create Software Serial object
SoftwareSerial ss (2,3);
uint8_t buffer[72];


float bodyTemp;
float airTemp;
int forceReading;
int pirVal;
int friendly;
int reading;

int air_Pin=0;
int body_Pin=1;
int force_Pin=2;
int pirPin = 4;
int LED_PIN=13;
unsigned long sensorTime=5000;//for delay
unsigned long sensorLast=0;

void setup(){
  Serial.begin(9600);
  ss.begin(9600);
  xbee.setSerial(ss);
  pinMode(pirPin,INPUT);
  pinMode(LED_PIN, OUTPUT);
  
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

void receiveData(){
 
  xbee.readPacket();
  //if something has been received
  if (xbee.getResponse().isAvailable()){
       
    
      xbee.getResponse().getZBRxIoSampleResponse(ioSample);
      Serial.println("packet has been received");
      reading = (rx.getData(0)|(rx.getData(1)<<8));
      Serial.println(reading);
      digitalWrite(LED_PIN, reading);
      digitalWrite(LED_PIN, LOW);
      
  }//isAvailable
  
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
  if(millis()-sensorLast >sensorTime){
  sensorLast = millis();
  getTemp();
  getForce();
  hasMotion();
  sendData();
  }
  receiveData();
  
  
}
