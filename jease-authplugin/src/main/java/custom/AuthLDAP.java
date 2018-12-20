package custom;

import jease.cms.domain.Content;
import jease.cms.domain.Role;
import jease.cms.domain.User;
import jease.cms.service.Authenticator;
import jease.cms.service.Users;
import jease.cms.web.filter.etag.ETagHashUtils;
import jfix.db4o.Database;
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

public class AuthLDAP extends Authenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ETagHashUtils.class);


    public static void saveuser(String login, String password) {
        User newuser = new User();

        newuser.setName("AD\\" + login);
        newuser.setLogin(login);
        newuser.setPassword(password);

        Database.save(newuser);
    }

    public static void updateuser(String login, String password) {
        User thisuser = Users.queryByLogin(login);

        //set new password
        thisuser.setPassword(password);

        Database.save(thisuser);
    }


    public static Boolean validateLogin(String userName, String userPassword) {
        Hashtable<String, String> env = new Hashtable<String, String>();

        String LDAP_SERVER = "ldap://";
        String LDAP_SERVER_PORT = "";
        String LDAP_BASE_DN = "admsk";
        String LDAP_BASE_SEARCH = "";

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_SERVER);
        env.put(Context.SECURITY_PRINCIPAL, LDAP_BASE_DN + '\\' + userName);
        env.put(Context.SECURITY_CREDENTIALS, userPassword);

        DirContext ctx;
        try {
            ctx = new InitialDirContext(env); //throw exception, if username-password not correct

            SearchControls ctrls = new SearchControls();
            ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            ctrls.setReturningAttributes(new String[]{"displayName", "memberOf"});
            NamingEnumeration<SearchResult> results = ctx.search(LDAP_BASE_SEARCH, "sAMAccountName=" + userName, ctrls);
            if (!results.hasMore()) {
                LOGGER.info("error - " + userName + " not found");
                throw new AuthenticationException("Principal name not found");
            }

            SearchResult result = results.next();

            String FIOstr = result.getAttributes().get("displayName").get().toString();
            LOGGER.info("FIOstr - " + userName + " ok");

            User thisuser = Users.queryByLogin(userName);
            if (thisuser != null && thisuser.getPassword().equals(userPassword) == false) {
                updateuser(userName, userPassword);
            } else {
                saveuser(userName, userPassword);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User identify(String login, String password) {
        LOGGER.info("identify - Hello - " + login);
        User usr = Users.queryByLogin(login, password);
        if (usr != null) {
            return Users.queryByLogin(login, password);
        }
        if (validateLogin(login,password)) {
            LOGGER.info("identify - validate - " + login + " OK");
        }
        return Users.queryByLogin(login, password);
    }

}