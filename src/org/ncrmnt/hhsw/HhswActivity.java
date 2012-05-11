package org.ncrmnt.hhsw;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

public class HhswActivity extends Activity {
	
String[] enable = {"echo 1 1 > /sys/module/sw_switch/parameters/power"};
String[] disable = {"echo 1 0 > /sys/module/sw_switch/parameters/power"};
boolean enabled = true;
	
public void RunAsRoot(String[] cmds){
	    try {
	    Process p = Runtime.getRuntime().exec("su");
	    DataOutputStream os = new DataOutputStream(p.getOutputStream());            
	    for (String tmpCmd : cmds) {
	            os.writeBytes(tmpCmd+"\n");
	    }           
	    os.writeBytes("exit\n");  
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}