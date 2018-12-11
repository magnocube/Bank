/*
 * Typical pin layout used:
 * -----------------------------------------------------------------------------------------
 *             MFRC522      Arduino       Arduino   Arduino    Arduino          Arduino
 *             Reader/PCD   Uno/101       Mega      Nano v3    Leonardo/Micro   Pro Micro
 * Signal      Pin          Pin           Pin       Pin        Pin              Pin
 * -----------------------------------------------------------------------------------------
 * RST/Reset   RST          9             5         D9         RESET/ICSP-5     RST
 * SPI SS      SDA(SS)      10            53        D10        10               10
 * SPI MOSI    MOSI         11 / ICSP-4   51        D11        ICSP-4           16
 * SPI MISO    MISO         12 / ICSP-1   50        D12        ICSP-1           14
 * SPI SCK     SCK          13 / ICSP-3   52        D13        ICSP-3           15
*/


void intalizeRFID()
{
    SPI.begin();        // Init SPI bus
    mfrc522.PCD_Init(); // Init MFRC522 card
    for (byte i = 0; i < 6; i++) {
        key2.keyByte[i] = 0xFF;
    }
}
String readUID()
{
    return(dump_byte_array(mfrc522.uid.uidByte, mfrc522.uid.size));
}
String readBankNr()
{
  secure=true;
   // Show some details of the PICC (that is: the tag/card)
    //Serial.print(F("PICC type: "));
    MFRC522::PICC_Type piccType = mfrc522.PICC_GetType(mfrc522.uid.sak);
    //Serial.println(mfrc522.PICC_GetTypeName(piccType));

    // Check for compatibility
    if (    piccType != MFRC522::PICC_TYPE_MIFARE_MINI
        &&  piccType != MFRC522::PICC_TYPE_MIFARE_1K
        &&  piccType != MFRC522::PICC_TYPE_MIFARE_4K) {
        Serial.println(F("card error"));
        return;
    }
    // read block 1(in sector 0 with the keys in of block 3)
    byte sector         = 0;
    byte blockAddr      =1;
    byte trailerBlock   = 3;
    
    MFRC522::StatusCode status;
    byte buffer[18];
    byte size = sizeof(buffer);

    // Authenticate using key A
    status = (MFRC522::StatusCode) mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, trailerBlock, &key2, &(mfrc522.uid));
    if (status != MFRC522::STATUS_OK) {
        Serial.print(F("PCD_Authenticate() failed: "));
        Serial.println(mfrc522.GetStatusCodeName(status));
        return;
    }
    
    
    // Read data from the block
    status = (MFRC522::StatusCode) mfrc522.MIFARE_Read(blockAddr, buffer, &size);
    if (status != MFRC522::STATUS_OK) {
        Serial.print(F("MIFARE_Read() failed: "));
        Serial.println(mfrc522.GetStatusCodeName(status));
    }
    byte newUid[] = NEW_UID;
    if ( mfrc522.MIFARE_SetUid(newUid, (byte)4, true) ) {
       // Serial.println("  you failed hacker,our bank don't accept cracked cards");
        secure=false;
      }
      else
      {
       // Serial.println("  card is secure"); 
      }
    String banknr = dump_byte_array(buffer,3);
    // Halt PICC
    mfrc522.PICC_HaltA();
    // Stop encryption on PCD
    mfrc522.PCD_StopCrypto1();
    return banknr;
}
String dump_byte_array(byte *buffer, byte bufferSize) {
    String output="";
    for (byte i = 0; i < bufferSize; i++) {
        output+=buffer[i] < 0x10 ? " 0" : " ";
        output+=String(buffer[i],HEX);
    }
    return output;
}
