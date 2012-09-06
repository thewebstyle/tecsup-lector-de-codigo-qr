package edu.tecsup.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity{
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);        
    }
    
    public void btnScan(View v){
        startActivity(new Intent(this, ScanActivity.class));
    }

    
    public void btnAyuda(View v){
        startActivity(new Intent(this, AyudaActivity.class));
    }
    
    
    public void btnAcerca(View v){
        startActivity(new Intent(this, AcercaActivity.class));
    }
    
}
