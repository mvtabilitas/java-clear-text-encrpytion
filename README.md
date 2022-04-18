# java-clear-text-encrpytion

Class with various functions for strings manipulations.

It has only two public methods. encrypt() and decrypt().
encrypt()'s process:

  the encryption of the string:
    reverse the order and the content of the string -> do an ascii adition to each char (char + (int key))
    -> add a number of random chars inbetween each real char (int spacing);
    
  hiding the real string:
    generate (int startAt) random characters -> insert encrypted string -> generate (int definedLength - int endAt) random characters
    -> insert secret end
    
  inserting the secret end:
    because each string has a unique length, two methods were required for inserting and finding where the real string ends.
    the secret end is temporarily set to be always placed at the (int secretKey) position, which is automatically calculated so:
      (int secretKey) = (int definedLength - int startAt);
    This is obviously flawed because it does not work with certain combinations of settings and strings.
    On the other side it is not a problem if considered, and it makes decryption without the exact settings harder.
  
decrypt()'s process:

  the decryption of the string:
    select only every (int spacing)th characters -> do an ascii substraction to each char (char - (int key))
    -> reverse the content and the order of the string;
    
  revealing the real string:
    reveal secret end -> select string from (int startAt) to secret end -> decrypt string
    
  finding the secret end:
    here comes trouble. we know where the secret end starts at in our encrypted string with help of the (int secretKey), 
    but there is no unambigous way to find out how many digits is the secret end position made of, without completely exposing it.
    as a way around that, I made the method use the digits of the (int startAt) for the loop that extracts the secret end.
    in order for that to work flawlessly, one must always choose a round number for the (int definedLength), i.e. 1.000.000,
    and an (int startAt) that is between 10% and 90% of the (int defined length), i.e. 100.000-900.000.
    alternatively, one can calculate other scenarios that would made both encryption and decryption possible.
    this is pretty complexe aspect, leading to both positive and negative facts.
    
Any suggestions/changes are welcome.
