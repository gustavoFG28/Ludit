#include <SoftwareSerial.h>

//BOTOES
int btn1 = 5;
int btn2 = 6;

int estadoBtn1;
int estadoBtn2;

//BLUETOOTH
SoftwareSerial minhaSerial(0, 1); //RX, TX

void setup() {
  // BOTOES
  Serial.begin(115200);
  pinMode(btn1, INPUT);
  pinMode(btn2, INPUT);

  estadoBtn1 = 0;
  estadoBtn2 = 0;

  //BLUETOOTH
  minhaSerial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  estadoBtn1 = digitalRead(btn1);
  estadoBtn2 = digitalRead(btn2);

  if(minhaSerial.available())
  {
    if(estadoBtn1 == HIGH)
    {
      minhaSerial.write("1");
      Serial.write("botao1 pressionado");
      delay(300);
      return;
    }
  
    if(estadoBtn2 == HIGH)
    {
      minhaSerial.write("2");
      Serial.write("botao2 pressionado");
      delay(300);
      return;
    }
  }
}
    
