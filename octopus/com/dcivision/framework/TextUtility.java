/*
 * @(#)TextUtility.java
 *
 * Copyright (c) 2003 DCIVision Ltd
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of DCIVision
 * Ltd ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with DCIVision Ltd.
 */
package com.dcivision.framework;

import java.awt.Color;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
  TextUtility.java

  Common text utility functions which used in systems.

    @author          Rollo Chan
    @company         DCIVision Limited
    @creation date   24/06/2003
    @version         $Revision: 1.70.2.8 $
*/

public class TextUtility {

  public static final String REVISION = "$Revision: 1.70.2.8 $";
  
  
  /**
   * An array of HTML tags that, in HTML, don't require closing tags. Note that
   * XHTML doesn't work this way.
   */
  public final static String[] SINGLE_TAGS = {"br", "p", "hr"};

  /**
   * br
   *
   * Convert line breaks to html <code>&lt;br&gt;</code> tag.
   *
   * @param s   The String to convert
   * @return    The converted string
   */
  public final static String br(String s) {
    s = noNull(s);

    StringBuffer str = new StringBuffer();

    for (int i = 0; i<s.length(); i++) {
      if (s.charAt(i)=='\n') {
        str.append("<br>");
      } else {
        str.append(s.charAt(i));
      }
    }

    return str.toString();
  }

  /**
   * closeTags
   *
   * Search through a String for any tags that have been opened and append
   * closing tags for those that have not been closed.
   *
   * @param str   A string possibly containing unclosed HTML tags
   * @return      The converted string
   */
  public final static String closeTags(String str) {
    Map openTags = new HashMap();
    str = noNull(str);

    boolean inTag = false;
    boolean inTagName = false;
    boolean inOpenTag = true;
    String tagName = "";
    List singleTags = Arrays.asList(SINGLE_TAGS);

    char[] strA = str.toCharArray();

    for (int i = 0; i<strA.length; i++) {
      char c = strA[i];

      if (!inTag) { // not in a tag
        if (c=='<') { // start of a tag
          // reset all state variables at start of each new tag
          inTag = true;
          inTagName = true;
          inOpenTag = true;
          tagName = "";
        }
      } else { // in a tag
        if (tagName.equals("") && (c=='/')) { // start of a close tag
          inOpenTag = false;
        } else if (inTagName && ((c==' ') || (c=='>') || (c=='/'))) { // end of the tagname or tag
          inTagName = false;

          if (inOpenTag && !singleTags.contains(tagName.toLowerCase())) {
            // count this tag in the list of open tags
            if (openTags.get(tagName)==null) {
              openTags.put(tagName, new Integer(1));
            } else {
              int tagCount = ((Integer) openTags.get(tagName)).intValue();
              openTags.put(tagName, new Integer(tagCount + 1));
            }
          } else { // in close tag
            // remove it from closetags
            if (openTags.get(tagName)!=null) {
              int tagCount = ((Integer)openTags.get(tagName)).intValue();

              if (tagCount>1) {
                openTags.put(tagName, new Integer(tagCount - 1));
              } else {
                openTags.remove(tagName);
              }
            }
          }

          if (c=='>') { // end of tag
            inTag = false;
          }
        } else if (inTagName) { // still in tag name
          tagName += c;
        } else if (c=='>') { // end of tag and there were attributes
          inTag = false;
        }
      }
    }

    // cycle through remaining open tags and close them
    Iterator openTagNames = openTags.keySet().iterator();
    StringBuffer closedString = new StringBuffer(str);

    while (openTagNames.hasNext()) {
      String openTagName = (String)openTagNames.next();

      for (int i = 0; i<((Integer)openTags.get(openTagName)).intValue(); i++) {
        closedString.append("</" + openTagName + ">");
      }
    }

    // return closed string
    return closedString.toString();
  }

  /**
   * colorToHex
   *
   * Convert Color to html hex string. (#012345)
   *
   * @param c   The Color to convert
   * @return    A string with a hexadecimal RGB encoding
   */
  public final static String colorToHex(java.awt.Color c) {
    String r = Integer.toHexString(c.getRed());
    String g = Integer.toHexString(c.getGreen());
    String b = Integer.toHexString(c.getBlue());

    if (r.length()<2) {
      r = '0' + r;
    }
    if (g.length()<2) {
      g = '0' + g;
    }
    if (b.length()<2) {
      b = '0' + b;
    }

    return '#' + r + g + b;
  }

  /**
   * decodeBytes
   *
   * Decode binary data from String using base64.
   *
   * @param str  The string going to be converted
   * @return     Byte array after decoded
   * @see #encodeBytes(byte[])
   */
 

  /**
   * decodeObject
   *
   * Decode Object from a String by decoding with base64 then deserializing.
   *
   * @param str   The string going to be converted
   * @return      Object after decoded
   * @see #encodeObject(java.lang.Object)
   */


  /**
   * Encode binary data into String using base64.
   *
   * @param data   The byte array going to be converted
   * @return       The string after encoded
   * @see #decodeBytes(java.lang.String)
   */
 
  /**
   * encodeObject
   *
   * Encode an Object to String by serializing it and encoding using base64.
   *
   * @param data   The object going to be converted
   * @return       The string after encoded
   * @see #decodeObject(java.lang.String)
   */


  /**
   * extractNumber
   *
   * Extract a number from a String.
   *
   * <h5>Example</h5>
   * <pre>
   *   " 12345"                      -&gt;     "12345"
   *   "hello123bye"                 -&gt;     "123"
   *   "a2b4c6 8 "                   -&gt;     "2468"
   *   " -22"                        -&gt;     "-22"
   *   "5.512"                       -&gt;     "5.512"
   *   "1.2.3.4"                     -&gt;     "1.234"
   *   ".2"                          -&gt;     "0.2"
   *   "-555.7"                      -&gt;     "-555.7"
   *   "-..6"                        -&gt;     "-0.6"
   *   "abc- dx.97 9"                -&gt;     "-0.979"
   *   "\u00A31,000,000.00 per year" -&gt;     "1000000.00"
   *   ""                            -&gt;     "0"
   *   "asdsf"                       -&gt;     "0"
   *   "123."                        -&gt;     "123"
   *   null                          -&gt;     "0"
   * </pre>
   *
   * @param in    Original String containing number to be extracted.
   * @return      String stripped of all non-numeric chars.
   * @see #parseInteger(String)
   * @see #parseLong(String)
   */
  public final static String extractNumber(String in) {
    if (in==null) {
      return "0";
    }

    StringBuffer result = new StringBuffer();
    boolean seenDot = false;
    boolean seenMinus = false;
    boolean seenNumber = false;

    for (int i = 0; i<in.length(); i++) {
      char c = in.charAt(i);

      if (c=='.') {
        // insert dot if not yet encountered
        if (!seenDot) {
          seenDot = true;

          if (!seenNumber) {
            result.append('0'); // padding zero if no number yet
          }

          result.append('.');
        }
      } else if (c=='-') {
        // insert minus sign if not yet encountered
        if (!seenMinus) {
          seenMinus = true;
          result.append('-');
        }
      } else if ((c=='0') || ((c>='1') && (c<='9'))) {
        // add number
        seenNumber = true;
        result.append(c);
      }
    }

    // remove trailing
    int length = result.length();

    if ((length>0) && (result.charAt(length-1)=='.')) {
      result.deleteCharAt(length - 1);
    }

    return (result.length()==0) ? "0" : result.toString(); // if nothing left, return 0
  }

