/**
 * OWASP Enterprise Security API (ESAPI)
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * Enterprise Security API (ESAPI) project. For details, please see
 * <a href="http://www.owasp.org/index.php/ESAPI">http://www.owasp.org/index.php/ESAPI</a>.
 *
 * Copyright (c) 2007 - The OWASP Foundation
 * 
 * The ESAPI is published by OWASP under the BSD license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jeff Williams <a href="http://www.aspectsecurity.com">Aspect Security</a>
 * @created 2007
 */
package org.owasp.esapi.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * EnterpriseSecurityException is the base class for all security related exceptions. You should pass in the root cause
 * exception where possible. Constructors for classes extending EnterpriseSecurityException should be sure to call the
 * appropriate super() method in order to ensure that logging and intrusion detection occur properly.
 * <P>
 * All EnterpriseSecurityExceptions have two messages, one for the user and one for the log file. This way, a message
 * can be shown to the user that doesn't contain sensitive information or unnecessary implementation details. Meanwhile,
 * all the critical information can be included in the exception so that it gets logged.
 * <P>
 * Note that the "logMessage" for ALL EnterpriseSecurityExceptions is logged in the log file. This feature should be
 * used extensively throughout ESAPI implementations and the result is a fairly complete set of security log records.
 * ALL EnterpriseSecurityExceptions are also sent to the IntrusionDetector for use in detecting anomalous patterns of
 * application usage.
 * <P>
 * @author Jeff Williams (jeff.williams@aspectsecurity.com)
 */
public class EnterpriseSecurityException extends Exception {

    protected static final long serialVersionUID = 1L;

    /** The logger. */
    protected static final Logger logger = LoggerFactory.getLogger(EnterpriseSecurityException.class);

    /**
     *
     */
    protected String logMessage = null;

    /**
     * Instantiates a new security exception.
     */
    protected EnterpriseSecurityException() {
        // hidden
    }
    
    /**
     * Returns message meant for display to users
     *
     * Note that if you are unsure of what set this message, it would probably
     * be a good idea to encode this message before displaying it to the end user.
     * 
     * @return a String containing a message that is safe to display to users
     */
    public String getUserMessage() {
        return getMessage();
    }

    /**
     * Returns a message that is safe to display in logs, but may contain
     * sensitive information and therefore probably should not be displayed to
     * users.
     * 
     * @return a String containing a message that is safe to display in logs,
     * but probably not to users as it may contain sensitive information.
     */
    public String getLogMessage() {
        return logMessage;
    }

}
