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
package org.owasp.esapi.reference;

import org.owasp.esapi.Encoder;
import org.owasp.esapi.codecs.Codec;


/**
 * Reference implementation of the Encoder interface. This implementation takes
 * a whitelist approach to encoding, meaning that everything not specifically identified in a
 * list of "immune" characters is encoded.
 * 
 * @author Jeff Williams (jeff.williams .at. aspectsecurity.com) <a
 *         href="http://www.aspectsecurity.com">Aspect Security</a>
 * @since June 1, 2007
 * @see org.owasp.esapi.Encoder
 */
public class DefaultEncoder implements Encoder {

    private static volatile Encoder singletonInstance;

    public static Encoder getInstance() {
        if ( singletonInstance == null ) {
            synchronized ( DefaultEncoder.class ) {
                if ( singletonInstance == null ) {
                    singletonInstance = new DefaultEncoder();
                }
            }
        }
        return singletonInstance;
    }
	/**
	 *  Character sets that define characters (in addition to alphanumerics) that are
	 * immune from encoding in various formats
	 */
	private final static char[] IMMUNE_SQL = { ' ', '{', '}' };
	private final static char[] IMMUNE_OS = { '-' };
	
	
	/**
	 * Instantiates a new DefaultEncoder
	 */
	public DefaultEncoder() {
	}

	
	/**
	 * {@inheritDoc}
	 */
	public String encodeForSQL(Codec codec, String input) {
	    if( input == null ) {
	    	return null;
	    }
	    return codec.encode(IMMUNE_SQL, input);
	}

	/**
	 * {@inheritDoc}
	 */
	public String encodeForOS(Codec codec, String input) {
	    if( input == null ) {
	    	return null;	
	    }
	    return codec.encode( IMMUNE_OS, input);
	}
}