    /**
     * Convert html hex string to Color. If the hexadecimal string is not
     * a valid character, <code>Color.black</code> is returned.
     * Only the first six hexadecimal characters are considered; any
     * extraneous values are discarded. Also, a leading "#", if any, is allowed
     * (and ignored).
     * @param color the String (in RGB hexadecimal format) to convert
     * @return the java.awt.Color
     */
  public final static Color hexToColor(String color) {
    try {
      if (color.charAt(0)=='#') {
        color = color.substring(1, 7);
      }

      int[] col = new int[3];

      for (int i = 0; i<3; i++) {
        col[i] = Integer.parseInt(color.substring(i * 2, (i * 2) + 2), 16);
      }

      return new Color(col[0], col[1], col[2]);
    } catch (Exception e) {
      return Color.black;
    }
  }

  /**
   * htmlEncode
   *
   * Escape html entity characters and high characters (eg "curvy" Word quotes).
   * Note this method can also be used to encode XML.
   *
   * @param s  The String to escape.
   * @return   The escaped string
   */
  public final static String htmlEncode(String s) {
    s = noNull(s);

    StringBuffer str = new StringBuffer();

    for (int j=0; j<s.length(); j++) {
      char c = s.charAt(j);

      // encode standard ASCII characters into HTML entities where needed
      if (c<'\200') {
        switch (c) {
          case '"':
            str.append("&quot;");
            break;
          case '&':
            str.append("&amp;");
            break;
          case '<':
            str.append("&lt;");
            break;
          case '>':
            str.append("&gt;");
          break;
            default:
            str.append(c);
        }
      } else if (c<'\377') { // encode 'ugly' characters (ie Word "curvy" quotes etc)
        String hexChars = new String("0123456789ABCDEF");
        int a = c % 16;
        int b = (c - a) / 16;
        String hex = "" + hexChars.charAt(b) + hexChars.charAt(a);
        str.append("&#x" + hex + ";");
      } else { // add other characters back in - to handle charactersets other than ascii
        str.append(c);
      }
    }

    return str.toString();
  }

  /**
   * hyperlink
   *
   * Convert all URLs and E-mail addresses in a string into hyperlinks.
   *
   * @param text  The block of text to hyperlink.
   * @return      The text with known uri formats hyperlinked
   * @see #hyperlink(String, String)
   */
 

  /**
   * hyperlink
   *
   * Convert all URLs and E-mail addresses in a string into hyperlinks.
   *
   * @param text    The block of text to hyperlink.
   * @param target  The target attribute to use for href (optional).
   * @return        The text with known uri formats hyperlinked
   * @see #linkEmail(String)
   * @see #linkURL(String)
   */

  /**
   * indent
   *
   * Indent a String with line-breaks.
   *
   * @param string       String to indent.
   * @param indentSize   Number of spaces to indent by. 0 will indent using a tab.
   * @param initialLine  Whether to indent initial line.
   * @return             Indented string.
   */
  public final static String indent(String string, int indentSize, boolean initialLine) {
    // Create indent String
    String indent;

    if (indentSize==0) {
      indent = "\t";
    } else {
      StringBuffer s = new StringBuffer();
      for (int i = 0; i<indentSize; i++) {
        s.append(' ');
      }
      indent = s.toString();
    }

    // Apply indent to input
    StringBuffer result = new StringBuffer();

    if (initialLine) {
      result.append(indent);
    }

    for (int i = 0; i<string.length(); i++) {
      char c = string.charAt(i);
      result.append(c);
      if (c=='\n') {
        result.append(indent);
      }
    }

    return result.toString();
  }

  /**
   * innerTrim
   *
   * Returns a string that has whitespace removed from
   * both ends of the String, as well as duplicate whitespace
   * removed from within the String.
   *
   * @param s   String going to be inner trimed
   * @return    String presentation of inner trimed string
   */
  public final static String innerTrim(String s) {
    StringBuffer b = new StringBuffer(s);
    int index = 0;

    while ((b.length()!=0) && (b.charAt(0)==' ')) {
      b.deleteCharAt(0);
    }

    while (index<b.length()) {
      if (Character.isWhitespace(b.charAt(index))) {
        if (((index + 1)<b.length()) && (Character.isWhitespace(b.charAt(index + 1)))) {
          b.deleteCharAt(index + 1);
          index--; // let's restart at this character!
        }
      }
      index++;
    }

    if (b.length()>0) {
      int l = b.length() - 1;

      if (b.charAt(l)==' ') {
        b.setLength(l);
      }
    }

    String result = b.toString();

    return result;
  }

  /**
   * join
   *
   * Join an Iteration of Strings together.
   *
   * <h5>Example</h5>
   * <pre>
   *   // get Iterator of Strings ("abc","def","123");
   *   Iterator i = getIterator();
   *   out.print( TextUtility.join(", ",i) );
   *   // prints: "abc, def, 123"
   * </pre>
   *
   * @param glue     Token to place between Strings.
   * @param pieces   Iteration of Strings to join.
   * @return         String presentation of joined Strings.
   */
  public final static String join(String glue, Iterator pieces) {
    StringBuffer s = new StringBuffer();

    while (pieces.hasNext()) {
      s.append(pieces.next().toString());
      if (pieces.hasNext()) {
        s.append(glue);
      }
    }

    return s.toString();
  }

  /**
   * join
   *
   * Join an array of Strings together.
   *
   * @param glue     Token to place between Strings.
   * @param pieces   Array of Strings to join.
   * @return         String presentation of joined Strings.
   * @see #join(String, java.util.Iterator)
   */
  public final static String join(String glue, String[] pieces) {
    return join(glue, Arrays.asList(pieces).iterator());
  }

  /**
   * join
   *
   * Join a Collection of Strings together.
   *
   * @param glue     Token to place between Strings.
   * @param pieces   Collection of Strings to join.
   * @return         String presentation of joined Strings.
   *
   * @see #join(String, java.util.Iterator)
   */
  public final static String join(String glue, Collection pieces) {
    return join(glue, pieces.iterator());
  }

  /**
   * leadingSpaces
   *
   * Finds all leading spaces on each line and replaces it with
   * an HTML space (&amp;nbsp;)
   *
   * @param s   String containing text to replaced with &amp;nbsp;
   * @return    The new string
   */
  public final static String leadingSpaces(String s) {
    s = noNull(s);

    StringBuffer str = new StringBuffer();
    boolean justAfterLineBreak = true;

    for (int i = 0; i<s.length(); i++) {
      if (justAfterLineBreak) {
        if (s.charAt(i)==' ') {
          str.append("&nbsp;");
        } else {
          str.append(s.charAt(i));
          justAfterLineBreak = false;
        }
      } else {
        if (s.charAt(i)=='\n') {
          justAfterLineBreak = true;
        }

        str.append(s.charAt(i));
      }
    }

    return str.toString();
  }

  /**
   * left
   *
   * Returns the leftmost n chars of the string.  If n is larger than the length of the string,
   * return the whole string unchanged.
   *
   * @param s  The string to operate on.
   * @param n  The number of chars to return.
   */
  public final static String left(String s, int n) {
    if (n>=s.length()) {
      return s;
    }

    return s.substring(0, n);
  }

  /**
   * linkEmail
   *
   * Wrap all email addresses in specified string with href tags.
   *
   * @param str   The block of text to check.
   * @return      String The block of text with all email addresses placed in href tags.
   */
  
