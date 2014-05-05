int forceReading;
int force_Pin=2;

void setup(){
 Serial.begin(9600); 
}

void getForce(){
 forceReading = analogRead(force_Pin); 
 Serial.println(forceReading);
}



void loop(){
    getForce();
}
