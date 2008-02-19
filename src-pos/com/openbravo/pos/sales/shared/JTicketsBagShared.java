//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007 Openbravo, S.L.
//    http://sourceforge.net/projects/openbravopos
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package com.openbravo.pos.sales.shared;

import com.openbravo.pos.ticket.TicketInfo;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.sales.*;
import com.openbravo.pos.forms.*; 

public class JTicketsBagShared extends JTicketsBag {
    
    private String m_sCurrentTicket = null;
    private DataLogicReceipts dlReceipts = null;
    
    
    /** Creates new form JTicketsBagShared */
    public JTicketsBagShared(AppView app, TicketsEditor panelticket) {
        
        super(app, panelticket);
        
        try {
            dlReceipts = (DataLogicReceipts) app.getBean("com.openbravo.pos.sales.DataLogicReceipts");
        } catch (BeanFactoryException e) {
        }
        
        initComponents();
    }
    
    public void activate() {
        
        // precondicion es que no tenemos ticket activado ni ticket en el panel
        
        m_sCurrentTicket = null;
        selectValidTicket();     
        
        // Authorization
        m_jDelTicket.setEnabled(m_App.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
       
        // postcondicion es que tenemos ticket activado aqui y ticket en el panel
    }
    
    public boolean deactivate() {
        
        // precondicion es que tenemos ticket activado aqui y ticket en el panel 
   
        saveCurrentTicket();
        
        m_sCurrentTicket = null;
        m_panelticket.setActiveTicket(null, null);       
        
        return true;
        
        // postcondicion es que no tenemos ticket activado ni ticket en el panel
    }
    
    public void saveTicket() {
        saveTicket(m_panelticket.getActiveTicket());
    }
    
    public void cancelTicket() {          
        m_sCurrentTicket = null;
        selectValidTicket();      
    }
    
    protected JComponent getBagComponent() {
        return this;
    }
    
    protected JComponent getNullComponent() {
        return new JPanel();
    }
   
    private void saveCurrentTicket() {
        
        // save current ticket, if exists,
        if (m_sCurrentTicket != null) {
            try {
                dlReceipts.insertSharedTicket(m_sCurrentTicket, m_panelticket.getActiveTicket());
            } catch (BasicException e) {
                new MessageInf(e).show(this);
            }  
        }    
    }
    
    private void setActiveTicket(String id) throws BasicException{
          
        // BEGIN TRANSACTION
        TicketInfo ticket = dlReceipts.getSharedTicket(id);
        if (ticket == null)  {
            // Does not exists ???
            throw new BasicException(AppLocal.getIntString("message.noticket"));
        } else {
            dlReceipts.deleteSharedTicket(id);
            m_sCurrentTicket = id;
            m_panelticket.setActiveTicket(ticket, null);
        } 
        // END TRANSACTION                 
    }
    
    private void selectValidTicket() {
        
        try {
            List<SharedTicketInfo> l = dlReceipts.getSharedTicketList();
            if (l.size() == 0) {
                newTicket();
            } else {
                setActiveTicket(l.get(0).getId());
            }
        } catch (BasicException e) {
            new MessageInf(e).show(this);
            newTicket();
        }    
    }    
    
    private void newTicket() {      
        
        saveCurrentTicket();

        TicketInfo ticket = createTicketModel();     
        m_sCurrentTicket = UUID.randomUUID().toString(); // m_fmtid.format(ticket.getId());
        m_panelticket.setActiveTicket(ticket, null);      
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        m_jNewTicket = new javax.swing.JButton();
        m_jDelTicket = new javax.swing.JButton();
        m_jListTickets = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        m_jNewTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/editnew.png")));
        m_jNewTicket.setFocusPainted(false);
        m_jNewTicket.setFocusable(false);
        m_jNewTicket.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jNewTicket.setRequestFocusEnabled(false);
        m_jNewTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jNewTicketActionPerformed(evt);
            }
        });

        jPanel1.add(m_jNewTicket);

        m_jDelTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/editdelete.png")));
        m_jDelTicket.setFocusPainted(false);
        m_jDelTicket.setFocusable(false);
        m_jDelTicket.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jDelTicket.setRequestFocusEnabled(false);
        m_jDelTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDelTicketActionPerformed(evt);
            }
        });

        jPanel1.add(m_jDelTicket);

        m_jListTickets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/unsortedList.png")));
        m_jListTickets.setFocusPainted(false);
        m_jListTickets.setFocusable(false);
        m_jListTickets.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jListTickets.setRequestFocusEnabled(false);
        m_jListTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jListTicketsActionPerformed(evt);
            }
        });

        jPanel1.add(m_jListTickets);

        add(jPanel1, java.awt.BorderLayout.WEST);

    }// </editor-fold>//GEN-END:initComponents

    private void m_jListTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jListTicketsActionPerformed

        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    List<SharedTicketInfo> l = dlReceipts.getSharedTicketList();

                    JTicketsBagSharedList listDialog = JTicketsBagSharedList.newJDialog(JTicketsBagShared.this);
                    String id = listDialog.showTicketsList(l); 

                    if (id != null) {
                        saveCurrentTicket();
                        setActiveTicket(id); 
                    }
                } catch (BasicException e) {
                    new MessageInf(e).show(JTicketsBagShared.this);
                    newTicket();
                }                    
            }
        });
        
    }//GEN-LAST:event_m_jListTicketsActionPerformed

    private void m_jDelTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jDelTicketActionPerformed
        
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"), AppLocal.getIntString("title.editor"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            cancelTicket();
        }
        
    }//GEN-LAST:event_m_jDelTicketActionPerformed

    private void m_jNewTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jNewTicketActionPerformed

        newTicket();
        
    }//GEN-LAST:event_m_jNewTicketActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton m_jDelTicket;
    private javax.swing.JButton m_jListTickets;
    private javax.swing.JButton m_jNewTicket;
    // End of variables declaration//GEN-END:variables
    
}