  /**
   * linkURL
   *
   * Wrap all urls ('http://', 'www.', and 'ftp://') in specified string with href tags.
   *
   * @param str  The block of text to check.
   * @return     String The block of text with all url's placed in href tags.
   */
  public final static String linkURL(String str) {
    return linkURL(str, null);
  }

  /**
   * linkURL
   *
   * Wrap all urls ('http://', 'https://', 'www.', and 'ftp://') in specified string with href tags.
   *
   * @param str     The block of text to check.
   * @param target  The target to use for the href (optional).
   * @return        String The block of text with all url's placed in href tags.
   */
  public final static String linkURL(String str, String target) {
    int lastEndIndex = -1; //Stores the index position, within the whole string, of the ending char

    //of the last URL found.
    String targetString = ((target==null) || (target.trim().length()==0)) ? "" : (" target=\"" + target.trim() + "\"");

    while (true) {
      //We find the positions of the next occurrence of 'http://', 'https://', 'www.' and 'ftp://'
      //strings.  We take the nearest occurrence (ie. smallest of the 3 values) and convert
      //that to hyperlink. We then repeat the process, starting from the end of that hyperlink...
      int httpIndex = str.toLowerCase().indexOf("http://", lastEndIndex + 1);
      int httpsIndex = str.toLowerCase().indexOf("https://", lastEndIndex + 1);
      int wwwIndex = str.toLowerCase().indexOf("www.", lastEndIndex + 1);
      int ftpIndex = str.toLowerCase().indexOf("ftp://", lastEndIndex + 1);

      if ((httpIndex==-1) && (httpsIndex==-1) && (wwwIndex==-1) && (ftpIndex==-1)) {
        break;
      } else {
        //Get the index of the nearest occurrence...
        int tempLinkStartIndex = Math.min(httpIndex, httpsIndex);

        if (tempLinkStartIndex==-1) {
          if (httpIndex==-1) {
            tempLinkStartIndex = httpsIndex;
          } else {
            tempLinkStartIndex = httpIndex;
          }
        }

        int tempLinkStartIndex2 = Math.min(tempLinkStartIndex, wwwIndex);

        if (tempLinkStartIndex2==-1) {
          if (tempLinkStartIndex==-1) {
            tempLinkStartIndex2 = wwwIndex;
          } else {
            tempLinkStartIndex2 = tempLinkStartIndex;
          }
        }

        int linkStartIndex = Math.min(tempLinkStartIndex2, ftpIndex);

        if (linkStartIndex==-1) {
          if (tempLinkStartIndex2==-1) {
            linkStartIndex = ftpIndex;
          } else {
            linkStartIndex = tempLinkStartIndex2;
          }
        }

        //Get the whole URL...
        //We move forward and add each character to the URL string until we encounter
        //an invalid URL character (we assume that the URL ends there).
        int linkEndIndex = linkStartIndex;
        String urlStr = "";

        while (true) {
          // if char at linkEndIndex is '&' then we look at the next 4 chars
          // to see if they make up "&amp;" altogether. This is the html coded
          // '&' and will pretty much stuff up an otherwise valid link becos of the ';'.
          // We therefore have to remove it before proceeding...
          if (str.charAt(linkEndIndex)=='&') {
            if ((linkEndIndex + 5)<=str.length()) {
              String all5Chars = str.substring(linkEndIndex, linkEndIndex + 5);

              if (all5Chars.equalsIgnoreCase("&amp;")) {
                str = removeAndInsert(str, linkEndIndex, linkEndIndex + 5, "&");
              }
            }
          }

          if (isValidURLChar(str.charAt(linkEndIndex))) {
            urlStr += str.charAt(linkEndIndex);
            linkEndIndex++;

            if (linkEndIndex==str.length()) { //Reached end of str...
              break;
            }
          } else {
            break;
          }
        }

        //Decrement linkEndIndex back by 1 to reflect the real ending index position of the URL...
        linkEndIndex--;

        //If the last chars of urlStr is a '.', ':', '-', '/' or '~' then we exclude those chars.
        //The '.' at the end could be just a fullstop to a sentence and we don't want
        //that to be part of an url (which would then be invalid).
        //Pretty much the same for the other symbols - we don't want them at the end of any url
        //cos' this would stuff the url up.
        while (true) {
          char lastChar = urlStr.charAt(urlStr.length() - 1);

          if ((lastChar=='.') || (lastChar==':') || (lastChar=='-') || (lastChar=='~')) {
            urlStr = urlStr.substring(0, urlStr.length() - 1);
            linkEndIndex--;
          } else {
            break;
          }
        }

        //if the URL had a '(' before it, and has a ')' at the end, trim the last ')' from the url
        //ie '(www.opensymphony.com)' => '(<a href="http://www.openymphony.com/">www.opensymphony.com</a>)'
        char lastChar = urlStr.charAt(urlStr.length() - 1);

        if (lastChar==')') {
          if ((linkStartIndex>0) && ('('==(str.charAt(linkStartIndex - 1)))) {
            urlStr = urlStr.substring(0, urlStr.length() - 1);
            linkEndIndex--;
          }
        }

        // we got the URL string, now we validate it and convert it into a hyperlink...
        String urlToDisplay = htmlEncode(urlStr);

        if (urlStr.toLowerCase().startsWith("www.")) {
          urlStr = "http://" + urlStr;
        }

        if (verifyUrl(urlStr)) {
          //Construct the hyperlink for the url...
          String urlLink = "<a href=\"" + urlStr + "\"" + targetString + ">" + urlToDisplay + "</a>";

          //Remove the original urlStr from str and put urlLink there instead...
          str = removeAndInsert(str, linkStartIndex, linkEndIndex + 1, urlLink);

          //Set lastEndIndex to reflect the position of the end of urlLink
          //within the whole string...
          lastEndIndex = (linkStartIndex - 1) + urlLink.length();
        } else {
          //lastEndIndex is different from the one above cos' there's no
          //<a href...> tags added...
          lastEndIndex = (linkStartIndex - 1) + urlStr.length();
        }
      }
    }

    return str;
  }

  /**
   * Create <li> elements in a piece of plain text;
   * Will convert lines starting with - or *. It might have been
   * useful for the people writing earlier versions of this module's Javadocs.
   * <tt>;)</tt>
   * @param str A string, possibly containing a plaintext "list"
   * @return a converted string
   */
  public final static String list(String str) {
    str = noNull(str);

    String strToRet = "";
    boolean inList = false;

    if (str.startsWith("-") || str.startsWith("*")) {
      // if first char is '-' or '*' then put a linebreak before str so that the following
      // code will take it as a list...
      str = '\n' + str;
    }

    for (int i = 0; i<str.length(); i++) {
      // look at whether the character at i is '\n'...
      if (str.charAt(i)=='\n') {
        // if so, look at the next char and see whether it's '-' or '*'...
        if (i!=(str.length() - 1)) {
          if ((str.charAt(i + 1)=='-') || (str.charAt(i + 1)=='*')) {
            // if so, and if we are not currently in a list, we start a list...
            if (!inList) {
              strToRet += "<ul>";
              inList = true;
            } else {
              // if we are already in a list, then the previous point is unclosed...
              strToRet += "</li>";
            }

            // we add the <li> tag since a new point is started...
            strToRet += "<li>";
            i++; // since we've taken care of the '-' or '*' char already...
          } else {
            // if we are currently in a list, and we have a linebreak char but
            // no '-' or '*' after it, then that means the list is closed...
            if (inList) {
              strToRet += "</li></ul>";
              inList = false;
            } else {
              // if we are not currently in a list, simply add the linebreak
              // char to the string...
              strToRet += str.charAt(i);
            }
          }
        } else {
          // there is no next char since this char is the last char in the string...
          // if we are in a list, then we close the list (and the current point in the list)
          // by adding </li> and </ul> tags...
          if (inList) {
            strToRet += "</li></ul>";
          }
        }
      } else {
        // not a linebreak char - simply add the char to the string...
        strToRet += str.charAt(i);
      }
    }

    // if we're still in a list, then we close it...
    if (inList) {
      strToRet += "</li></ul>";
    }

    return strToRet;
  }

