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

## How To Clone and Run RowdyGuard
To run this application, first ensure Git is properly downloaded on your device.
If Git is not already installed, refer to the PDF linked below for instructions:
[Git Setup and SSH Access Token Configuration](https://github.com/user-attachments/files/17941240/Git.Setup.and.SSH.Access.Token.Configuration.2.pdf)

Once Git is installed, navigate to our GitHub page. Click the green-colored Code button and copy the hyperlink from the SSH option as shown in the image:

![Step 1](https://github.com/user-attachments/assets/73450d4a-3048-487d-8f5a-0a09ac9b28ee)

Next, open your Android Studio and go to File > New > Project from Version Control as shown:

![Step 2](https://github.com/user-attachments/assets/d2b4cd78-209a-41b5-b3b7-ce5c3a4ccbd8)

After clicking Project from Version Control, a popup window will appear. Choose Git from the drop-down menu:

![Step 3](https://github.com/user-attachments/assets/875a9f1a-e48f-4cd2-907e-2e09458e2daf)

Finally, paste the link in the URL field, select your desired Directory, and click the Clone button:

![Step 4](https://github.com/user-attachments/assets/10dd284c-11c0-47d5-8d17-3b53440a298c)

Once the application is successfully cloned and your Android device or emulator is connected, you can run the application by clicking the green Play button in the top-right corner:

![Step 5](https://github.com/user-attachments/assets/e1e1eb22-d115-4eb0-9e78-bc221799379e)

After the application finishes loading, use your login credentials to access your password manager!