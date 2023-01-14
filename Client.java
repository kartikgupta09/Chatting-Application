package com.kartik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client  implements ActionListener {
    //constructor of server object
    JTextField text;
    static JPanel a1;// declared globally so that all can access
    static Box vertical = Box.createVerticalBox();//for message appears in vertically
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    Client(){
        f.setLayout(null);
        JPanel p1= new JPanel();
        p1.setBackground(new Color(7,94,84));//used to divide on frame
        p1.setBounds(0,0,700,70);
        p1.setLayout(null);
        f.add(p1); //used to add any fn over the panel

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,35,35);
        p1.add(back);

        //for clicking the back button to exit
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);

            }
        });
        // for setting profile picture
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        //for video icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        // for call icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(350,20,35,30);
        p1.add(phone);

        //for more option button icon
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(410,20,10,25);
        p1.add(morevert);

        //for name over the frame
        //with the help of Jlable we can write over the frame
        JLabel name = new JLabel("Bunty");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SANT SERIF" , Font.BOLD,18));
        p1.add(name);


        //font for active status over the frame
        JLabel status = new JLabel("Active now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SANT SERIF" , Font.BOLD,14));
        p1.add(status);

        // text area panel
        a1 = new JPanel();
        a1.setBounds(5, 75,440,570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN,16));
        f.add(text);


        // for button of sending
        JButton Boom = new JButton("Boom");
        Boom.setBounds(320,655,123,40);
        //setting the color of boom button
        Boom.setBackground(new Color(7,94,84));
        Boom.setForeground(Color.WHITE);
        //after clicking on send button
        // message should be displayed on panel
        Boom.addActionListener(this);
        f.add(Boom);

        //after clicking on send button
        // message should be displayed on panel

        f.setSize(450,700);//for frame size
        f.setLocation(800,50);
        f.setUndecorated(true);//for removing corner
        f.getContentPane().setBackground(Color.WHITE);


        f.setVisible(true); // for visibility and should be at the last
        //so that all the changes should be visible
    }
    public static void main(String[] args) {
        new Client();
        try{
            // for connecting to the server making socket
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din  = new DataInputStream(s.getInputStream());//for input of messages
            dout = new DataOutputStream(s.getOutputStream());

            while (true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLable(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                f.validate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
            //JLabel output = new JLabel(out);
            JPanel p2 = formatLable(out);


            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);//if messages are single then are align in single on right
            vertical.add(right);// if messages are multiple then are align in a vertical manner
            vertical.add(Box.createVerticalStrut(15));//for space between messages

            a1.add(vertical, BorderLayout.PAGE_START);//message should be displayed on a1 panel

            dout.writeUTF(out);
            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static JPanel formatLable(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);
        //for time available at the end of the messages
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);
        return panel;
    }

}