  /**
   * noNull
   *
   * Return <code>string</code>, or <code>defaultString</code> if
   * <code>string</code> is <code>null</code> or <code>""</code>.
   * Never returns <code>null</code>.
   *
   * <p>Examples:</p>
   * <pre>
   * // prints "hello"
   * String s=null;
   * System.out.println(TextUtility.noNull(s,"hello");
   *
   * // prints "hello"
   * s="";
   * System.out.println(TextUtility.noNull(s,"hello");
   *
   * // prints "world"
   * s="world";
   * System.out.println(TextUtility.noNull(s, "hello");
   * </pre>
   *
   * @param string the String to check.
   * @param defaultString The default string to return if <code>string</code> is <code>null</code> or <code>""</code>
   * @return <code>string</code> if <code>string</code> is non-empty, and <code>defaultString</code> otherwise
   * @see #stringSet(java.lang.String)
   */
  public final static String noNull(String string, String defaultString) {
    return (stringSet(string)) ? string : defaultString;
  }

  /**
   * noNull
   *
   * Return <code>string</code>, or <code>""</code> if <code>string</code>
   * is <code>null</code>. Never returns <code>null</code>.
   * <p>Examples:</p>
   * <pre>
   * // prints 0
   * String s=null;
   * System.out.println(TextUtility.noNull(s).length());
   *
   * // prints 1
   * s="a";
   * System.out.println(TextUtility.noNull(s).length());
   * </pre>
   * @param string the String to check
   * @return a valid (non-null) string reference
   */
  public final static String noNull(String string) {
    return noNull(string, "");
  }

  /**
   * parseBoolean
   *
   * Convert a String to an boolean.
   * Accepts: 1/0, yes/no, true/false - case insensitive. If the value does
   * not map to "true,", <code>false</code> is returned.
   *
   * @param in  String to be parsed.
   * @return    Boolean representation of String.
   */
  public final static boolean parseBoolean(String in) {
    in = noNull(in);

    if (in.length()==0) {
      return false;
    }

    switch (in.charAt(0)) {
      case '1':
      case 'y':
      case 'Y':
      case 't':
      case 'T':
        return true;
    }

    return false;
  }

  /**
   * parseBooleanObj
   *
   * Convert a String to an Boolean object.
   * Accepts: 1/0, yes/no, true/false - case insensitive. If the value does
   * not map to "true,", <code>false</code> is returned.
   *
   * @param in  String to be parsed.
   * @return    Boolean object representation of String.
   */
  public final static Boolean parseBooleanObj(String in) {
    in = noNull(in);

    if (in.length()==0) {
      return(null);
    }

    switch (in.charAt(0)) {
      case '1':
      case 'y':
      case 'Y':
      case 't':
      case 'T':
        return new Boolean(true);
    }

    return new Boolean(false);
  }

  /**
   * Given 3 Strings representing the the year, month and day, return a Date object.
   * This is only valid for Gregorian calendars.
   *
   * <p>If the day cannot be determined, 1st will be used.
   * If the month cannot be determined, Jan will be used.</p>
   *
   * @param year   Year : 4 digit
   * @param month  Month : 1 or 2 digit (1=jan, 2=feb, ...) or name (jan, JAN, January, etc). If null, default is Jan.
   * @param day    Day of month : 1 or 2 digit, prefix will be stripped (1, 30, 05, 3rd). If null, default is 1st.
   */
  public final static Date parseDate(String year, String month, String day) {
        year = noNull(year);
        month = noNull(month);
        day = noNull(day);

        int y = parseInteger(year);
        int m = parseInteger(month) - 1;
        int d = parseInteger(extractNumber(day));

        if (m==-1) { // month was not a number, parse text

            // @todo i18n support
            if (month.length()<3) {
                month = month + "   ";
            }

            String str = month.toLowerCase().substring(0, 3);

            if (str.equals("jan")) {
                m = 0;
            } else if (str.equals("feb")) {
                m = 1;
            } else if (str.equals("mar")) {
                m = 2;
            } else if (str.equals("apr")) {
                m = 3;
            } else if (str.equals("may")) {
                m = 4;
            } else if (str.equals("jun")) {
                m = 5;
            } else if (str.equals("jul")) {
                m = 6;
            } else if (str.equals("aug")) {
                m = 7;
            } else if (str.equals("sep")) {
                m = 8;
            } else if (str.equals("oct")) {
                m = 9;
            } else if (str.equals("nov")) {
                m = 10;
            } else if (str.equals("dec")) {
                m = 11;
            } else {
                m = 0; // jan
            }
        }

        if (d==0) {
            d = 1; // if no day, set to 1st
        }

        Calendar cal = Calendar.getInstance();
        cal.set(y, m, d);

        return cal.getTime();
    }

  /**
   * parseDouble
   *
   * Convert a String to a double.
   *
   * @param in String containing number to be parsed.
   * @return Double value of number or 0 if error.
   * @see #extractNumber(String)
   */
  public final static double parseDouble(String in) {
    double d = 0;

    try {
      d = Double.parseDouble(in);
    } catch (Exception e) {
    }

    return d;
  }

  /**
   * parseDoubleObj
   *
   * Convert a String to a double object.
   *
   * @param in String containing number to be parsed.
   * @return Double object of number or 0 if error.
   * @see #extractNumber(String)
   */
  public final static Double parseDoubleObj(String in) {
    try {
      return(new Double(Double.parseDouble(in)));
    } catch (Exception e) {
      return(null);
    }
  }


  /**
   * parseFloat
   *
   * Convert a String to a float.
   *
   * @param in String containing number to be parsed.
   * @return Float value of number or 0 if error.
   * @see #extractNumber(String)
   */
  public final static float parseFloat(String in) {
    float f = 0;

    try {
      f = Float.parseFloat(in);
    } catch (Exception e) {
    }

    return f;
  }

  /**
   * parseFloatObj
   *
   * Convert a String to a float object.
   *
   * @param in String containing number to be parsed.
   * @return Float object of number or 0 if error.
   * @see #extractNumber(String)
   */
  public final static Float parseFloatObj(String in) {
    try {
      return(new Float(Float.parseFloat(in)));
    } catch (Exception e) {
      return(null);
    }
  }

  /**
   * parseInteger
   *
   * Convert a String to an int. Truncates numbers if it's a float string;
   * for example, 4.5 yields a value of 4.
   *
   * @param in  String containing number to be parsed.
   * @return    Integer value of number or 0 if error.
   * @see #extractNumber(String)
   */
  public final static int parseInteger(String in) {
    int i = 0;

    try {
      i = Integer.parseInt(in);
    } catch (Exception e) {
      i = (int) parseFloat(in);
    }

    return i;
  }

