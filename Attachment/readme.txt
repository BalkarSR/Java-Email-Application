This file shows you how to compile/run the program.

In order to compile, simply run the following command on your terminal/cmd line:
javac -cp javax.mail.jar SendEmail.java

In order to run the compiled program run the following command:
java -cp .:javax.mail.jar SendEmail thisfile.txt

The thisfile.txt includes the key information such as server, user, password, etc. It can be changed to whatever you want. However, for testing purposes I made up a gmail account to test.

If you get "Exception in thread "main" java.lang.RuntimeException: javax.mail.AuthenticationFailedException: 534-5.7.9 Please log in with your web browser and then try again. Learn more at
534 5.7.9  https://support.google.com/mail/?p=WebLoginRequired 14-v6sm893851itu.20 - gsmtp" then you have to enable allow access for low security apps in your mail settings.