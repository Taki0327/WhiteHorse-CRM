package gui;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CRMThread extends Thread {
    private int time = 300;
    admin admin;
    Client client;
    String uname;
    Employee employee;
    boolean bool=true;
    Date date;
    JLabel Time;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public CRMThread(admin admin,String uname,JLabel Time) {
        this.admin=admin;
        this.uname=uname;
        this.Time=Time;
	}

	public CRMThread(Client client,String uname,JLabel Time) {
        this.client=client;
        this.uname=uname;
        this.Time=Time;
	}
    public CRMThread(Employee employee,String uname,JLabel Time) {
        this.employee=employee;
        this.uname=uname;
        this.Time=Time;
	}

	public void cz() {
        time = 300;
    }
    public void Stop()
    {
        this.bool=false;
        this.admin=null;
        this.client=null;
        this.employee=null;
        //this.interrupt();
    }

    public void run() {
        try {
            while(bool){
                date=new Date();
                time--;
                Time.setText(sdf.format(date));
                CRMThread.sleep(1000);
                if(time<=0)
                {
                    bool=false;
                    this.interrupt();
                    if(admin!=null)
                    {
                        admin.setVisible(false);
                    }
                    else if(client!=null)
                    {
                        client.setVisible(false);
                    }
                    else if(employee!=null)
                    {
                        employee.setVisible(false);
                    }
                    if(!bool)
                    {
                        new Login(uname);
                    }
                }
            }
        } 
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