  /**
   * parseIntegerObj
   *
   * Convert a String to an integer object. Truncates numbers if it's a float
   * string; for example, 4.5 yields a value of 4.
   *
   * @param in  String containing number to be parsed.
   * @return    Integer object of number or 0 if error.
   * @see #extractNumber(String)
   */
  public final static Integer parseIntegerObj(String in) {
    try {
      return(new Integer(Integer.parseInt(in)));
    } catch (Exception e) {
      return(null);
    }
  }

  /**
   * parseLong
   *
   * Convert a String to a long. Truncates numbers if it's a float or double string;
   * for example, 4.5 yields a value of 4.
   *
   * @param in String containing number to be parsed.
   * @return Long value of number or 0 if error.
   * @see #extractNumber(String)
   */
  public final static long parseLong(String in) {
    long l = 0;

    try {
      l = Long.parseLong(in);
    } catch (Exception e) {
      l = (long) parseDouble(in);
    }

    return l;
  }

  /**
   * parseLongObj
   *
   * Convert a String to a long object. Truncates numbers if it's a float or
   * double string; for example, 4.5 yields a value of 4.
   *
   * @param in String containing number to be parsed.
   * @return Long object of number or 0 if error.
   * @see #extractNumber(String)
   */
  public final static Long parseLongObj(String in) {
    try {
      return(new Long(Long.parseLong(in)));
    } catch (Exception e) {
      return(null);
    }
  }

  /**
   * parseTimestamp
   *
   * Call this function to parse a date String into java.sql.Timestamp. It
   * returns null if the date format ("dd-mm-yyyy" currently) is not recognized.
   *
   * @param in String containing date time to be parsed.
   * @return Timestamp object after parsing.
   */
 
  /**
   * parseTimestamp
   *
   * Call this function to parse a date String into java.sql.Timestamp. It
   * returns null if the date format ("dd-mm-yyyy" currently) is not recognized.
   *
   * @param in String containing date time to be parsed.
   * @return Timestamp object after parsing.
   */

  
  /**
    * parseDBDateTimeToTimeStamp
    *
    * Call this function to parse a date String from the datepicker into 
    * java.sql.Timestamp. It returns null if the db date format is not recognized.
    *
    * @param in String containing date time to be parsed.
    * @return Timestamp object after parsing.
    */
  
 
  
