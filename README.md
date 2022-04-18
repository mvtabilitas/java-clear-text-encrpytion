# java-clear-text-encrpytion

Class with various functions for strings manipulations.

It has only two public methods. encrypt() and decrypt().
encrypt()'s process:

  the encryption of the string:
    reverse the order and the content of the string -> do an ascii adition to each char (char + (int key)) ->
    add a number of random chars inbetween each real char (int spacing);
    
  hiding the real string:
    generate (int startAt) random characters -> insert encrypted string -> generate (int definedLength - int endAt) random characters
    
  inserting and finding the secret end:
    because each string has a unique length, two methods were required for inserting and finding where the real string ends.
    This is temporarily set to be always placed at the (int secretKey) position, which is automatically calculated so:
      (int secretKey) = (int definedLength - int startAt);
    This is obviously flawed because it does not work with certain combinations of settings and strings.
    On the other side it is not a problem if considered, and it makes decryption without the exact settings harder.
  
Any suggestions/changes are welcome.
