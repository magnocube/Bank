#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN         9           // Configurable, see typical pin layout above
#define SS_PIN          10          // Configurable, see typical pin layout above

MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.
MFRC522::MIFARE_Key key2;
#define NEW_UID {0xDE, 0xAD, 0xBE, 0xEF}

#include <AESLib.h>  
#include <Base64.h>
boolean secure=true;

uint8_t key[] = {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
String sharedKey = "hond";
char encryptedData[100];
int *size;

String inputString = "";                                                 // a string to hold incoming data, it will be reset after use
boolean stringComplete = false;                                          // whether the string is complete, it will be reset after use

String character = "q";                                                   // character that was read out by the keypad
int lastKeysPressed[2] = {0, 0};                                          // coordinates (pinNumbers) of the last pressed character, so arduino can wait until the button is released
void setup() {
  inputString.reserve(200);


  Serial.begin(9600);
  pinMode(2, INPUT_PULLUP);
  pinMode(3, INPUT_PULLUP);
  pinMode(4, INPUT_PULLUP);
  pinMode(5, INPUT_PULLUP);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  intalizeRFID();
  
}

void loop() {
  serialEvent();                                                          // read the serial monitor, if some data from outside arrived
  character = readKeypad();
  if (character != "q") {                                                 //character is by default "q", so if the keyad reads another value, it will be processed.
    encryptAndSend(sharedKey + character);
    waitForRelease(lastKeysPressed[0], lastKeysPressed[1]);
  }
  // Look for new cards
    if (  mfrc522.PICC_IsNewCardPresent())
    {
        // Select one of the cards
        if (  mfrc522.PICC_ReadCardSerial())
        {
          String uid=sharedKey;
          uid+=readUID();
          String banknr=readBankNr();
          if(secure==true)
          {
            encryptAndSend(uid);
            serialEvent(); 
            delay(100);
            serialEvent(); 
            delay(100);
            serialEvent(); 
            encryptAndSend(sharedKey+banknr);
         }
          delay(500);
        }
    }
}