  /**
   * htmlToPlainText
   *
   * Converts html code to plain text.
   * <ul>
   * <li>escapes appropriate characters
   * <li>puts in line breaks
   * <li>hyperlinks link and email addresses
   * </ul>
   *
   * @param str - String containing the plain text.
   * @return the escaped string
   */
  public final static String htmlToPlainText(String str,boolean addLineBreak) {      
    String BR_REPLACER="!!xxx!!";
    StringReader reader = null;
    String text = null;
    //remove \n since it's significant for plain text
    if(addLineBreak){
      str = replaceString(str,"<tr>",BR_REPLACER);
      str = replaceString(str,"<br>",BR_REPLACER);
    }
    str = replaceString(str,"\n","");
 
    try{
      reader = new StringReader(str);
      HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
      HTMLDocument dsd = (HTMLDocument)htmlEditorKit.createDefaultDocument();
      dsd.putProperty("IgnoreCharsetDirective", Boolean.TRUE);

      htmlEditorKit.read(reader, dsd, 0);            
      text = dsd.getText(0, dsd.getLength());  
      text = replaceString(text,"\n","");
      if(addLineBreak){
        text = replaceString(text,BR_REPLACER,"\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try { reader.close(); } catch (Exception ignore) {} finally { reader = null; }
    }      
    return text;      
  }  

  /**
   * plainTextToHtml
   *
   * Converts plain text to html code.
   * <ul>
   * <li>escapes appropriate characters
   * <li>puts in line breaks
   * <li>hyperlinks link and email addresses
   * </ul>
   *
   * @param str - String containing the plain text.
   * @return the escaped string
   */


  /**
   * plainTextToHtml
   *
   * Converts plain text to html code.
   * <ul>
   * <li>escapes appropriate characters
   * <li>puts in line breaks
   * <li>hyperlinks link and email addresses
   * </ul>
   *
   * @param str      String containing the plain text.
   * @param target   Target for href tags (optional).
   * @return         The escaped string
   */

  /**
   * removeAndInsert
   *
   * Removes part of the original string starting at removeAndInsertStart and ending at removeEnd.
   * Then insert another string at the same position where the part was removed (ie. at removeAndInsertStart).
   *
   * @param str                   The original string
   * @param removeAndInsertStart  The index within the original string at which removal is to start
   * @param removeEnd             The index within the original string at which removal is to end (exclusive -
   *                              ie. does not remove the character at that index)
   * @param insertStr             The string to be inserted
   * @return                      The string after conversion
   */
  public final static String removeAndInsert(String str, int removeAndInsertStart, int removeEnd, String insertStr) {
    //Take the part of the str before removeAndInsertStart, the part including and after removeEnd, and add
    //the insertStr between those two parts...
    String partBefore = str.substring(0, removeAndInsertStart);
    String partAfter = str.substring(removeEnd);

    str = partBefore + insertStr + partAfter;

    return str;
  }

  /**
   * slashes
   *
   * Escape chars that need slashes in front of them.
   *
   * @param s  The String to add escape characters to
   * @return   The converted String
   */
  public final static String slashes(String s) {
    s = noNull(s);

    StringBuffer str = new StringBuffer();
    char[] chars = s.toCharArray();

    for (int i = 0; i<chars.length; i++) {
      if ((chars[i]=='\\') || (chars[i]=='\"') || (chars[i]=='\'')) {
        str.append('\\');
      }

      str.append(chars[i]);
    }

    return str.toString();
  }

  /**
   * stringSet
   *
   * Check whether <code>string</code> has been set to
   * something other than <code>""</code> or <code>null</code>.
   *
   * @param string  The <code>String</code> to check
   * @return        A boolean indicating whether the string was non-empty (and non-null)
   */
  public final static boolean stringSet(String string) {
    return (string!=null) && !"".equals(string);
  }

  /**
   * trimToEndingChar
   *
   * Trim a String to the specified length. However, if the cutoff
   * point is in the middle of a sentence (remember that a String can contain many
   * sentences), trim the string such that it ends with an ending char, which is
   * defined in the isEndingChar() method.
   *
   * @param str  String to trim.
   * @param len  Length to which string is to be trimmed.
   * @return     The trimmed string
   */
  public final static String trimToEndingChar(String str, int len) {
    boolean inTag = false;
    boolean anyTags = false;
    String result = "";
    int goodChars = 0;
    int lastEndingCharPos = -1;

    if (str.length()<len) {
      return str;
    }

    char[] strA = str.toCharArray();

    for (int i = 0; i<strA.length; i++) {
      if ((strA[i]=='<') && !inTag) {
        anyTags = true;
        inTag = true;
      }

      if ((strA[i]=='>') && inTag) {
        inTag = false;
      }

      if (!inTag) {
        // loop through ending chars
        // if this char==ending char, record last seen
        if (isEndingChar(strA[i])) {
          lastEndingCharPos = i;
        }

        goodChars++;
      }

      result += strA[i];

      if (goodChars==len) {
        break;
      }
    }

    // ok, now we have a string consisting of a bunch of tags and tagless characters.
    // we now see whether the last char is an ending char (by comparing lastEndingCharPos+1 with
    // the length of the string). If it is not, then it means that the end of the string is the middle
    // of some sentence in the original string. In this case, we would have to trim the string further so
    // that the end of the string corresponds to the end of some sentence, but keeping the length of the string
    // closest to the specified len. We do this by utilising lastEndingCharPos...
    if ((lastEndingCharPos + 1)!=result.length()) {
      if (lastEndingCharPos!=-1) {
        result = result.substring(0, lastEndingCharPos + 1);
      } else {
        // there aren't any ending chars...
        // best thing we could do is to trim the result to the nearest word...
        // if there aren't any spaces in the result, then we can do nothing at all.
        int spacePos = result.lastIndexOf(' ');

        if (spacePos!=-1) {
          result = result.substring(0, spacePos);
        }
      }
    }

    if (anyTags) {
      return closeTags(result); //Put closing tags and return the result...
    }

    return result;
  }

  /**
   * verifyEmail
   *
   * Verify that the given string is a valid email address.
   * "Validity" in this context only means that the address conforms
   * to the correct syntax (not if the address actually exists).
   *
   * @param email The email address to verify.
   * @return a boolean indicating whether the email address is correctly formatted.
   */
 

  /**
   * verifyUrl
   *
   * Verify That the given String is in valid URL format.
   *
   * @param url The url string to verify.
   * @return A boolean indicating whether the URL seems to be incorrect.
   */
  public final static boolean verifyUrl(String url) {
    if (url==null) {
      return false;
    }

    if (url.startsWith("https://")) {
       // URL doesn't understand the https protocol, hack it
       url = "http://" + url.substring(8);
    }

    try {
      new URL(url);
      return true;
    } catch (MalformedURLException e) {
      return false;
    }
  }

  /**
   * wrapParagraph
   *
   * Wrap paragraphs in html <code>&lt;p&gt;</code> tags.
   * Paragraphs are seperated by blank lines.
   *
   * @param s  The String to reformat
   * @return   The reformatted string
   */
  public final static String wrapParagraph(String s) {
    s = noNull(s);

    StringBuffer result = new StringBuffer();

    char lastC = 0;
    char thisC = 0;

    result = new StringBuffer(replaceString(result.toString(), "\r", ""));

    for (int i = 0; i<s.length(); i++) {
      thisC = s.charAt(i);

      if (thisC=='\n') {
        result.append("<br/>\n");
      } else {
        result.append(thisC);
      }

      lastC = thisC;
    }

    return result.toString();
  }

  /**
   * isEndingChar
   *
   * Determine if <code>c</code> is a valid end-of-sentence character.
   * Currently, only English characters are included.
   *
   * @param c  A character to consider
   * @return   Whether the character is a valid end-of-sentence mark in English
   */
  private final static boolean isEndingChar(char c) {
    return ((c=='.') || (c=='!') || (c==',') || (c=='?'));
  }

  private final static boolean isValidEmailChar(char c) {
    return (((c>='A') && (c<='Z')) || ((c>='a') && (c<='z')) || (c=='_') || (c=='-') || (c=='.') || ((c>='0') && (c<='9')));
  }

  private final static boolean isValidURLChar(char c) {
    if (isValidEmailChar(c)) {
        return true;
    }

    if ((c=='+') || (c=='?') || (c=='&') || (c=='%') || (c=='/') || (c=='~') || (c==':') || (c=='=') || (c=='#') || (c=='_') || (c=='(') || (c==')') || (c==',') || (c=='!')) {
        return true;
    }

    return false;
  }

  /**
   * splitString
   *
   * Returns string array which after spliting the input string.
   *
   * @param   src   String which going to be splitted.
   * @param   div   Delimitator for split.
   * @return        String array after split.
   */
 
  /**
   * splitString
   *
   * Returns string array which after spliting the input string using space as delimitator.
   *
   * @param   src String which going to be splitted using space as delimitator.
   * @return      String array after split.
   */
 

  /**
   * replaceString
   *
   * Returns string after replacing the original string to the target string, no matter
   * the ori string wrapped by space or charater.
   *
   * @param   src Source string which going to be replaced
   * @param   ori Original string in the source string
   * @param   tar Target string which replaces the original string
   * @return      String after replacement taken place.
   */
  public static String replaceString(String src, String ori, String tar) {
    String result = null;

    if (src!=null && ori!=null && tar!=null){
      try {
        StringBuffer source = new StringBuffer(src);
        int appearedIndex = 0;
        int oriLength = ori.length();
        int tarLength = tar.length();
        int byPassingIndex = 0;
        // If you got exception here, please check your JDK version. JDK 1.4 is needed for this method.
        while (source.indexOf(ori,byPassingIndex)!=-1) {
          appearedIndex = source.indexOf(ori,byPassingIndex);
          if (ori.equals(source.substring(appearedIndex,appearedIndex+oriLength))) {
            source = source.replace(appearedIndex,appearedIndex+oriLength,tar);
            byPassingIndex = appearedIndex+tarLength;
          } else {
            byPassingIndex = appearedIndex;
          }
        }
        result = source.toString();
      } catch (Exception e) {
      e.printStackTrace();
      }
    }
    return result;
  }

  /**
   * replaceString
   *
   * Returns string after replacing the original string to the prefix + suffix string, no matter   
   *
   * @param   src Source string which going to be replaced
   * @param   ori Original string in the source string
   * @param   tar Target string which replaces the original string
   * @param   ignoreSrcCase if true, then the original string case will be ignored
   * @return      String after replacement taken place.
   */

  /**
   * wrap string for html
   * 
   * @param src
   * @param length
   * @return string
   */
  public static String wrapString(String src,int length){
    String result = null;
    if(src!=null){
      StringBuffer buffer = new StringBuffer("");
      String[] splite = src.split(" ");
      for(int i=0;i<splite.length;i++){
        String tempStr = splite[i];
        if(tempStr.length()>length){
          String tempStr2 = null;
          tempStr2 = tempStr.substring(0,length)+" ";
          tempStr2 += wrapString(tempStr.substring(length),length);
          buffer.append(tempStr2).append(" ");
        }else{
          buffer.append(splite[i]).append(" ");
        }
      }
      result = buffer.toString();
    }
    return result;
  }
  
  /**
   * Truncation long file name.
   * 
   * @param src
   * @param length
   * @return string
   */
  public static String subFileName(String fileName ,int length){
    if(Utility.isEmpty(fileName)){
      return "";
    }
    String fileSuffix = "";
    if(fileName.lastIndexOf(".")!=-1){
      fileSuffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
    }
    return fileName.substring(0,length)+"..."+fileSuffix;
  }
  
  /**
   * formatDate
   *
   * Returns the formatted date according to the input pattern.
   *
   * @param   date The date going to be formatted.
   * @param   formatPattern The format pattern.
   * @return  The formatted date according to the input pattern.
   */
  public static String formatDate(java.util.Date date, String formatPattern) {
    if (date==null) {
      return "";
    }
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(formatPattern, new java.util.Locale("en", "US"));
    return(formatter.format(date));
  }

  /**
   * formatIntegerObj
   *
   * Convert a integer (java.sql.Integer) representation into string.
   *
   * @param    intValue    Integer representation
   * @return   String      The String format after convertion
   */
  public static String formatIntegerObj(Integer intValue) {
    if (intValue==null) {
      return "";
    }
    return(intValue.toString());
  }
  /**
   * formatLongObj
   *
   * Convert a Long (java.lang.Long) representation into string.
   *
   * @param    intValue    Long representation
   * @return   String      The String format after convertion
   */  
  public static String formatLongObj(Long intValue) {
    if (intValue==null) {
      return "";
    }
    return(intValue.toString());
  }
  /**
   * formatNumberWithZeroPrefix
   *
   * Convert a integer (java.sql.Integer) representation into string with Zeros prefix.
   *
   * @param    numOfZero    Num of Zero for the integer as prefix
   * @param    value        The target value to be format
   * @return   String      The String format after convertion
   */
  public static String formatNumberWithZeroPrefix(int numOfZero, Integer value) {
    return(formatNumberWithZeroPrefix(numOfZero, value, false));
  }

  /**
   * formatNumberWithZeroPrefix
   *
   * Convert a integer (java.sql.Integer) representation into string with Zeros prefix.
   *
   * @param    numOfZero    Num of Zero for the integer as prefix
   * @param    value        The target value to be format
   * @param    grouping     Property of having the ',' format; true for "10, 000", false for "10000"
   * @return   String      The String format after convertion
   */
  public static String formatNumberWithZeroPrefix(int numOfZero, Integer value, boolean grouping) {
    NumberFormat df = NumberFormat.getInstance();
    df.setGroupingUsed(grouping);
    df.setMinimumIntegerDigits(numOfZero);
    return(df.format(value));
  }

  /**
   * formatFileSize
   *
   * Convert a int representation into the label of file size.
   *
   * @param    intValue    Int value representation
   * @return   String      The String format after convertion
   */
  public static String formatFileSize(long size) {
    StringBuffer str = new StringBuffer();
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(1);
    nf.setMinimumFractionDigits(1);

    if (size <= 0) {
      return "0 bytes";
    }
    
    if (size < 1024) {
      str.append(size).append(" bytes");
    }
    else if (size < 1048576) {
      str.append(nf.format(size / 1024.0)).append(" KB");
    }
    else if (size < 1073741824) {
      str.append(nf.format((size / 1024.0) / 1024.0)).append(" MB");
    }
    else {
      str.append(nf.format(size / (1024.0 * 1024 * 1024))).append(" GB");
    }

    return str.toString();
  }

  /**
 * formatStorageSize
 *
 * Convert a double representation into the label of folder size.
 *
 * @param    size      size of the file representation
 * @param    label     label for indiate the base is KB or MB
 * @return   String    The String format after convertion
 */


  public static String formatStorageSize(Double size, String label){
    StringBuffer str = new StringBuffer();
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(1);

    if (size!=null){
      if (("KB").equals(label)){
         if (size.doubleValue() < 1024) {
             str.append(nf.format(size.doubleValue())).append(" KB");
         }
         else if(size.doubleValue() < 1048576) {
             str.append(nf.format(size.doubleValue()/1024)).append(" MB");
         }
         else {
             str.append(nf.format((size.doubleValue()/1024.0)/1024.0)).append(" GB");
         }
      }//end if kb
      else if(("MB").equals(label)){
          if (size.doubleValue() >= 1024) {
              str.append(nf.format(size.doubleValue() / 1024.0)).append(" GB");
          } else {
          str.append(nf.format(size.doubleValue())).append(" MB");
          }
      } //end if label

    }//end if size!=null

    return str.toString();


  }
  
  /**
   * formatFileSizeToRange
   * convert a file size to range for sql size compare.
   * @param    size      size of the file representation
   * @param    label     label for indiate the base is KB or MB or GB
   * @return   String    The String format after convertion
   */
  public static String[] formatFileSizeToRange(String size, String label) {
    String[] range = null;
    if(!Utility.isEmpty(size)) {
      range = new String[3];
      BigDecimal basic = new BigDecimal("1024");
      BigDecimal sizeBigDecimal = new BigDecimal(size);
      BigDecimal rate = new BigDecimal("0.05");
      if("MB".equals(label)) {
        basic = basic.multiply(basic);
      } else if("GB".equals(label)) {
        basic = basic.multiply(basic).multiply(basic);
      }
      //default is KB.
      rate = basic.multiply(rate);
      //min value.
      range[0] = basic.multiply(sizeBigDecimal).subtract(rate).toString();
      //max value.
      range[1] = basic.multiply(sizeBigDecimal).add(rate).toString();
      //original value.
      range[2] = basic.multiply(sizeBigDecimal).toString();
    }
    
    return range;
  }

  /**
    * formatFileNamee
    *
    * Convert passed in document full path into file name
    *
    * @param    intValue    Int value representation
    * @return   String      The String format after convertion
   */
  public static String formatFileName(String fullPath) {
    String fileName = null;
    if (fullPath.lastIndexOf("/") != -1 || fullPath.lastIndexOf("\\") != -1) {
      if (fullPath.lastIndexOf("\\") != -1) {
        fileName = fullPath.substring(fullPath.lastIndexOf("\\") + 1, fullPath.length());
      } else {
        fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1, fullPath.length());
      }
    } else {
      fileName = fullPath;
    }
    return fileName;
  }

  /**
   * formatInteger
   *
   * Convert a integer (java.sql.Integer) representation into string.
   *
   * @param    intValue    Int value representation
   * @return   String      The String format after convertion
   */
  public static String formatInteger(int intValue) {
    return(new Integer(intValue).toString());
  }

  /**
   * formatFloatObj
   *
   * Convert a float (java.sql.Float) representation into string.
   *
   * @param    floatValue  Float representation
   * @return   String      The String format after convertion
   */
  public static String formatFloatObj(Float floatValue) {
    if (floatValue==null) {
      return "";
    }
    return(floatValue.toString());
  }

  /**
   * formatFloat
   *
   * Convert a float (java.sql.Float) representation into string.
   *
   * @param    floatValue  Float value representation
   * @return   String      The String format after convertion
   */
  public static String formatFloat(int floatValue) {
    return(new Float(floatValue).toString());
  }

  /**
   * formatDoubleObj
   *
   * Convert a double (java.sql.Double) representation into string.
   *
   * @param    doubleValue Double representation
   * @return   String      The String format after convertion
   */
  public static String formatDoubleObj(Double doubleValue) {
    if (doubleValue==null) {
      return "";
    }
    return(doubleValue.toString());
  }

  /**
   * formatDouble
   *
   * Convert a double (java.sql.Double) representation into string.
   *
   * @param    doubleValue Double value representation
   * @return   String      The String format after convertion
   */
  public static String formatDouble(int doubleValue) {
    return(new Double(doubleValue).toString());
  }

  /**
   * formatTimestamp
   *
   * Call this function to format a java.sql.Timestamp into a String
   * with format "dd/mm/yyyy HH:mm"
   *
   * @param    timestamp  Timestamp value representation
   * @return   String     The String format after convertion
   */
  

  /**
   * formatTimestampToDate
   *
   * Call this function to format a java.sql.Timestamp into a String
   * with format "dd/mm/yyyy"
   *
   * @param    timestamp  Timestamp value representation
   * @return   String     The String format after convertion
   */
  
  
  
	
  
  /**
      * formatTimestampToIndex
      *
      * Call this function to format a java.sql.Timestamp into a String
      * with format "yyyyMMddhhmmss" for lucene indexing
      *
      * @param    timestamp  Timestamp value representation
      * @return   String     The String format after convertion
      */
  public static String formatTimestampToIndex(java.sql.Timestamp timestamp) {
    if (timestamp==null) {
      return "";
    }
      
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.US);
    String dateStr = sdf.format(timestamp);
    return dateStr;
   }
     
