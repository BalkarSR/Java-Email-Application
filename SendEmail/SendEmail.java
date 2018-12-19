import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 I declare that this assignment is my own work and that all material previously written or published in
 any source by any other person has been duly acknowledged in the assignment. I have not submitted this
 work, or a significant part thereof, previously as part of any academic program. In submitting this assignment
 I give permission to copy it for assessment purposes only.
 */

/**
 * DOCUMENTATION...
 */

/**
 *
 *<H1>main</H1>
 *
 *<H3>Purpose and Description</H3>
 *
 *<P>
 * Application is a mail client that sends an email with attachments
 *</P>
 *<P>
 * This program allows a client to send an email to various CC/BCC without attachments
 *</P>
 *<P>
 * This program uses Java 1.8
 *</P>
 *
 *<DL>
 *<DT> Compiling and running instructions</DT>
 *<DT> Assuming SDK 1.8</DT>
 *<DT> Change to the directory containing the source code.</DT>
 *<DD> Compile:    javac main.java</DD>
 *<DD> Run:        java main ${filename}</DD>
 *<DD> Document:   javadoc main.java</DD>
 *</DL>
 */

/**
 *
 * <H3>Classes</H3>
 *
 *<P>
 * public class main {<BR>
 * This is the main public class for this application
 *</P>
 *
 * <H3>main Methods</H3>
 *
 *<P>
 * public static void send(String server, String user, String pass, String recp, String sub, String msg, String file, String [] cc, String [] bcc){<BR>
 * This method is used to get all the required data for the email to be sent, and it takes all the fields and sends the email
 * 
 *</P>
 */

public class SendEmail {

    public static void main(String[] args){
        Map data = new HashMap();

        File file = new File(args[0]);

        //gets all the information from the text file using hashmap
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {

                String i = sc.nextLine();
                System.out.println(i);
                String[] parts = i.split(":");
                if(parts[0].trim().equals("CC") || parts[0].trim().equals("BCC")){
                    data.put(parts[0].trim(),parts[1].split(","));
                } else if(parts[0].trim().equals("Body")){

                    String body = "";
                    body+=parts[1];
                    while (sc.hasNextLine()) {
                        String bod = sc.nextLine();
                        body+=bod;
                    }
                    data.put(parts[0].trim(),body);
                }else{
                    data.put(parts[0].trim(),parts[1].trim());
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        send((String)data.get("Server"), (String)data.get("User"), (String)data.get("Password"), (String)data.get("To"), (String)data.get("Subject"), (String)data.get("Body"), (String [])data.get("CC"), (String[])data.get("BCC"));
    }

    public static void send(String server, String user, String pass, String recp, String sub, String msg, String [] cc, String [] bcc){
        //Getting the sessions
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp." + server + ".com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,pass);
                    }
                });

        //Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(recp));

            for(int i = 0; i < bcc.length; i++){
                message.addRecipient(Message.RecipientType.BCC,new InternetAddress(bcc[i]));
            }

            for(int i = 0; i < cc.length; i++){
                message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc[i]));
            }

            message.setSubject(sub);
            message.setText(msg);

            //send message
            Transport.send(message);

            System.out.println("Email was sent!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
