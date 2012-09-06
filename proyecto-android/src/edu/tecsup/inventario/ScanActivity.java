package edu.tecsup.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScanActivity extends Activity{
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);        
    }
    
    public void btnData(View v){
        startActivity(new Intent(this, DataActivity.class));
    }

}
