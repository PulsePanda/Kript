# Kript
A Java based network encryption library

# CURRENTLY UNDERGOING REVISION

This encryption library is built as a utility for networking. All that must be done to encrypt and decrypt an object is the following:  
* ## NOTE THAT THIS FLOW IS ONLY CURRENT BEFORE MAY 1ST 2016 ##
* Create a Kript object
* Convert your object into a byte array
* Pass that byte array to your `long kriptObject.encrypt(byte)` method, one index at a time.
This has some obvious flaws, and is being revised.

### Current revision strategy
Right now the current method of encryption is not nicely flowing, and hogs up memory and CPU for no real benefit.  
Currently, revisions include changing the `long encrypt(byte)` into `Packet encrypt(Packet)`. This new method will convert a packet that is passed into an encrypted version of that packet, and then returned.  
This version of Kript will not be available for some time, however it is coming. Another thing to note is that if you do not wish to use the Packet structure, the current method will still be available for use.
