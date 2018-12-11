String keys[4][4] = {
  {"1", "2", "3","A"},
  {"4", "5", "6","B"},
  {"7", "8", "9","C"},
  {"*", "0", "#","D"}
};

void waitForRelease(int x, int y) {
  digitalWrite(x, LOW);
  while (digitalRead(y) == LOW) {
    delay(5);
  }
  digitalWrite(x, HIGH);
}
String readKeypad() {
  for (int i = 6; i <= 9; i++) {
    digitalWrite(i, LOW);
    for (int i2 = 2; i2 <= 5; i2++) {
      if (digitalRead(i2) == LOW) {
        lastKeysPressed[0] = i;
        lastKeysPressed[1] = i2;
       return keys[i2 - 2][i - 6];
      }
    }
    digitalWrite(i, HIGH);
  }
  return "q";
}
