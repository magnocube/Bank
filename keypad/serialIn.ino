void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read();
    inputString += inChar;
    if (inChar == '\n') {
      stringComplete = true;
    }
  }
  if (stringComplete) {    
    sharedKey = inputString;
    sharedKey.trim();  
    inputString = "";
    stringComplete = false;
  }
}