  /**
   * getDateFormat
   *
   * @return  The default date format.
   */


  /**
   * getTimeFormat
   *
   * @return  The default time format.
   */
 
  /**
   * getDateTimeFormat
   *
   * @return  The default date and time format.
   */
  

  /**
   * getDBDateTimeFormat
   *
   * @return  The default date and time format.
   */
 
  /**
   * getDBDateFormatString
   *
   * @return  The default DB date format string.
   */
 

  /**
   * getURLEncodeInUTF8
   *
   * @return  The the string encoded in UTF format
   */
  public static String getURLEncodeInUTF8(String str) {
    if (str == null) {
      return null;
    }
    String encodedStr = new String();
    String hexStr = null;
    try {
      byte [] arrStrByte = str.getBytes("UTF-8");
      for (int i = 0; i < arrStrByte.length; i++) {
        if ((arrStrByte[i] & 0x80) != 0x80){
          encodedStr += URLEncoder.encode(new String(arrStrByte, i, 1), "UTF-8");
        } else {
          hexStr = Integer.toHexString(arrStrByte[i] & 0xFF).toUpperCase();
          encodedStr = encodedStr + "%" + hexStr;
        }
      }
    } catch (UnsupportedEncodingException uee) {
    }
    return encodedStr;
  }

  /**
   * resolveSingleQuote
   *
   * To with place a single quote with double single quote for where clause.
   *
   * @param searchText     The search string.
   * @return               The result string.
   */
  public static String resolveSingleQuote(String searchText) {
    if (Utility.isEmpty(searchText)) {
      return(searchText);
    }
    StringBuffer sb = new StringBuffer(searchText);
    StringBuffer sbNewText = new StringBuffer();
    while (sb.indexOf("'") >= 0) {
      int index = sb.indexOf("'");
      sb.insert(index + 1, "'");
      sbNewText.append(sb.substring(0, index + 2));
      sb = new StringBuffer(sb.substring(index + 2, sb.length()));
    }
    sbNewText.append(sb);
    return sbNewText.toString();
  }

