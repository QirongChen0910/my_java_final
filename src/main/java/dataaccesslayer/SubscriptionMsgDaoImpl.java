/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

import model.SubscriptionMsg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Cheng
 */
public class SubscriptionMsgDaoImpl {

    public SubscriptionMsgDaoImpl() {
    }
    public ArrayList<SubscriptionMsg> getAllSubscriptionMsgs(int recipientID) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<SubscriptionMsg> msges = null;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            con = databaseConnection.createConnection();
            pstmt = con.prepareStatement(
                    "SELECT * from SubscriptionMsg where recipientID = ? ");
             pstmt.setInt(1, recipientID);
            rs = pstmt.executeQuery();
            msges = new ArrayList<SubscriptionMsg>();
            while (rs.next()) {
                SubscriptionMsg msg = new SubscriptionMsg();
                msg.setId(rs.getInt("ID"));
                msg.setSenderName(rs.getString("senderName")); 
                msg.setRecipientID(rs.getInt("recipientID"));
                msg.setContent(rs.getString("content"));               
                msg.setDateSent(rs.getDate("dateSent"));
                msges.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return msges;
    }

    public void addSubscriptionMsg(SubscriptionMsg msges) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            con = databaseConnection.createConnection();
            // do not insert productID, it is generated by Database
            String query = "INSERT INTO SubscriptionMsg "
                           +"(senderName,recipientID,content,dateSent)"                           
                           +" VALUES(?, ?, ?, ?)" ;
                           
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, msges.getSenderName());
            pstmt.setInt(2, msges.getRecipientID());
            pstmt.setString(3, msges.getContent());
            pstmt.setDate(4,msges.getDateSent());           
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
 
 public void deleteSubscriptionMsg(int id){
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            con = databaseConnection.createConnection();
            String query = "delete from SubscriptionMsg where ID = ?" ;             
            pstmt = con.prepareStatement(query);
             pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}