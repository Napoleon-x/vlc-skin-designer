/*****************************************************************************
 * Checkbox.java
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
import vlcskineditor.history.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;
import vlcskineditor.resources.ImageResource;

/**
 * Checkbox item
 * @author Daniel Dreibrodt
 */
public class Checkbox extends Item implements ActionListener{
  
  public final String DOWN1_DEFAULT = "none";
  public final String DOWN2_DEFAULT = "none";
  public final String OVER1_DEFAULT = "none";
  public final String OVER2_DEFAULT = "none";  
  public final String ACTION1_DEFAULT = "none";
  public final String ACTION2_DEFAULT = "none";
  public final String TOOLTIPTEXT1_DEFAULT = "";
  public final String TOOLTIPTEXT2_DEFAULT = "";
  
  public String up1, up2;  
  public String down1 = DOWN1_DEFAULT;
  public String down2 = DOWN2_DEFAULT;
  public String over1 = OVER1_DEFAULT;
  public String over2 = OVER2_DEFAULT;
  public String state;
  public String action1 = ACTION1_DEFAULT;
  public String action2 = ACTION2_DEFAULT;
  public String tooltiptext1 = TOOLTIPTEXT1_DEFAULT;
  public String tooltiptext2 = TOOLTIPTEXT2_DEFAULT;
  
  JFrame frame = null;
  JTextField id_tf, x_tf, y_tf, help_tf, visible_tf, up1_tf, down1_tf, over1_tf, action1_tf, tooltiptext1_tf;
  JTextField up2_tf, down2_tf, over2_tf, action2_tf, tooltiptext2_tf, state_tf;
  JComboBox lefttop_cb, rightbottom_cb, xkeepratio_cb, ykeepratio_cb;
  JButton visible_btn, action1_btn, action2_btn, state_btn, ok_btn, cancel_btn, help_btn;

  ImageResource up1_res, over1_res, down1_res, up2_res, over2_res, down2_res;

  ActionEditor action1_ae, action2_ae;

  private boolean state_bool = true;
  
  {
    type = Language.get("CHECKBOX");
  }
  
