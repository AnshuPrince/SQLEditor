/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JdbcMark1;

import java.sql.*;
import javax.sql.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Anshu_Prince
 */
public class mark3 extends Frame{
    
    public mark3()
    {
        try{
            prepareConnection();
            initComponents();
        }
        catch(Exception e)
        {
            System.out.println("Problem connecting");
            System.exit(0);
        }
    }
    public void prepareConnection() throws Exception
    {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost;databaseName=anshu";
        String user = "sa";
        String password = "anshu123";
        con = DriverManager.getConnection(url, user, password);
        st = con.createStatement();
    }
    public void initComponents()
    {
        label1 = new Label();
        input = new TextField();
        label2 = new Label();
        output = new TextArea();
        setLayout(null);
        setTitle("SQL Editor");
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent evt)
            {
                exitForm(evt);
            }
        });
        label1.setAlignment(Label.CENTER);
        Font font = new Font("Arial",1,14);
        label1.setFont(font);
        add(label1);
        label1.setBounds(0,40,640,40);
        input.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                inputActionPerformed(evt);
            }

        });
        add(input);
        input.setBounds(70,100,590,30);
        label2.setAlignment(Label.RIGHT);
        label2.setText("SQL>");
        add(label2);
        label2.setBounds(0,100,70,30);
        output.setBackground(new Color(255,255,255));
        output.setEditable(false);
        add(output);
        output.setBounds(10,140,660,290);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(670,432));
        setLocation((screenSize.width-670)/2,(screenSize.height-432)/2);
        setResizable(false);
        
    }   
private void inputActionPerformed(ActionEvent evt)
{
    try{
        if(input.getText().equals(""))
            throw new Exception("Invalid query");
        boolean flag = st.execute(input.getText());
        if(flag)
        {
            displayResults();
        }
        else
        {
            output.setText(st.getUpdateCount()+"rows have been affected");
        }
    }
    catch(Exception e)
    {
        output.setText("");
        output.setText("Invalid Query");
        output.setText(e.getMessage());
        e.printStackTrace();
        
        
    }
}
private void  displayResults() throws SQLException
{
    StringBuffer sb = new  StringBuffer();
    rs = st.getResultSet();
    ResultSetMetaData rsmd = rs.getMetaData();
    int count = rsmd.getColumnCount();
    for(int i = 1; i<=count;i++)
    {
        System.out.println(rsmd.getColumnDisplaySize(i));
        sb.append(getString(rsmd.getColumnName(i),rsmd.getColumnDisplaySize(i)));
    }
    sb.append("\n");
    for(int i=1;i<=count;i++)
    {
        sb.append(getLines(rsmd.getColumnDisplaySize(i)));
    }
    sb.append("\n");
    while(rs.next())
    {
        for(int i=1;i<=count;i++)
        {
           sb.append(getString(rs.getString(i),rsmd.getColumnDisplaySize(i))); 
        }
        sb.append("\n");
    }
    sb.append(count+" rows selected");
    output.setText(sb.toString());
}
private String getString(String s,int len)
{
    if(s == null)
    {
        s = "";
    }
    for(int i= s.length();i<=(len+5);i++)
    {
        s+="";
    }
    return s;
}
private String getLines(int  len)
{
    String s="";
     for(int i= s.length();i<=(len);i++)
    {
        s+="-";
    }
     return s+"     ";
}
private void exitForm(WindowEvent evt)
{
    System.exit(0);
}
    public static void main(String[] args) {
        new mark3().show();
    }
    private TextField input;
    private TextArea output;
    private Label label2,label1;
    private Connection con;
    private  Statement st;
    private ResultSet rs;
}
