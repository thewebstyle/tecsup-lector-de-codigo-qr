package edu.tecsup.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DataActivity extends Activity{
	private String array_spinner[];
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);    
        array_spinner=new String[3];
        array_spinner[0]="Operativo";
        array_spinner[1]="Dañado";
        array_spinner[2]="Malogrado";
        Spinner s = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter adapter = new ArrayAdapter(this,
        android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
    }  

}
