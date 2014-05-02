#include <SoftwareSerial.h>
#include <XBee.h>

//create XBee object

XBee xbee = XBee();
SoftwareSerial ss (2,3);
uint8_t buffer[72];

void setup(){
  Serial.begin(9600);
  ss.begin(9600);
  xbee.setSerial(ss);
}

void sendData(){
  
  int reading = analogRead(A0);
  buffer[0] = reading & 0xFF; //grab first byte
  buffer[1] = (reading >>8) & 0xFF; //grab the second byte
  
  XBeeAddress64 addr64 = XBeeAddress64(0,0);
  ZBTxRequest zbTx = ZBTxRequest(addr64, buffer, 2);
  xbee.send(zbTx);
}


void loop(){
  sendData();
  delay (10000);  
}
