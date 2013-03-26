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
package org.owasp.esapi;

import org.owasp.esapi.codecs.Codec;


/**
 * The Encoder interface contains a number of methods for decoding input and encoding output
 * so that it will be safe for a variety of interpreters. To prevent
 * double-encoding, callers should make sure input does not already contain encoded characters
 * by calling canonicalize. Validator implementations should call canonicalize on user input
 * <b>before</b> validating to prevent encoded attacks.
 * <p>
 * All of the methods must use a "whitelist" or "positive" security model.
 * For the encoding methods, this means that all characters should be encoded, except for a specific list of
 * "immune" characters that are known to be safe.
 * <p>
 * The Encoder performs two key functions, encoding and decoding. These functions rely
 * on a set of codecs that can be found in the org.owasp.esapi.codecs package. These include:
 * <ul><li>CSS Escaping</li>
 * <li>HTMLEntity Encoding</li>
 * <li>JavaScript Escaping</li>
 * <li>MySQL Escaping</li>
 * <li>Oracle Escaping</li>
 * <li>Percent Encoding (aka URL Encoding)</li>
 * <li>Unix Escaping</li>
 * <li>VBScript Escaping</li>
 * <li>Windows Encoding</li></ul>
 * <p>
 * 
 * @author Jeff Williams (jeff.williams .at. aspectsecurity.com) <a
 *         href="http://www.aspectsecurity.com">Aspect Security</a>
 * @since June 1, 2007
 */
public interface Encoder {
	
	/**
	 * Standard character sets
	 */

	/**  
	 * @deprecated Use {@link EncoderConstants#CHAR_LOWERS} instead
	 */
	@Deprecated
	public final static char[] CHAR_LOWERS = EncoderConstants.CHAR_LOWERS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_UPPERS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_UPPERS = EncoderConstants.CHAR_UPPERS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_DIGITS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_DIGITS = EncoderConstants.CHAR_DIGITS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_SPECIALS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_SPECIALS = EncoderConstants.CHAR_SPECIALS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_LETTERS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_LETTERS = EncoderConstants.CHAR_LETTERS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_ALPHANUMERICS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_ALPHANUMERICS = EncoderConstants.CHAR_ALPHANUMERICS;
	
	
	/**
	 * Password character set, is alphanumerics (without l, i, I, o, O, and 0)
	 * selected specials like + (bad for URL encoding, | is like i and 1,
	 * etc...)
	 * @deprecated Use {@link EncoderConstants#CHAR_PASSWORD_LOWERS} instead
	 */
	@Deprecated
	public final static char[] CHAR_PASSWORD_LOWERS = EncoderConstants.CHAR_PASSWORD_LOWERS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_PASSWORD_UPPERS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_PASSWORD_UPPERS = EncoderConstants.CHAR_PASSWORD_UPPERS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_PASSWORD_DIGITS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_PASSWORD_DIGITS = EncoderConstants.CHAR_PASSWORD_DIGITS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_PASSWORD_SPECIALS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_PASSWORD_SPECIALS = EncoderConstants.CHAR_PASSWORD_SPECIALS;
    /**
	 * @deprecated Use {@link EncoderConstants#CHAR_PASSWORD_LETTERS} instead
	 *
	 */
	@Deprecated
	public final static char[] CHAR_PASSWORD_LETTERS = EncoderConstants.CHAR_PASSWORD_LETTERS;
    

	/**
	 * Encode input for use in a SQL query, according to the selected codec 
	 * (appropriate codecs include the MySQLCodec and OracleCodec).
	 * 
	 * This method is not recommended. The use of the PreparedStatement 
	 * interface is the preferred approach. However, if for some reason 
	 * this is impossible, then this method is provided as a weaker 
	 * alternative. 
	 * 
	 * The best approach is to make sure any single-quotes are double-quoted.
	 * Another possible approach is to use the {escape} syntax described in the
	 * JDBC specification in section 1.5.6.
	 * 
	 * However, this syntax does not work with all drivers, and requires
	 * modification of all queries.
	 * 
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/guide/jdbc/getstart/statement.html">JDBC Specification</a>
	 *  
	 * @param codec 
	 * 		a Codec that declares which database 'input' is being encoded for (ie. MySQL, Oracle, etc.)
	 * @param input 
	 * 		the text to encode for SQL
	 * 
	 * @return input encoded for use in SQL
	 */
	String encodeForSQL(Codec codec, String input);
}
