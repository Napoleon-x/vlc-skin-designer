/*****************************************************************************
 * Video.java
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
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;

/**
 * Video item
 * @author Daniel Dreibrodt
 */
public class Video extends Item implements ActionListener{
  
  public final int WIDTH_DEFAULT = 0;
  public final int HEIGHT_DEFAULT = 0;
  public final boolean AUTORESIZE_DEFAULT = true;
  
  public int width = WIDTH_DEFAULT;
  public int height = HEIGHT_DEFAULT;
  public boolean autoresize = AUTORESIZE_DEFAULT;
  
  JFrame frame = null;
  JTextField id_tf, x_tf, y_tf, help_tf, visible_tf, width_tf, height_tf;
  JComboBox lefttop_cb, rightbottom_cb, xkeepratio_cb, ykeepratio_cb, autoresize_cb;
  JButton visible_btn, ok_btn, cancel_btn, help_btn;
  
  /** Creates a new instance of Video */
  public Video(String xmlcode, Skin s_) {
    s = s_;
    if(xmlcode.indexOf(" width=\"")!=-1) width = XML.getIntValue(xmlcode,"width");
    if(xmlcode.indexOf(" height=\"")!=-1) height = XML.getIntValue(xmlcode,"height");
    if(xmlcode.indexOf(" autoresize=\"")!=-1) autoresize = XML.getBoolValue(xmlcode,"autoresize");
    if(xmlcode.indexOf(" x=\"")!=-1) x = XML.getIntValue(xmlcode,"x");
    if(xmlcode.indexOf(" y=\"")!=-1) y = XML.getIntValue(xmlcode,"y");
    if(xmlcode.indexOf(" id=\"")!=-1) id = XML.getValue(xmlcode,"id");
    else id = "Unnamed video #"+s.getNewId();
    if(xmlcode.indexOf(" lefttop=\"")!=-1) lefttop = XML.getValue(xmlcode,"lefttop");
    if(xmlcode.indexOf(" rightbottom=\"")!=-1) rightbottom = XML.getValue(xmlcode,"rightbottom");
    if(xmlcode.indexOf(" xkeepratio=\"")!=-1) xkeepratio = XML.getBoolValue(xmlcode,"xkeepratio");
    if(xmlcode.indexOf(" ykeepratio=\"")!=-1) ykeepratio = XML.getBoolValue(xmlcode,"ykeepratio");
    if(xmlcode.indexOf(" visible=\"")!=-1) visible = XML.getValue(xmlcode,"visible");
    created = true;
  }
  public Video(Skin s_) {
    s = s_;    
    id = "Unnamed video #"+s.getNewId();
    showOptions();
  }
  public void update() {
    id = id_tf.getText();
    x = Integer.parseInt(x_tf.getText());
    y = Integer.parseInt(y_tf.getText());
    lefttop = lefttop_cb.getSelectedItem().toString();
    rightbottom = rightbottom_cb.getSelectedItem().toString();
    xkeepratio = Boolean.parseBoolean(xkeepratio_cb.getSelectedItem().toString());
    ykeepratio = Boolean.parseBoolean(ykeepratio_cb.getSelectedItem().toString());
    visible = visible_tf.getText();
    help = help_tf.getText();
    
    width = Integer.parseInt(width_tf.getText());  
    height = Integer.parseInt(height_tf.getText());  
    autoresize = Boolean.parseBoolean(autoresize_cb.getSelectedItem().toString());
    
    s.updateItems();    
    frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
    created = true;
  }
  public void showOptions() {
    if(frame==null) {
      frame = new JFrame("Video settings");
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
      String[] align_values = {"lefttop", "leftbottom", "righttop", "rightbottom"};
      JLabel lefttop_l = new JLabel("Lefttop:");
      lefttop_cb = new JComboBox(align_values);
      lefttop_cb.setToolTipText("Indicate to which corner of the Layout the top-left-hand corner of this item is attached, in case of resizing.");
      JLabel rightbottom_l = new JLabel("Rightbottom:");
      rightbottom_cb = new JComboBox(align_values);
      rightbottom_cb.setToolTipText("Indicate to which corner of the Layout the bottom-right-hand corner of this item is attached, in case of resizing.");
      Object[] bool_values = { true, false };
      JLabel xkeepratio_l = new JLabel("Keep X Ratio:");
      xkeepratio_cb = new JComboBox(bool_values);
      xkeepratio_cb.setToolTipText("When set to true, the behaviour of the horizontal resizing is changed. For example, if initially the space to the left of the control is twice as big as the one to its right, this will stay the same during any horizontal resizing. The width of the control stays constant.");
      JLabel ykeepratio_l = new JLabel("Keep Y Ratio:");
      ykeepratio_cb = new JComboBox(bool_values);
      ykeepratio_cb.setToolTipText("When set to true, the behaviour of the vertical resizing is changed. For example, if initially the space to the top of the control is twice as big as the one to its bottom, this will stay the same during any vertical resizing. The height of the control stays constant.");
      JLabel visible_l = new JLabel("Visibility:");
      visible_tf = new JTextField();
      visible_btn = new JButton("",s.m.help_icon);
      visible_btn.addActionListener(this);
      JLabel help_l = new JLabel("Help Text:");
      help_tf = new JTextField();
      help_tf.setToolTipText("Help text for the current control. The variable '$H' will be expanded to this value when the mouse hovers the current control.");
      
      JLabel width_l = new JLabel("Width:");
      width_tf = new JTextField();
      width_tf.setDocument(new NumbersOnlyDocument());
      JLabel height_l = new JLabel("Height:");
      height_tf = new JTextField();
      height_tf.setDocument(new NumbersOnlyDocument());
      JLabel autoresize_l = new JLabel("Autoresize:");
      autoresize_cb = new JComboBox(bool_values);
      autoresize_cb.setToolTipText("Indicate whether the layout should be automatically resized to fit the dimensions of the played video.");
      
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
      y_tf.setBounds(85,75,150,24);      
      general.add(lefttop_l);
      general.add(lefttop_cb);
      lefttop_l.setBounds(5,105,75,24);
      lefttop_cb.setBounds(85,105,150,24);
      general.add(rightbottom_l);
      general.add(rightbottom_cb);
      rightbottom_l.setBounds(5,135,75,24);
      rightbottom_cb.setBounds(85,135,150,24);
      general.add(xkeepratio_l);
      general.add(xkeepratio_cb);
      xkeepratio_l.setBounds(5,165,75,24);
      xkeepratio_cb.setBounds(85,165,150,24);
      general.add(ykeepratio_l);
      general.add(ykeepratio_cb);
      ykeepratio_l.setBounds(5,195,75,24);
      ykeepratio_cb.setBounds(85,195,150,24);
      general.add(visible_l);
      general.add(visible_tf);
      general.add(visible_btn);
      visible_l.setBounds(5,225,75,24);
      visible_tf.setBounds(85,225,120,24);
      visible_btn.setBounds(210,225,24,24);
      general.add(help_l);
      general.add(help_tf);
      help_l.setBounds(5,255,75,24);
      help_tf.setBounds(85,255,150,24);
      general.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "General Attributes"));
      general.setMinimumSize(new Dimension(240,285));
      general.setPreferredSize(new Dimension(240,285));
      general.setMaximumSize(new Dimension(240,285));
      frame.add(general);
      
