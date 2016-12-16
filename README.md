# Kript
A Java built RSA network encryption library.

## How to build
Kript is built with a Gradle wrapper. To build the software yourself, clone or download the repository. Access the directory with your command line. Type 

## How to use Kript in your own software
Kript is designed to be built into a .jar file and included in your project as a library. After you have the project set up with Kript included, simply create a Kript object. The constructor creates all the keys and provides methods to access the public key to send to the connection.

## Encrypting/Decrypting data
Kript has two functions, `byte[] encrypt(byte[] message)` and `byte[] decrypt(byte[] encryptedMessage)`. To encrypt data, convert the data into it's byte values, and pass the array into the `encrypt` method. The encrypted byte array will be returned.

To decrypt, simply pass the encrypted byte array into the decrypt method. The decrypted data will be returned in a byte array. Simply convert that array back into the data and you're good to go!

## Why doesn't Kript allow me to access the Private key?
It's designed to be as secure as possible for client-server connections with software such as a chat client. To do this most effectively, Kript is designed to generate a new key pair per connection. Because of that, the need to save the key pair to your computer is not needed. However, I will likely add that functionality to the library later on.

### Planned improvements
* Add ability to save key pairs to hard drive
* Add AES-256 file encryption
