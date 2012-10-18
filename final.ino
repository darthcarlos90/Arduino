#include <SoftwareSerial.h>
int bluetoothTx = 2;
int bluetoothRx = 3;

SoftwareSerial bluetooth (bluetoothTx, bluetoothRx);

void loop(){
 //Leer de bluetooth y guardarlo
if(bluetooth.available())
{
  char received = (char) bluetooth.read();
  switch (received)
  {
    case 'i':
    //moverse a la izquierda
    break;
    
    case 'd':
    //moverse a la derecha
    break;
    
  }
}
}
