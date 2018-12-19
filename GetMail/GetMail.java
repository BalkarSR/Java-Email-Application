import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

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
 * Application is a mail client that gets emails for the given email account
 *</P>
 *<P>
 * This program allows a client to view emails
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
 * public static void getContent(Message msg){<BR>
 * This method is used to get all the content
 * 
 *</P>
 *<P>
 public static void dumpPart(Part part) throws Exception {<BR>
 this method dumps all the excess content
 *</P>
 */

public class GetMail {

	Folder inbox;

	public static void getContent(Message msg)

	{
		try {
			
			Multipart mp = (Multipart) msg.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				dumpPart(mp.getBodyPart(i));
			}
		} catch (Exception ex) {
			System.out.println("Exception arise at get Content");
			ex.printStackTrace();
		}
	}

	public static void dumpPart(Part part) throws Exception {
		// Dump input stream .
		InputStream inputStream = part.getInputStream();
		
		if (!(inputStream instanceof BufferedInputStream)) {
			inputStream = new BufferedInputStream(inputStream);
		}
		int c;
		
		while ((c = inputStream.read()) != -1) {
			System.out.write(c);
		}
	}

	public static void main(String args[]) throws MessagingException, IOException {
		String hostServer = "";
		String username = "";
		String password = "";
		int index = -1;
		// verify the args length
		if (args.length < 3 || args.length > 4) {
			System.out.println("Number of arguments provided are incorrect");
			System.exit(0);
		}
		if (args[0].contains("gmail")) {
			hostServer = "imap.googlemail.com";
		}
		username = args[1];
		password = args[2];
		// in order to list the particular Index message
		if (args.length == 4) {
			index = Integer.valueOf(args[3]);
		}
		Session session = Session.getDefaultInstance(new Properties());
		// USE the IMAP protocol
		Store store = session.getStore("imap");
		// connect to the server
		store.connect(hostServer, username, password);
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);

		// Fetch unseen messages from inbox folder
		Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

		if (messages.length == 0) {
			System.out.println("No messages found.");
			System.exit(0);
		}
		if (index != -1) {
			for (int i = 0; i < messages.length; i++) {

				// print the index
				System.out.print((i + 1) + ". ");
				// print the message
				getContent(messages[i]);
				// print the email from
				System.out.println(" (" + messages[i].getFrom()[0].toString() + ")");
			}
		}else{
			int i = index;
			System.out.print((i + 1) + ". ");
			getContent(messages[i]);
			System.out.println(" (" + messages[i].getFrom()[0].toString() + ")");
			
		}

		inbox.close(true);
		store.close();
	}
}
