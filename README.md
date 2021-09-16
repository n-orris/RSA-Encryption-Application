# **ULock**
### **Project built in CPSC 210 (Software Construction)**



Provides services involving encryption/decryption and storage of secure messages, files, and keys.
Utilizing the Java Cryptography architecture (JCA) and several external libraries users will be able to:


- Generate public/private key pairs
- Encrypt messages using public keys
- Decrypt messages using private keys
- store encrypted messages/files

### *Who will use it?*
Whether it be sending emails, protecting your computer files or controlling access & permissions, This application will 
be useful for anyone dealing with confidential information. It will allow for storage and encryption services that can
be done offline and stored on the physical computer. This helps people secure information without the risks of associated 
with online third party encryption providers. A Simple UX and help section will allow for individuals without 
Cybersecurity knowledge to make use of this application.   
 
### *Interest*

This project is of interest to me because I am currently work on the business side of a Cybersecurity company and have
been interested in the subject since a young age. I have previous technical experience through Capture the flag style 
CyberSec hackathons and choose this project as a way to expand my technical knowledge of the subject. I would like to
a good understanding of the implementation of encryption in software projects.

User Stories:
- As a user, I want to be able to Generate an encryption Keypair
- As a user, I want to be able to encrypt a message with a public key
- As a user, I want to be able to add multiple encryptions and associated messages (Cipherobjs) to an account
- As a user, I want to be able to view my keypair

P2 Stories:
-As a user, I want to be able to save an account containing my info and keys
-As a user, I want to be able to enter my userid and use the associated account 


Phase 4: Task 2: Implemented robust design in CipherObject class

- Exceptions created: InvalidKeyPairExpection, PrivateKeyException, PublicKeyException
- InvalidKeyPairException methods: validPair
- PrivateKeyException: createPrivateKey, getCipherDecrypt, decryptText
- PublicKeyException: createPublicKey, getCipherEncrypt, encryptText
- NoSuchAlgorithmException & NoSuchPaddingException: Constructor & genKeyPair throws JCA exceptions if invalid encryp

Phase 4: Task 3:
- I would remove the field of Cipherobj in userInteraction and directly use the accounts current cipher instead of 
always setting the current account cipher to the CipherObj field. This would get rid of the association of 
userInteraction with Cipherobj.
- I would refactor my 3 GUIs into one to avoid having too many windows and a small UML diagram 

