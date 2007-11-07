/*****************************************************************************
 * Group.java
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

package vlcskineditor.items;

import vlcskineditor.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;

/**
 * Represents a Group item
 * @author Daniel Dreibrodt
 */
public class Group extends Item implements ActionListener{
  
  /** The items contained in the group */
  public java.util.List<Item> items = new LinkedList<Item>();  
  JFrame frame = null;
  JTextField id_tf, x_tf, y_tf;
  JButton ok_btn, cancel_btn, help_btn;  
  
  /** Creates a new instance of Group */
  public Group(String xmlcode, Skin s_) {
    type = "Group";
    s = s_;
    String[] code = xmlcode.split("\n");
    if(code[0].indexOf("x=\"")!=-1) x = XML.getIntValue(code[0],"x");
    if(code[0].indexOf("y=\"")!=-1) y = XML.getIntValue(code[0],"y");
    if(code[0].indexOf("id=\"")!=-1) id = XML.getValue(code[0],"id");
    else id = "Unnamed group #"+s.getNewId();
    for(int i=1;i<code.length;i++) {
      if (code[i].startsWith("<!--")) {
        while(code[i].indexOf("-->")==-1) {
          i++;
        }
      }
      else if(code[i].startsWith("<Anchor")) items.add(new Anchor(code[i],s));
      else if(code[i].startsWith("<Button")) items.add(new Button(code[i],s));
      else if(code[i].startsWith("<Checkbox")) items.add(new Checkbox(code[i],s));
      else if(code[i].startsWith("<Image")) items.add(new Image(code[i],s));
      else if(code[i].startsWith("<Text")) items.add(new Text(code[i],s));
      else if(code[i].startsWith("<Video")) items.add(new Video(code[i],s));
      else if(code[i].startsWith("<RadialSlider")) items.add(new RadialSlider(code[i],s));
      else if(code[i].startsWith("<Playlist")) {
        String itemcode = code[i];
        i++;
        while(!code[i].startsWith("</Playlist>")) {          
          itemcode += "\n"+code[i];
          i++;
        }
        itemcode += "\n"+code[i];
        items.add(new Playtree(itemcode,s));        
      }
      else if(code[i].startsWith("<Playtree")) {
        String itemcode = code[i];
        i++;
        while(!code[i].startsWith("</Playtree>")) {          
          itemcode += "\n"+code[i];
          i++;
        }
        itemcode += "\n"+code[i];
        items.add(new Playtree(itemcode,s));        
      }
      else if(code[i].startsWith("<Slider")) {
        if(code[i].indexOf("/>")!=-1) {
          items.add(new Slider(code[i],s));
        }
        else {
          String itemcode = code[i];
          i++;
          while(!code[i].startsWith("</Slider>")) {          
            itemcode += "\n"+code[i];
            i++;
          }
          itemcode += "\n"+code[i];
          items.add(new Slider(itemcode,s));
        }                
      }
    }
    for(Item i:items) {
      i.setOffset(x,y);
    }
    created = true;
  }
  public Group(Skin s_) {
    type="Group";
    s=s_;
    id = "Unnamed group #"+s.getNewId();
    showOptions();    
    s.updateItems();        
  }
  public void update() {
    id=id_tf.getText();
    x=Integer.parseInt(x_tf.getText());
    y=Integer.parseInt(y_tf.getText());
    for(Item i:items) {
      i.setOffset(x,y);
    }
    s.updateItems();    
    s.expandItem(id);
    frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
    created = true;
  }
  public void showOptions() {
    if(frame==null) {
      frame = new JFrame("Group settings");
      frame.setResizable(false);
      frame.setLayout(new FlowLayout());
      if(!created) frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);
      JLabel id_l = new JLabel("ID*:");
      id_tf = new JTextField();      
      JLabel x_l = new JLabel("X:");
      x_tf = new JTextField();      
      x_tf.setDocument(new NumbersOnlyDocument());
      JLabel y_l = new JLabel("Y:");
      y_tf = new JTextField();      
      y_tf.setDocument(new NumbersOnlyDocument());
      ok_btn = new JButton("OK");
      ok_btn.addActionListener(this);
      ok_btn.setPreferredSize(new Dimension(70,25));
      cancel_btn = new JButton("Cancel");
      cancel_btn.addActionListener(this);
      cancel_btn.setPreferredSize(new Dimension(70,25));
      help_btn = new JButton("Help");
      help_btn.addActionListener(this);
      help_btn.setPreferredSize(new Dimension(70,25));
      
      JPanel general = new JPanel(null);
      general.add(id_l);
      general.add(id_tf);
      id_l.setBounds(5,15,75,24);
      id_tf.setBounds(85,15,150,24);
      general.add(x_l);
      general.add(x_tf);
      x_l.setBounds(5,45,75,24);
      x_tf.setBounds(85,45,150,24);
      general.add(y_l);
      general.add(y_tf);
      y_l.setBounds(5,75,75,24);
      y_tf.setBounds(85,75,75,24);      
      general.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "General Attributes"));
      general.setMinimumSize(new Dimension(240,110));
      general.setPreferredSize(new Dimension(240,110));
      general.setMaximumSize(new Dimension(240,110));
      frame.add(general);
      
      frame.add(ok_btn);
      frame.add(cancel_btn);
      frame.add(help_btn);      
      frame.add(new JLabel("* required attribute"));
      
      frame.setMinimumSize(new Dimension(250,180));
      frame.setPreferredSize(new Dimension(250,180));
      frame.setMaximumSize(new Dimension(250,180));
      
      frame.pack();
    }
    id_tf.setText(id);
    x_tf.setText(String.valueOf(x));
    y_tf.setText(String.valueOf(y));    
    frame.setVisible(true);
  }  
  public void actionPerformed(ActionEvent e) {
    if(e.getSource().equals(ok_btn)) {
      if(id_tf.getText().equals("")) {
        JOptionPane.showMessageDialog(frame,"Please enter a valid ID!","ID not valid",JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      else if(!id_tf.getText().equals(id)) {
        if(s.idExists(id_tf.getText())) {
          JOptionPane.showMessageDialog(frame,"The ID \""+id_tf.getText()+"\" already exists, please choose another one.","ID not valid",JOptionPane.INFORMATION_MESSAGE);
          return;
        }
      }
      frame.setVisible(false);
      update();
    }
    else if(e.getSource().equals(help_btn)) {
      Desktop desktop;
      if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
              desktop.browse(new java.net.URI("http://www.videolan.org/vlc/skins2-create.html#Group"));
            }
            catch (Exception ex) {
              JOptionPane.showMessageDialog(null,ex.toString(),ex.getMessage(),JOptionPane.ERROR_MESSAGE);    
            }
      }
      else {
        JOptionPane.showMessageDialog(null,"Could not launch Browser","Go to the following URL manually:\nhttp://www.videolan.org/vlc/skins2-create.html",JOptionPane.WARNING_MESSAGE);    
      }
    }
    else if(e.getSource().equals(cancel_btn)) {
      if(!created) {
        java.util.List<Item> l = s.getParentListOf(id);
        if(l!=null) l.remove(this);
      }
      frame.setVisible(false);
    }
  }
  public String returnCode() {
    String code = "<Group";    
    if (x!=X_DEFAULT) code+=" x=\""+String.valueOf(x)+"\"";
    if (y!=Y_DEFAULT) code+=" y=\""+String.valueOf(y)+"\"";    
    code+=">";
    //if (id!=ID_DEFAULT) code+="<!-- id=\""+id+"\" -->";
    for (int i=0;i<items.size();i++) {
      code+="\n"+items.get(i).returnCode();
    }
    code+="\n</Group>";
    return code;
  }
  public void draw(Graphics2D g) {     
     draw(g,offsetx,offsety);
  }
  public void draw(Graphics2D g,int x_,int y_) {    
    for(Item i:items) {
      i.draw(g,x+x,y+y_);
      i.setOffset(x+offsetx,y+offsety);
    }    
  }
  public boolean contains(int x_, int y_) {    
    return (x_>=x+offsetx && y_>=y+offsety);
  }
  public DefaultMutableTreeNode getTreeNode() {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode("Group: "+id);      
    for(int i=0;i<items.size();i++) {
      node.add(items.get(i).getTreeNode());
    }
    return node;
  }
  public Item getItem(String id_) {
    if(id.equals(id_)) return this;
    else {
      for(int x=0;x<items.size();x++) {
        Item i = items.get(x).getItem(id_);
        if (i!=null) return i;      
      }    
    }
    return null;
  }
  public java.util.List<Item> getParentListOf(String id_) {
    for(int x=0;x<items.size();x++) {
      Item i = items.get(x);
      if(i.id.equals(id_)) {        
        return items;        
      }
      if (i.type.equals("Group")||i.type.equals("Panel")) {
        java.util.List<Item> p = i.getParentListOf(id_);
        if (p!=null) return p;
      }
    }
    return null;
  }
  public Item getParentOf(String id_) {
    for(int x=0;x<items.size();x++) {
      Item i = items.get(x);
      if(i.id.equals(id_)) {        
        return this;        
      }
      Item it = i.getParentOf(id_);
      if (it!=null) return it;      
    }
    return null;
  }
}