  /**
   * summarizeContent
   *
   * Summarize the content to given number of words.
   *
   * @param content      The original content.
   * @param noOfWords    The no. of words of summary.
   * @return             The summary in String format.
   */
  public static String summarizeContent(String content, int noOfWords) {
    String formatedContent = "";
    int noOfChars = 0;
    int count = 0;

    try {
      if (content == null) {
        return formatedContent;
      }
      if (content.length() > 1) {
        do {
          char ch = content.charAt(noOfChars);
          if (ch == ' ') {
            count = count + 1;
          }
          noOfChars = noOfChars + 1;
        }
        while ((count < noOfWords) && (content.length() > noOfChars));

        if (content.length() > noOfChars) {
          formatedContent = content.substring(0, noOfChars);
        } else {
          formatedContent = content;
        }
      } else {
        if (Utility.isEmpty(content)) {
          formatedContent = "-";
        } else {
          formatedContent = content;
        }
      }
    } catch (Exception e) {
e.printStackTrace();
    }

    return formatedContent;
  }

  public static String escapeJSString(String inStr) {
    if (inStr == null) {
      return("");
    }
    String output = inStr;
    output = TextUtility.replaceString(output, "\r", "");
    //output = TextUtility.replaceString(output, "\n", "\\n");
    output = TextUtility.replaceString(output, "\\", "\\u005C");
    output = TextUtility.replaceString(output, "'", "\\u0027");
    output = TextUtility.replaceString(output, "\"", "\\u0022");
    return(output);
  }

 
  
  

 

  public static String insertStringAtOffset(String srcStr, String inStr, int offset) {
    String result = "";
    if (inStr == null) {
      inStr = "\n";
    }
    for (int i = 0; i < srcStr.length(); i++) {
      if (i != 0 && i % offset == 0) {
        result += inStr;
      }
      result += srcStr.charAt(i);
    }
    return(result);
  }

  public static String getExtension(String filename) {
    if (filename == null) {
      return null;
    }
    int iDot = filename.lastIndexOf(".");
    String sExt = filename.substring(iDot + 1);
    if (!Utility.isEmpty(sExt)) {
      sExt = sExt.toUpperCase();
    }
    return(sExt);
  }

  /**
    Description: This will added one more day base on the date string input and return
                 the string based on the db date format
    @parameter   datePickerDate   Date string get from datepicker
                 noOfDateAdded    no of date added
    @return      Date string after added
  */
  
   
  /**
    Description: This will format the system format date string into 
                 database format date string
    @parameter   sysDate   Date string in system date format
    @return      Date string in db date format
  */
  
 
  /**
   * Description:This will convert Big5 to GB or GB to Big5 
   * @param s
   * @return
   */
 
  
  
  /**
   * Check the @param contains search operand words or not.
   * @param field
   * @return
   */
  public static boolean checkContainsOperandChar(String field){
    boolean hasOperandChar = false;
    if(field!=null){
      StringTokenizer st = new StringTokenizer(field);
      while (st.hasMoreTokens()) {
        String str = st.nextToken().trim();
        if(("AND".equals(str))||("OR".equals(str))||(">=".equals(str))||(">".equals(str))
          ||("<=".equals(str))||("<".equals(str))||("NOT".equals(str))){
            hasOperandChar=true;
            break;
        }
      }
    }
    return hasOperandChar;
  }
  
  /**
   * delete this repeat character
   * 
   * @param repeatStr The String contain the repeat character
   * @param separator The list separator
   * @return noRepeatStr The String has not contain the repeat character
   */
  public static String deleteRepeatStr(String repeatStr, String separator) {
    if (repeatStr == null) {
      return null;
    }
    StringBuffer noRepeatStr = new StringBuffer();
    String[] strElement = repeatStr.split(separator);
    List strlist = new ArrayList();
    for (int i = 0; i < strElement.length; i++) {
      if (!strlist.contains(strElement[i])) {
        strlist.add(strElement[i]);
      }
    }
    for (int i = 0; i < strlist.size(); i++) {
      noRepeatStr.append(strlist.get(i));
      if (i != strlist.size() - 1) {
        noRepeatStr.append(separator);
      }
    }
    return noRepeatStr.toString();
  }   

  /**
   * test string contains DBC case
   * 
   * @param testStr The String must be testing.
   * @return boolean true means contains DBC case.
   */
  
  
  /**
   * test string contains SBC case
   * 
   * @param testStr The String must be testing.
   * @return boolean true means contains SBC case.
   */

  
  /**
   * Wrap text by line of length.
   * 
   * @param text
   * @param lineLength
   * @return String.
   */
  public static String wrapText(String text,int lineLength){
    if(Utility.isEmpty(text)){
      return "";
    }
    StringBuffer result = new StringBuffer();
    for(int i=0;i<text.length();i+=lineLength){
      if(i+lineLength > text.length()){
        result.append(text.substring(i, text.length())+"<br/>");
      }else{
        result.append(text.substring(i, i+lineLength)+"<br/>");
      }
    }
    return result.toString();
  }
  
  /**
   * Check character byte length.
   * @param src
   * @param maxLen
   * @return boolean:true(normal),false(too long).
   */
  public static boolean checkMaxLength(String src,int maxLen){
    if(Utility.isEmpty(src)){
      return true;
    }
    String regex = "[\\u4E00-\\u9FA5]";
    String tempSrc = src.replaceAll(regex, "xxx");
    if(tempSrc.length()>maxLen){
      return false;
    }
    return true;
  }
}
