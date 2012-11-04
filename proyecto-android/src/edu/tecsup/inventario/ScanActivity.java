package edu.tecsup.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScanActivity extends Activity{
	/** Called when the activity is first created. */
	 // JSON Node names
    private static final String TAG_CODIGO = "codigo";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);        
    }
    
    public void btnData(View v){
    	// getting values from selected ListItem
        String codigo = (String)"2";

        // Starting new intent
        Intent in = new Intent(getApplicationContext(),
                DataActivity.class);
        // sending pid to next activity
        in.putExtra(TAG_CODIGO, codigo);
        
        // starting new activity and expecting some response back
        startActivityForResult(in, 100);
    }
    
    public void btnEscanear(View v){
    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    	startActivityForResult(intent, 0);    	
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	   if (requestCode == 0) {
    	      if (resultCode == RESULT_OK) {
    	         String contents = intent.getStringExtra("SCAN_RESULT");
    	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
    	         TextView textView3 = (TextView) findViewById(R.id.textView3);
    	    	 textView3.setText(contents);
    	     	// getting values from selected ListItem
    	         String codigo = (String)contents;

    	         // Starting new intent
    	         Intent in = new Intent(getApplicationContext(),
    	                 DataActivity.class);
    	         // sending pid to next activity
    	         in.putExtra(TAG_CODIGO, codigo);
    	         
    	         // starting new activity and expecting some response back
    	         startActivityForResult(in, 100);
    	         // Handle successful scan
    	      } else if (resultCode == RESULT_CANCELED) {
    	         // Handle cancel
    	      }
    	   }    	   
    }

}
