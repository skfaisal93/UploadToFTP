package com.uploadftp;

import org.apache.commons.net.ftp.FTPClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Upload {

    static FTPClient ftpClient;

    public static void main(String[] args) {

        JFrame frame= new JFrame("Upload Files To Server");
        JLabel askFTPDetails, askFTPHost, askFTPUsername, askFTPPassword, askFTPPort, askFTPMode, localDir, remoteDir;
        JTextPane hostText, usernameText, passwordText, portText, localDirText, remoteDirText;
        ButtonGroup modeChoose;
        JRadioButton _default, active, passive;
        JButton login;

        try {

            askFTPDetails = new JLabel("FTP Details");
            askFTPDetails.setBounds(10,10, 100,30);

            askFTPHost = new JLabel("Host");
            askFTPHost.setBounds(10,40, 100,20);
            hostText = new JTextPane();
            hostText.setBounds(100,40, 180,20);

            askFTPUsername = new JLabel("Username");
            askFTPUsername.setBounds(10,65, 100,20);
            usernameText = new JTextPane();
            usernameText.setBounds(100,65, 180,20);

            askFTPPassword = new JLabel("Password");
            askFTPPassword.setBounds(10,90, 100,20);
            passwordText = new JTextPane();
            passwordText.setBounds(100,90, 180,20);

            askFTPPort = new JLabel("Port");
            askFTPPort.setBounds(10,115, 100,20);
            portText = new JTextPane();
            portText.setText("21");
            portText.setBounds(100,115, 180,20);

            askFTPMode = new JLabel("Mode");
            askFTPMode.setBounds(10,140, 100,20);
            modeChoose = new ButtonGroup();
            _default = new JRadioButton("Default");
            _default.setBounds(100, 140,65, 20);
            _default.setSelected(true);
            active = new JRadioButton("Active");
            active.setBounds(165, 140,65, 20);
            passive = new JRadioButton("Passive");
            passive.setBounds(230, 140,80, 20);
            modeChoose.add(_default);
            modeChoose.add(active);
            modeChoose.add(passive);

            localDir = new JLabel("Local Dir");
            localDir.setBounds(10,170, 100,20);
            localDirText = new JTextPane();
            localDirText.setBounds(100,170, 180,20);

            remoteDir = new JLabel("Remote Dir");
            remoteDir.setBounds(10,195, 100,20);
            remoteDirText = new JTextPane();
            remoteDirText.setBounds(100,195, 180,20);

            login = new JButton("Login & Upload");
            login.setBounds(10,225, 150,20);
            login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loginAndUpload(hostText.getText(), Integer.parseInt(portText.getText()), usernameText.getText(), passwordText.getText(), modeChoose.getElements().nextElement().getText(), localDirText.getText(), remoteDirText.getText());
                }
            });

            frame.add(askFTPDetails);
            frame.add(askFTPHost);
            frame.add(hostText);
            frame.add(askFTPUsername);
            frame.add(usernameText);
            frame.add(askFTPPassword);
            frame.add(passwordText);
            frame.add(askFTPPort);
            frame.add(portText);
            frame.add(askFTPMode);
            frame.add(_default);
            frame.add(active);
            frame.add(passive);
            frame.add(localDir);
            frame.add(localDirText);
            frame.add(remoteDir);
            frame.add(remoteDirText);
            frame.add(login);

            frame.setSize(500,300);
            frame.setLayout(null);
            frame.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void loginAndUpload(String server, int port, String user, String pass, String mode, String local, String remote) {

        try {

            ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            if (mode.equals("Active")) {
                ftpClient.enterLocalActiveMode();
            } else if (mode.equals("Passive")) {
                ftpClient.enterLocalPassiveMode();
            }

            System.out.println("Connected");

            FTPUtil.uploadDirectory(ftpClient, remote, local, "");

            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
