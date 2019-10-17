#include<SoftwareSerial.h>
SoftwareSerial Bluetooth(7, 8);

void setup()
{
  Serial.begin(9600);
  Bluetooth.begin(9600);
}

void loop()
{
  delay(500);
  if (Bluetooth.available()) {
  Bluetooth.print("G");
  Serial.write("G");
  delay(2000);
  Bluetooth.print("M");
  Serial.write("M");
  }
}