  /** Creates a new instance of Checkbox
   * @param xmlcode The XML code
   * @param s_ The parent skin
   */
  public Checkbox(String xmlcode, Skin s_) {
    s = s_;
    up1 = XML.getValue(xmlcode,"up1");
    up1_res = s.getImageResource(up1);
    up2 = XML.getValue(xmlcode,"up2");
    up2_res = s.getImageResource(up2);
    state = XML.getValue(xmlcode,"state");
    if(xmlcode.indexOf(" over1=\"")!=-1) {
      over1 = XML.getValue(xmlcode,"over1");
      over1_res = s.getImageResource(over1);
    }
    if(xmlcode.indexOf(" over2=\"")!=-1) {
      over2 = XML.getValue(xmlcode,"over2");
      over2_res = s.getImageResource(over2);
    }
    if(xmlcode.indexOf(" down1=\"")!=-1) {
      down1 = XML.getValue(xmlcode,"down1");
      down1_res = s.getImageResource(down1);
    }
    if(xmlcode.indexOf(" down2=\"")!=-1) {
      down2 = XML.getValue(xmlcode,"down2");
      down2_res = s.getImageResource(down2);
    }
    if(xmlcode.indexOf(" action1=\"")!=-1) action1 = XML.getValue(xmlcode,"action1");
    if(xmlcode.indexOf(" action2=\"")!=-1) action2 = XML.getValue(xmlcode,"action2");
    if(xmlcode.indexOf(" tooltiptext1=\"")!=-1) tooltiptext1 = XML.getValue(xmlcode,"tooltiptext1");
    if(xmlcode.indexOf(" tooltiptext2=\"")!=-1) tooltiptext2 = XML.getValue(xmlcode,"tooltiptext2");
    if(xmlcode.indexOf(" x=\"")!=-1) x = XML.getIntValue(xmlcode,"x");
    if(xmlcode.indexOf(" y=\"")!=-1) y = XML.getIntValue(xmlcode,"y");
    if(xmlcode.indexOf(" id=\"")!=-1) id = XML.getValue(xmlcode,"id");
    else id = Language.get("UNNAMED").replaceAll("%t",type).replaceAll("%i",String.valueOf(s.getNewId()));
    if(xmlcode.indexOf(" lefttop=\"")!=-1) lefttop = XML.getValue(xmlcode,"lefttop");
    if(xmlcode.indexOf(" rightbottom=\"")!=-1) rightbottom = XML.getValue(xmlcode,"rightbottom");
    if(xmlcode.indexOf(" xkeepratio=\"")!=-1) xkeepratio = XML.getBoolValue(xmlcode,"xkeepratio");
    if(xmlcode.indexOf(" ykeepratio=\"")!=-1) ykeepratio = XML.getBoolValue(xmlcode,"ykeepratio");
    if(xmlcode.indexOf(" visible=\"")!=-1) visible = XML.getValue(xmlcode,"visible");
    created = true;
  }
  public Checkbox(Skin s_) {
    s = s_;
    up1="none";
    up2="none";
    state="false";
    id = Language.get("UNNAMED").replaceAll("%t",type).replaceAll("%i",String.valueOf(s.getNewId()));
    showOptions();
  }
  @Override
  public void update() {
    if(!created) {
      id = id_tf.getText();
      x = Integer.parseInt(x_tf.getText());
      y = Integer.parseInt(y_tf.getText());
      lefttop = lefttop_cb.getSelectedItem().toString();
      rightbottom = rightbottom_cb.getSelectedItem().toString();
      xkeepratio = Boolean.parseBoolean(xkeepratio_cb.getSelectedItem().toString());
      ykeepratio = Boolean.parseBoolean(ykeepratio_cb.getSelectedItem().toString());
      visible = visible_tf.getText();
      help = help_tf.getText();

      up1 = up1_tf.getText();
      over1 = over1_tf.getText();
      down1 = down1_tf.getText();
      action1 = action1_tf.getText();
      tooltiptext1 = tooltiptext1_tf.getText();
      up2 = up2_tf.getText();
      over2 = over2_tf.getText();
      down2 = down2_tf.getText();
      action2 = action2_tf.getText();
      tooltiptext2 = tooltiptext2_tf.getText();
      state = state_tf.getText();
      
      ItemAddEvent cae = new ItemAddEvent(s.getParentListOf(id),this);
      s.m.hist.addEvent(cae);
      
      s.updateItems();   
      s.expandItem(id);
      frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      created = true;
    }
    else {
      CheckboxEditEvent cee = new CheckboxEditEvent(this);
      
      id = id_tf.getText();
      x = Integer.parseInt(x_tf.getText());
      y = Integer.parseInt(y_tf.getText());
      lefttop = lefttop_cb.getSelectedItem().toString();
      rightbottom = rightbottom_cb.getSelectedItem().toString();
      xkeepratio = Boolean.parseBoolean(xkeepratio_cb.getSelectedItem().toString());
      ykeepratio = Boolean.parseBoolean(ykeepratio_cb.getSelectedItem().toString());
      visible = visible_tf.getText();
      help = help_tf.getText();

      up1 = up1_tf.getText();
      over1 = over1_tf.getText();
      down1 = down1_tf.getText();
      action1 = action1_tf.getText();
      tooltiptext1 = tooltiptext1_tf.getText();
      up2 = up2_tf.getText();
      over2 = over2_tf.getText();
      down2 = down2_tf.getText();
      action2 = action2_tf.getText();
      tooltiptext2 = tooltiptext2_tf.getText();
      state = state_tf.getText();
      
      cee.setNew();
      s.m.hist.addEvent(cee);
      
      s.updateItems();   
      s.expandItem(id);
    }
    updateToGlobalVariables();
  }
  @Override
  public void showOptions() {
    if(frame==null) {
      frame = new JFrame("Checkbox settings");
      frame.setResizable(false);
      frame.setLayout(new FlowLayout());
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      JLabel id_l = new JLabel(Language.get("WIN_ITEM_ID"));
      id_tf = new JTextField();      
      JLabel x_l = new JLabel(Language.get("WIN_ITEM_X"));
      x_tf = new JTextField();      
      x_tf.setDocument(new NumbersOnlyDocument());
      JLabel y_l = new JLabel(Language.get("WIN_ITEM_Y"));
      y_tf = new JTextField();      
      y_tf.setDocument(new NumbersOnlyDocument());
      String[] align_values = {"lefttop", "leftbottom", "righttop", "rightbottom"};
      JLabel lefttop_l = new JLabel(Language.get("WIN_ITEM_LEFTTOP"));
      lefttop_cb = new JComboBox(align_values);
      lefttop_cb.setToolTipText("Indicate to which corner of the Layout the top-left-hand corner of this item is attached, in case of resizing.");
      JLabel rightbottom_l = new JLabel(Language.get("WIN_ITEM_RIGHTBOTTOM"));
      rightbottom_cb = new JComboBox(align_values);
      rightbottom_cb.setToolTipText("Indicate to which corner of the Layout the bottom-right-hand corner of this item is attached, in case of resizing.");
      Object[] bool_values = { true, false };
      JLabel xkeepratio_l = new JLabel(Language.get("WIN_ITEM_XKEEPRATIO"));
      xkeepratio_cb = new JComboBox(bool_values);
      xkeepratio_cb.setToolTipText(Language.get("WIN_ITEM_XKEEPRATIO_TIP"));
      JLabel ykeepratio_l = new JLabel(Language.get("WIN_ITEM_YKEEPRATIO"));
      ykeepratio_cb = new JComboBox(bool_values);
      ykeepratio_cb.setToolTipText(Language.get("WIN_ITEM_YKEEPRATIO_TIP"));
      JLabel visible_l = new JLabel(Language.get("WIN_ITEM_VISIBLE"));
      visible_tf = new JTextField();
      visible_btn = new JButton("",s.m.help_icon);
      visible_btn.addActionListener(this);  
      JLabel help_l = new JLabel(Language.get("WIN_ITEM_HELP"));
      help_tf = new JTextField();
      help_tf.setToolTipText(Language.get("WIN_ITEM_HELP_TIP"));
      
      JLabel up1_l = new JLabel("Normal image*:");
      up1_tf = new JTextField();
      JLabel over1_l = new JLabel("Mouse-over image:");
      over1_tf = new JTextField();
      JLabel down1_l = new JLabel("Mouse-click image:");
      down1_tf = new JTextField();
      JLabel action1_l = new JLabel("Action:");
      action1_tf = new JTextField();
      action1_btn = new JButton("",s.m.editor_icon);
      action1_btn.addActionListener(this);
      JLabel tooltiptext1_l = new JLabel(Language.get("WIN_ITEM_TOOLTIPTEXT"));
      tooltiptext1_tf = new JTextField();
      JLabel up2_l = new JLabel("Normal image*:");
      up2_tf = new JTextField();
      JLabel over2_l = new JLabel("Mouse-over image:");
      over2_tf = new JTextField();
      JLabel down2_l = new JLabel("Mouse-click image:");
      down2_tf = new JTextField();
      JLabel action2_l = new JLabel("Action:");
      action2_tf = new JTextField();
      action2_btn = new JButton("",s.m.editor_icon);
      action2_btn.addActionListener(this);
      JLabel tooltiptext2_l = new JLabel(Language.get("WIN_ITEM_TOOLTIPTEXT"));
      tooltiptext2_tf = new JTextField();
      
      JLabel state_l = new JLabel("Condition:");
      state_tf = new JTextField();
      state_tf.setToolTipText("Boolean expression specifying the state of the checkbox: if the expression resolves to 'false', the first state will be used, and if it resolves to 'true' the second state will be used. Example for a checkbox showing/hiding a window whose id is \"playlist_window\": state=\"playlist_window.isVisible\" (or state=\"not playlist_window.isVisible\", depending on the states you chose).");
      state_btn = new JButton("",s.m.help_icon);
      state_btn.addActionListener(this);
      
      ok_btn = new JButton(Language.get("BUTTON_OK"));
      ok_btn.addActionListener(this);
      ok_btn.setPreferredSize(new Dimension(70,25));
      cancel_btn = new JButton(Language.get("BUTTON_CANCEL"));
      cancel_btn.addActionListener(this);
      cancel_btn.setPreferredSize(new Dimension(70,25));
      help_btn = new JButton(Language.get("BUTTON_HELP"));
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
      y_l.setBounds(240,45,75,24);
      y_tf.setBounds(325,45,150,24);      
      general.add(lefttop_l);
      general.add(lefttop_cb);
      lefttop_l.setBounds(5,75,75,24);
      lefttop_cb.setBounds(85,75,150,24);
      general.add(rightbottom_l);
      general.add(rightbottom_cb);
      rightbottom_l.setBounds(240,75,75,24);
      rightbottom_cb.setBounds(325,75,150,24);
      general.add(xkeepratio_l);
      general.add(xkeepratio_cb);
      xkeepratio_l.setBounds(5,105,75,24);
      xkeepratio_cb.setBounds(85,105,150,24);
      general.add(ykeepratio_l);
      general.add(ykeepratio_cb);
      ykeepratio_l.setBounds(240,105,75,24);
      ykeepratio_cb.setBounds(325,105,150,24);
      general.add(visible_l);
      general.add(visible_tf);
      general.add(visible_btn);
      visible_l.setBounds(5,135,75,24);
      visible_tf.setBounds(85,135,120,24);
      visible_btn.setBounds(210,225,24,24);
      general.add(help_l);
      general.add(help_tf);
      help_l.setBounds(240,135,75,24);
      help_tf.setBounds(325,135,150,24);
      general.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), Language.get("WIN_ITEM_GENERAL")));
      general.setMinimumSize(new Dimension(495,165));
      general.setPreferredSize(new Dimension(495,165));
      general.setMaximumSize(new Dimension(495,165));
      frame.add(general);
      
      JPanel state_panel = new JPanel(null);
      state_panel.add(state_l);
      state_panel.add(state_tf);
      state_panel.add(state_btn);
      state_l.setBounds(5,15,75,24);
      state_tf.setBounds(85,15,120,24);
      state_btn.setBounds(210,15,24,24);
      state_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Checkbox state"));
      state_panel.setMinimumSize(new Dimension(495,45));
      state_panel.setPreferredSize(new Dimension(495,45));
      state_panel.setMaximumSize(new Dimension(495,45));
      frame.add(state_panel);
      
      JPanel state1 = new JPanel(null);
      state1.add(up1_l);
      state1.add(up1_tf);
      up1_l.setBounds(5,15,75,24);
      up1_tf.setBounds(85,15,150,24);
      state1.add(over1_l);
      state1.add(over1_tf);
      over1_l.setBounds(5,45,75,24);
      over1_tf.setBounds(85,45,150,24);
      state1.add(down1_l);
      state1.add(down1_tf);
      down1_l.setBounds(5,75,75,24);
      down1_tf.setBounds(85,75,150,24);
      state1.add(action1_l);
      state1.add(action1_tf);
      state1.add(action1_btn);
      action1_l.setBounds(5,105,75,24);
      action1_tf.setBounds(85,105,120,24);
      action1_btn.setBounds(210,105,24,24);
      state1.add(tooltiptext1_l);
      state1.add(tooltiptext1_tf);
      tooltiptext1_l.setBounds(5,135,75,24);
      tooltiptext1_tf.setBounds(85,135,150,24);
      state1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "State One (Condition is not fulfilled:"));
      state1.setMinimumSize(new Dimension(240,165));
      state1.setPreferredSize(new Dimension(240,165));
      state1.setMaximumSize(new Dimension(240,165));
      frame.add(state1);
      
      JPanel state2 = new JPanel(null);
      state2.add(up2_l);
      state2.add(up2_tf);
      up2_l.setBounds(5,15,75,24);
      up2_tf.setBounds(85,15,150,24);
      state2.add(over2_l);
      state2.add(over2_tf);
      over2_l.setBounds(5,45,75,24);
      over2_tf.setBounds(85,45,150,24);
      state2.add(down2_l);
      state2.add(down2_tf);
      down2_l.setBounds(5,75,75,24);
      down2_tf.setBounds(85,75,150,24);
      state2.add(action2_l);
      state2.add(action2_tf);
      state2.add(action2_btn);
      action2_l.setBounds(5,105,75,24);
      action2_tf.setBounds(85,105,120,24);
      action2_btn.setBounds(210,105,24,24);
      state2.add(tooltiptext2_l);
      state2.add(tooltiptext2_tf);
      tooltiptext2_l.setBounds(5,135,75,24);
      tooltiptext2_tf.setBounds(85,135,150,24);
      state2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "State Two (Condition is fulfilled):"));
      state2.setMinimumSize(new Dimension(240,165));
      state2.setPreferredSize(new Dimension(240,165));
      state2.setMaximumSize(new Dimension(240,165));
      frame.add(state2);
      
      
      frame.add(ok_btn);
      frame.add(cancel_btn);
      frame.add(help_btn);      
      frame.add(new JLabel(Language.get("NOTE_STARRED")));
      
      frame.setMinimumSize(new Dimension(505,450));
      frame.setPreferredSize(new Dimension(505,450));
      frame.setMaximumSize(new Dimension(505,450));
      
      frame.pack();
      
      frame.getRootPane().setDefaultButton(ok_btn);
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
    
    state_tf.setText(state);
    up1_tf.setText(up1);
    over1_tf.setText(over1);
    down1_tf.setText(down1);
    action1_tf.setText(action1);
    tooltiptext1_tf.setText(tooltiptext1);
    up2_tf.setText(up2);
    over2_tf.setText(over2);
    down2_tf.setText(down2);
    action2_tf.setText(action2);
    tooltiptext2_tf.setText(tooltiptext2);
    
    frame.setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource().equals(ok_btn)) {
      if(id_tf.getText().equals("")) {
        JOptionPane.showMessageDialog(frame,Language.get("ERROR_ID_INVALID_MSG"),Language.get("ERROR_ID_INVALID_TITLE"),JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      else if(!id_tf.getText().equals(id)) {
        if(s.idExists(id_tf.getText())) {
          JOptionPane.showMessageDialog(frame,Language.get("ERROR_ID_EXISTS_MSG").replaceAll("%i", id_tf.getText()),Language.get("ERROR_ID_INVALID_TITLE"),JOptionPane.INFORMATION_MESSAGE);
          return;
        }
      }
      if(state_tf.getText().equals("")) {
        JOptionPane.showMessageDialog(frame,"Please provide the state condition!","State not valid",JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      up1_res = s.getImageResource(up1_tf.getText());
      if(up1_res==null) {
        JOptionPane.showMessageDialog(frame,"The bitmap \""+up1_tf.getText()+"\" does not exist!","Image not valid",JOptionPane.INFORMATION_MESSAGE);
        up1_res = s.getImageResource(up1);
        return;
      }
      over1_res = s.getImageResource(over1_tf.getText());
      if(!over1_tf.getText().equals("none") && over1_res==null) {
        JOptionPane.showMessageDialog(frame,"The bitmap \""+over1_tf.getText()+"\" does not exist!","Image not valid",JOptionPane.INFORMATION_MESSAGE);
        over1_res = s.getImageResource(over1);
        return;
      }
      down1_res = s.getImageResource(down1_tf.getText());
      if(!down1_tf.getText().equals("none") && down1_res==null) {
        JOptionPane.showMessageDialog(frame,"The bitmap \""+down1_tf.getText()+"\" does not exist!","Image not valid",JOptionPane.INFORMATION_MESSAGE);
        down1_res = s.getImageResource(down1);
        return;
      }
      up2_res = s.getImageResource(up2_tf.getText());
      if(up2_res==null) {
        JOptionPane.showMessageDialog(frame,"The bitmap \""+up2_tf.getText()+"\" does not exist!","Image not valid",JOptionPane.INFORMATION_MESSAGE);
        up2_res = s.getImageResource(up2);
        return;
      }
      over2_res = s.getImageResource(over2_tf.getText());
      if(!over2_tf.getText().equals("none") && s.getResource(over2_tf.getText())==null) {
        JOptionPane.showMessageDialog(frame,"The bitmap \""+over2_tf.getText()+"\" does not exist!","Image not valid",JOptionPane.INFORMATION_MESSAGE);
        over2_res = s.getImageResource(over2);
        return;
      }
      down2_res = s.getImageResource(down2_tf.getText());
      if(!down2_tf.getText().equals("none") && down2_res==null) {
        JOptionPane.showMessageDialog(frame,"The bitmap \""+down2_tf.getText()+"\" does not exist!","Image not valid",JOptionPane.INFORMATION_MESSAGE);
        down2_res = s.getImageResource(down2);
        return;
      }
      update();
      frame.setVisible(false); 
      frame.dispose();
      frame = null;
    }
    else if(e.getSource().equals(help_btn)) {
      Helper.browse("http://www.videolan.org/vlc/skinedhlp/i-checkbox.html");
    }
    else if(e.getSource().equals(state_btn)) {
      Helper.browse("http://www.videolan.org/vlc/skinedhlp/boolexpr.html");
    }
    else if(e.getSource().equals(action1_btn)) {
      if(action1_ae==null) action1_ae = new ActionEditor(this);
      action1_ae.editAction(action1_tf.getText());
    }
    else if(e.getSource().equals(action2_btn)) {
      if(action2_ae==null) action2_ae = new ActionEditor(this);
      action2_ae.editAction(action2_tf.getText());
    }
    else if(e.getSource().equals(visible_btn)) {
      Helper.browse("http://www.videolan.org/vlc/skinedhlp/boolexpr.html");
    }
    else if(e.getSource().equals(cancel_btn)) {
      if(!created) {
        java.util.List<Item> l = s.getParentListOf(id);
        if(l!=null) l.remove(this);
      }
      frame.setVisible(false);
      frame.dispose();
      frame = null;
    }
  }
  @Override
  public void actionWasEdited(ActionEditor ae) {
    if(ae==action1_ae) action1_tf.setText(action1_ae.getCode());
    else if(ae==action2_ae) action2_tf.setText(action2_ae.getCode());
  }
  @Override
  public String returnCode(String indent) {
    String code = indent+"<Checkbox";
    code+=" state=\""+state+"\" up1=\""+up1+"\" up2=\""+up2+"\"";
    if (!down1.equals(ID_DEFAULT)) code+=" down1=\""+down1+"\"";
    if (!down2.equals(ID_DEFAULT)) code+=" down2=\""+down2+"\"";
    if (!over1.equals(ID_DEFAULT)) code+=" over1=\""+over1+"\"";
    if (!over2.equals(ID_DEFAULT)) code+=" over2=\""+over2+"\"";
    if (!action1.equals(ID_DEFAULT)) code+=" action1=\""+action1+"\"";
    if (!action2.equals(ID_DEFAULT)) code+=" action2=\""+action2+"\"";
    if (!tooltiptext1.equals(ID_DEFAULT)) code+=" tooltiptext1=\""+tooltiptext1+"\"";
    if (!tooltiptext2.equals(ID_DEFAULT)) code+=" tooltiptext2=\""+tooltiptext2+"\"";
    if (!id.equals(ID_DEFAULT)) code+=" id=\""+id+"\"";
    if (x!=X_DEFAULT) code+=" x=\""+String.valueOf(x)+"\"";
    if (y!=Y_DEFAULT) code+=" y=\""+String.valueOf(y)+"\"";
    if (!lefttop.equals(LEFTTOP_DEFAULT)) code+=" lefttop=\""+lefttop+"\"";
    if (!rightbottom.equals(RIGHTBOTTOM_DEFAULT)) code+=" rightbottom=\""+rightbottom+"\"";
    if (xkeepratio!=XKEEPRATIO_DEFAULT) code+=" xkeepratio=\""+String.valueOf(xkeepratio)+"\"";
    if (ykeepratio!=YKEEPRATIO_DEFAULT) code+=" ykeepratio=\""+String.valueOf(ykeepratio)+"\"";
    if (!help.equals(HELP_DEFAULT)) code+=" help=\""+help+"\"";
    if (!visible.equals(VISIBLE_DEFAULT)) code+=" visible=\""+visible+"\"";
    code+="/>";
    return code;
  }
  @Override
  public void draw(Graphics2D g, int z) {
    draw(g,offsetx,offsety,z);
  }
  @Override
  public void draw(Graphics2D g, int x_, int y_, int z) {
    if(!created) return;    
    java.awt.image.BufferedImage bi = null; 
    if(state_bool) {
      if(!hovered || ( (over2.equals("none") && !clicked)||(clicked && down2.equals("none")) )) bi = up2_res.image;
      else if(!clicked || down2.equals("none")) bi = over2_res.image;
      else bi = down2_res.image;
    }
    else {
      if(!hovered || ( (over1.equals("none") && !clicked)||(clicked && down1.equals("none")) )) bi = up1_res.image;
      else if(!clicked || down1.equals("none")) bi = over1_res.image;
      else bi = down1_res.image;
    }
    if(vis)g.drawImage(bi,(x+x_)*z,(y+y_)*z,bi.getWidth()*z,bi.getHeight()*z,null);
    if(selected) {
      g.setColor(Color.RED);
      g.drawRect((x+x_)*z,(y+y_)*z,bi.getWidth()*z-1,bi.getHeight()*z-1);
    }
  }
  @Override
  public boolean contains(int x_, int y_) {
    java.awt.image.BufferedImage bi = up1_res.image;
    return (x_>=x+offsetx && x_<=x+bi.getWidth()+offsetx && y_>=y+offsety && y_<=y+bi.getHeight()+offsety);
  }
  public DefaultMutableTreeNode getTreeNode() {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode("Checkbox: "+id);    
    return node;
  }
  @Override
  public boolean uses(String id_) {
    return (up1.equals(id_)||up2.equals(id_)||over1.equals(id_)||over2.equals(id_)||down1.equals(id_)||down2.equals(id_));
  }
  @Override
  public void updateToGlobalVariables() {
    vis = s.gvars.parseBoolean(visible);
    state_bool = s.gvars.parseBoolean(state);
  }
}
