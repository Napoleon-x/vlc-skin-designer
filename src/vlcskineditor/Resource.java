/*****************************************************************************
 * Resource.java
 *****************************************************************************
 * Copyright (C) 2007 Daniel Dreibrodt
 *
 * This file is part of VLC Skin Editor
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

import javax.swing.*;
import javax.swing.tree.*;
/**
 * Abstract superclass representing a Bitmap, SubBitmap or Font
 * @author Daniel Dreibrodt
 */
public abstract class Resource {
  
  /** Value should be either Bitmap or Font */
  public String id;
  public String type;
  /** Represents the skin to which the resource belongs */
  public Skin s;
  public Resource() {
  }  
  /** Show a dialog to modify the resource's parameters */
  public abstract void showOptions();  
  /** Creates the XML code representing the resource */
  public abstract String returnCode();
  /** Creates a DefaultMutableTreeNode to be displayed in the resources tree */
  public abstract DefaultMutableTreeNode getTreeNode();  
}