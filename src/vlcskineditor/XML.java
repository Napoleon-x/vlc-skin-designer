/*****************************************************************************
 * XML.java
 *****************************************************************************
 * Copyright (C) 2006 Daniel Dreibrodt
 *
 * This file is part of __PACKAGE_NAME__
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/

package vlcskineditor;

import org.w3c.dom.Node;

/**
 * XML Handler
 * @author Daniel Dreibrodt
 */
public class XML {
  
  
  /**
   * Returns the value of a xml attribute from a given line of xml code
   * @deprecated Now the DOM model is used
   * @param line  The XML Code
   * @param field The name of the attribute whose value is to be retrieved
   * @return      The attribute's value
   */
  public static String getValue(String line, String field) {    
    int value_start = line.indexOf(" "+field+"=\"") + field.length() +3;
    int value_end = line.indexOf("\"",value_start);
    return line.substring(value_start,value_end);
  }
  
  /**
   * Returns the value of a xml attribute from a given line of xml code as an integer
   * @deprecated Now the DOM model is used
   * @param line
   * @param field
   * @return
   */
  public static int getIntValue(String line, String field) {
    int i = 0;
    try {
      i = Integer.parseInt(getValue(line,field).trim());
    }
    catch (Exception e) {
      System.err.println("Could not parse int from getValue("+line+","+field+"): "+getValue(line,field));
    }
    return i;
  }
  
  /**
   * Returns the value of a xml attribute from a given line of xml code as a boolean value
   * @deprecated Now the DOM model is used
   * @param line
   * @param field
   * @return
   */
  public static boolean getBoolValue(String line, String field) {
    boolean b = false;
    try {
      b = Boolean.parseBoolean(getValue(line,field).trim());
    }
    catch (Exception e) {
      System.err.println("Could not parse boolean from getValue("+line+","+field+"): "+getValue(line,field));
    }
    return b;
  }
  
  /**
   * Gets the value of an XML node's attribute if it exists
   * @param n The XML node
   * @param name The name of the attribute
   * @param oldvalue The value which is returned if the attribute is not set in the given node
   * @return If the attribute is set, the attribute's value is returned. Otherwise the given old value.
   */
  public static String getStringAttributeValue(Node n, String name, String oldvalue) {
    if(n.getAttributes().getNamedItem(name)!=null) return n.getAttributes().getNamedItem(name).getNodeValue();
    else return oldvalue;
  }
  
  /**
   * Gets the value of an XML node's attribute as an integer if it exists
   * @param n The XML node
   * @param name The name of the attribute
   * @param oldvalue The value which is returned if the attribute is not set in the given node
   * @return If the attribute is set, the attribute's value is returned. Otherwise the given old value.
   */
  public static int getIntAttributeValue(Node n, String name, int oldvalue) {
    try {
      if(n.getAttributes().getNamedItem(name)!=null) return Integer.parseInt(n.getAttributes().getNamedItem(name).getNodeValue());
    } catch(NumberFormatException ex) {
      System.err.println("Tried to get a node's attribute as an integer, although it is no integer. It's value is "+n.getAttributes().getNamedItem(name).getNodeValue());
    }
    return oldvalue;
  }
  
  /**
   * Gets the value of an XML node's attribute as a boolean value if it exists
   * @param n The XML node
   * @param name The name of the attribute
   * @param oldvalue The value which is returned if the attribute is not set in the given node
   * @return If the attribute is set, the attribute's value is returned. Otherwise the given old value.
   */
  public static boolean getBoolAttributeValue(Node n, String name, boolean oldvalue) {
    if(n.getAttributes().getNamedItem(name)!=null) return Boolean.parseBoolean(n.getAttributes().getNamedItem(name).getNodeValue());    
    else return oldvalue;
  }
}
