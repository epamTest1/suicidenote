package com.my.suicidenote.mail;

import com.mongodb.BasicDBObject;
import com.my.suicidenote.db.NoteHelper;
import com.my.suicidenote.db.SendNoteHelper;
import com.my.suicidenote.db.object.Note;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.bson.types.ObjectId;

/**
 *
 * @author Andrii_Manuiev
 */
public class SendMail {

    static final String SMTP_NAME = "smtp.gmail.com";
    static final String SMTP_PORT = "465";
    static final String USER_NAME = "epam.test1@gmail.com";
    static final String USER_PASS = "epamtest";
    static final String FROM = "suicide@dead.com";
    static final String SUBJECT = "%s has sent you a suicide note";
    
    public static void send(Note note) {

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_NAME);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
                
        Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, USER_PASS);
            }
        });
        Message simpleMessage = new MimeMessage(mailSession);

        InternetAddress fromAddress = null;
        InternetAddress[] toAddress = null;
        try {
            fromAddress = new InternetAddress(FROM);
            toAddress = InternetAddress.parse(note.getSentTo());
        } catch (AddressException e) {
            Logger.getLogger(Postman.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            simpleMessage.setFrom(fromAddress);
            for (InternetAddress rec : toAddress) {
                simpleMessage.setRecipient(Message.RecipientType.TO, rec);
            }
            simpleMessage.setSubject(String.format(SUBJECT, note.getFrom()));
            //TODO : develop and apply some template 
            simpleMessage.setText(note.getSay());

            Transport.send(simpleMessage);
            //move note to already sent collection
            NoteHelper.removeNote(note);
            SendNoteHelper.incertNote(note);
        } catch (MessagingException e) {
            Logger.getLogger(Postman.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}