      JPanel video = new JPanel(null);
      video.add(width_l);
      video.add(width_tf);
      width_l.setBounds(5,15,75,24);
      width_tf.setBounds(85,15,150,24);
      video.add(height_l);
      video.add(height_tf);
      height_l.setBounds(5,45,75,24);
      height_tf.setBounds(85,45,150,24);
      video.add(autoresize_l);
      video.add(autoresize_cb);
      autoresize_l.setBounds(5,75,75,24);
      autoresize_cb.setBounds(85,75,150,24);
      video.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Video Attributes"));
      video.setMinimumSize(new Dimension(240,105));
      video.setPreferredSize(new Dimension(240,105));
      video.setMaximumSize(new Dimension(240,105));
      frame.add(video);
      
      frame.add(ok_btn);
      frame.add(cancel_btn);
      frame.add(help_btn);      
      frame.add(new JLabel("* required attribute"));
      
      frame.setMinimumSize(new Dimension(250,460));
      frame.setPreferredSize(new Dimension(250,460));
      frame.setMaximumSize(new Dimension(250,460));
      
      frame.pack();
    }
    id_tf.setText(id);    
    x_tf.setText(String.valueOf(x));
    y_tf.setText(String.valueOf(y));
    lefttop_cb.setSelectedItem(lefttop);
    rightbottom_cb.setSelectedItem(rightbottom);
    xkeepratio_cb.setSelectedItem(xkeepratio);
    ykeepratio_cb.setSelectedItem(ykeepratio);
    visible_tf.setText(visible);
    help_tf.setText(help);
    
    width_tf.setText(String.valueOf(width));
    height_tf.setText(String.valueOf(height));
    autoresize_cb.setSelectedItem(autoresize);
    
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
      update();
      frame.setVisible(false);      
    }
    else if(e.getSource().equals(help_btn)) {
      Desktop desktop;
      if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
              desktop.browse(new java.net.URI("http://www.videolan.org/vlc/skins2-create.html#Video"));
            }
            catch (Exception ex) {
              JOptionPane.showMessageDialog(null,ex.toString(),ex.getMessage(),JOptionPane.ERROR_MESSAGE);    
            }
      }
      else {
        JOptionPane.showMessageDialog(null,"Could not launch Browser","Go to the following URL manually:\nhttp://www.videolan.org/vlc/skins2-create.html",JOptionPane.WARNING_MESSAGE);    
      }
    }
    else if(e.getSource().equals(visible_btn)) {
      Desktop desktop;
      if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
              desktop.browse(new java.net.URI("http://www.videolan.org/vlc/skins2-create.html#boolexpr"));
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
    String code = "<Video";
    if (id!=ID_DEFAULT) code+=" id=\""+id+"\"";
    if (x!=X_DEFAULT) code+=" x=\""+String.valueOf(x)+"\"";
    if (y!=Y_DEFAULT) code+=" y=\""+String.valueOf(y)+"\"";
    if (width!=WIDTH_DEFAULT) code+=" width=\""+String.valueOf(width)+"\"";
    if (height!=HEIGHT_DEFAULT) code+=" height=\""+String.valueOf(height)+"\"";
    if (autoresize!=AUTORESIZE_DEFAULT) code+=" autoresize=\""+String.valueOf(autoresize)+"\"";
    if (lefttop!=LEFTTOP_DEFAULT) code+=" lefttop=\""+lefttop+"\"";
    if (rightbottom!=RIGHTBOTTOM_DEFAULT) code+=" rightbottom=\""+rightbottom+"\"";
    if (xkeepratio!=XKEEPRATIO_DEFAULT) code+=" xkeepratio=\""+String.valueOf(xkeepratio)+"\"";
    if (ykeepratio!=YKEEPRATIO_DEFAULT) code+=" ykeepratio=\""+String.valueOf(ykeepratio)+"\"";
    if (help!=HELP_DEFAULT) code+=" help=\""+help+"\"";
    if (visible!=VISIBLE_DEFAULT) code+=" visible=\""+visible+"\"";
    code+="/>";
    return code;
  }
  public void draw(Graphics2D g) {
    draw(g,offsetx,offsety);
  }
  public void draw(Graphics2D g, int x_, int y_) {
    if(!created) return;
    if(s.gvars.parseBoolean(visible)==false) return;
    g.setColor(Color.BLACK);
    g.fillRect(x+x_,y+y_,width,height);   
    if(selected) {
      g.setColor(Color.RED);
      g.drawRect(x+x_,y+y_,width-1,height-1);
    }
  }
  public boolean contains(int x_, int y_) {
    return (x_>=x+offsetx && x_<=x+width+offsetx && y_>=y+offsety && y_<=y+height+offsety);
  }
  public DefaultMutableTreeNode getTreeNode() {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode("Video: "+id);      
    return node;
  }
  
}