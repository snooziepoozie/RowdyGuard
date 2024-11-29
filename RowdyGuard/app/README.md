# RowdyGuard
This document outlines the functionality that is included in the final version of the application.
At a high level, the functionality includes:
1. Database encryption
2. Vault overview containing all stored passwords
3. Password operations
   1. Addition
   2. Modification
   3. Deletion

## Encryption
RowdyGuard uses AES-256 encryption to store passwords in a central database. These are
encrypted at all times and only decrypted into memory when the user provides the correct decryption
password.

## Vault
RowdyGuard organizes all passwords into a centralized vault structure. Once a user has successfully
logged in, they are presented with all passwords in one spot in a list format. All that's necessary
to access the password data is to click the password item that's desired. If the user wants to add a
password to their vault, they may press the corresponding button up at the top of the activity
screen.

## Password Operations
In the vault overview, if a user wants to access their credentials, all they have to do is click the
corresponding button. RowdyGuard will then display the title, username, and password in a dedicated
activity. If the user wants to either delete or edit said password, there are convenient buttons at
the top of the screen.