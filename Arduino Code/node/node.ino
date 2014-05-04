#include <SoftwareSerial.h>
#include <XBee.h>

//create XBee object
XBee xbee = XBee();
//create Software Serial object
SoftwareSerial ss (2,3);
uint8_t buffer[72];

float bodyTemp;
float airTemp;

int air=0;
int body=1;

void setup(){
  Serial.begin(9600);
  ss.begin(9600);
  xbee.setSerial(ss);
}

void sendData(){
  //int reading = analogRead(A0);

  buffer[0] = airTemp;
  buffer[1] = bodyTemp;

  XBeeAddress64 addr64 = XBeeAddress64(0,0);
  ZBTxRequest zbTx = ZBTxRequest(addr64, buffer, 6);
  xbee.send(zbTx);
}

void getTemp(){
  //air is pin 0
  //body is pin 1
    int tempAir = analogRead(air);
    int tempBod = analogRead(body);
    
    float airVolt = tempAir*(5.0f/1023.0f);
    float bodVolt = tempBod*(5.0f/1023.0f);
    
    airTemp = airVolt*100-50;
    bodyTemp = bodVolt*100-50;
  
}


void loop(){
  getTemp();
  sendData();
  delay (10000);  
}
