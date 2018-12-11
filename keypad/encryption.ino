

void encryptAndSend(String inputData)
{
    inputData.replace(" ","");
  if (inputData.length() > 16)
  {
    Serial.println("string te lang");
    return;
  }
  while (inputData.length() < 16)
  {
    inputData += " ";
  }
  int len = inputData.length() + 1;
  char data[len - 1];
  inputData.toCharArray(data, len) ;
  aes128_enc_single(key, data);
  int inputLen = sizeof(data);
  int encodedLen = base64_enc_len(inputLen);
  char encoded[encodedLen];
  base64_encode(encoded, data, inputLen);
  Serial.println(encoded);
}
