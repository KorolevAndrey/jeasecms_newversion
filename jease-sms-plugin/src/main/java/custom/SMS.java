package custom;

import jease.Names;
import jease.Registry;
import jease.cms.domain.Content;
import jease.cms.domain.Role;
import jease.cms.domain.User;
import jease.cms.service.Authenticator;
import jease.cms.service.Users;
import jease.cms.web.filter.etag.ETagHashUtils;
import jfix.db4o.Database;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Arrays;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

public class SMS {

    private static final Logger LOGGER = LoggerFactory.getLogger(ETagHashUtils.class);

    static {
        try {
            init("");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void init(String resource) throws Exception {
        //
    }


    public static void sendSMS(String ani, String text) {
        LOGGER.info("0");
        String sms_enabled = Registry.getParameter("JEASE_SMS_ENABLED");

        if(StringUtils.isBlank(sms_enabled) || !sms_enabled.toLowerCase().equals("true")) {
            LOGGER.info("SMS disabled, add JEASE_SMS_ENABLED = true");
            return;
        }

        String smshost = new String("");
        String smsport = new String("5000");
        String smsuser = new String("");
        String smspass = new String("");


        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("");
            LOGGER.info( "1" );
            javax.jms.Connection connection = connectionFactory.createConnection();
            connection.start();
            LOGGER.info( "2" );
            javax.jms.Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            LOGGER.info( "3" );
            javax.jms.Destination destination = session.createQueue("");
            LOGGER.info( "4" );
            javax.jms.MessageProducer messageProducer = session.createProducer(destination);
            javax.jms.MapMessage message = session.createMapMessage();
            LOGGER.info( "5" );
            message.setString("", ani);
            message.setString("", text);
            message.setString("", smshost);
            message.setString("", smsport);
            message.setString("", smsuser);
            message.setString("", smspass);

            LOGGER.info( "6" );
            messageProducer.send(message);
            LOGGER.info( "7" );
            session.close();
            connection.close();
            LOGGER.info( "8" );
        }
        catch (Exception e) {
            LOGGER.warn("error : Caught: " + e.getMessage(), e);
        }

        LOGGER.info( "9" );
    }

}
