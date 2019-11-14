//Include the SoftwareSerial library
#include "SoftwareSerial.h"

//Create a new software  serial
SoftwareSerial bluetooth(2,3); //TX, RX (Bluetooth)

//BOTOES
int btnR = 6;
int btnG = 9;
int btnB = 8;
int btnY = 7;

int estadoBtnR;
int estadoBtnB;
int estadoBtnG;
int estadoBtnY;

void setup() {
  Serial.begin(38400);
  bluetooth.begin(38400);

  pinMode(btnR, INPUT);
  pinMode(btnB, INPUT);
  pinMode(btnG, INPUT);
  pinMode(btnY, INPUT);

  estadoBtnR = 0;
  estadoBtnB = 0;
  estadoBtnG = 0;
  estadoBtnY = 0;
}

void loop() {

  int estadoBtnR = digitalRead(btnR);
  int estadoBtnB = digitalRead(btnB);
  int estadoBtnG = digitalRead(btnG);
  int estadoBtnY = digitalRead(btnY);
  
  
  if(estadoBtnR == HIGH)
  {
    bluetooth.print("R\n");
    Serial.write("botao R pressionado");
    delay(300);
    return;
  }
  
  if(estadoBtnG == HIGH)
  {
    bluetooth.print("G\n");
    Serial.write("botao G pressionado");
    delay(300);
    return;
  }
  
  if(estadoBtnB == HIGH)
  {
    bluetooth.print("B\n");
    Serial.write("botao B pressionado");
    delay(300);
    return;
  }
  
  if(estadoBtnY == HIGH)
  {
    bluetooth.print("Y\n");
    Serial.write("botao Y pressionado");
    delay(300);
    return;
  }

  //Check received a byte from bluetooth by software serial
  if (bluetooth.available()) {
    char r = bluetooth.read(); //Read and save the byte
    Serial.print(r); //Print the byte to hardware serial
  }
  
}
