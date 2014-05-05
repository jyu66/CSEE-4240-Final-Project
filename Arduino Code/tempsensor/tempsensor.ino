float temp;
int reading =0;

void setup(){
  Serial.begin(9600);
}

void loop(){
  int tempSensor = analogRead(reading);
  float tempSensorVoltage = tempSensor*5.0f/1023.0f;
  
  float temp = tempSensorVoltage*100-50;
  Serial.println (temp);
  delay(3000);
  
}
