package edu.tecsup.inventario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DataActivity extends Activity{
	private String array_spinner[];

        EditText txtCodigo;
        EditText txtNombre_Equipo;
        EditText txtModelo;
        EditText txtMarca;
        EditText txtSerie;
        Spinner txtEstado;
        EditText txtObservacion;
                
        EditText txtCreatedAt;
        Button btnGuardar;        
     
        String codigo;
     
        // Progress Dialog
        private ProgressDialog pDialog;
     
        // JSON parser class
        JSONParser jsonParser = new JSONParser();
     
        // single product url
        private static final String url_product_details = "http://192.168.14.250/android/get_equipo_details.php";
     
        // url to update product
        private static final String url_update_product = "http://192.168.14.250/android/update_equipo.php";
     
        // JSON Node names
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_EQUIPO = "equipo";        
        private static final String TAG_CODIGO = "codigo";
        private static final String TAG_NOMBRE_EQUIPO = "nombre_equipo";
        private static final String TAG_MODELO = "modelo";
        private static final String TAG_MARCA = "marca";
        private static final String TAG_SERIE = "serie";
        private static final String TAG_ESTADO = "estado";
        private static final String TAG_OBSERVACION = "observacion";        
     
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.data);
            
            //Pintar Spinner View
            array_spinner=new String[3];
            array_spinner[0]="Operativo";
            array_spinner[1]="Dañado";
            array_spinner[2]="Malogrado";
            Spinner s = (Spinner) findViewById(R.id.spinnerEstado);
            ArrayAdapter adapter = new ArrayAdapter(this,
            android.R.layout.simple_spinner_item, array_spinner);
            s.setAdapter(adapter);
     
            // save button
            btnGuardar = (Button) findViewById(R.id.btnGuardar);           
     
            // getting product details from intent
            Intent i = getIntent();
     
            // getting product id (pid) from intent
            codigo = i.getStringExtra(TAG_CODIGO);
     
            // Getting complete product details in background thread
            new GetProductDetails().execute();
     
            // save button click event
            btnGuardar.setOnClickListener(new View.OnClickListener() {
     
                public void onClick(View arg0) {
                    // starting background task to update product
                    new SaveProductDetails().execute();
                }
            });
          
        }
     
        /**
         * Background Async Task to Get complete product details
         * */
        class GetProductDetails extends AsyncTask<String, String, String> {
     
            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(DataActivity.this);
                pDialog.setMessage("Loading product details. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }
     
            /**
             * Getting product details in background thread
             * */
            protected String doInBackground(String... params) {
     
                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {
                        // Check for success tag
                        int success;
                        try {
                            // Building Parameters
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("codigo", codigo));
     
                            // getting product details by making HTTP request
                            // Note that product details url will use GET request
                            JSONObject json = jsonParser.makeHttpRequest(
                                    url_product_details, "GET", params);
     
                            // check your log for json response
                            Log.d("Single Product Details", json.toString());
     
                            // json success tag
                            success = json.getInt(TAG_SUCCESS);
                            if (success == 1) {
                                // successfully received product details
                                JSONArray equipoObj = json
                                        .getJSONArray(TAG_EQUIPO); // JSON Array
     
                                // get first product object from JSON Array
                                JSONObject equipo = equipoObj.getJSONObject(0);
     
                                // product with this pid found
                                // Edit Text
                                txtCodigo = (EditText) findViewById(R.id.inputCodigo);
                                txtNombre_Equipo = (EditText) findViewById(R.id.inputEquipo);
                                txtModelo = (EditText) findViewById(R.id.inputModelo);
                                txtMarca = (EditText) findViewById(R.id.inputMarca);
                                txtSerie = (EditText) findViewById(R.id.inputSerie);
                                txtEstado = (Spinner) findViewById(R.id.spinnerEstado);                        
                                txtObservacion = (EditText) findViewById(R.id.inputObservacion);
     
                                // display product data in EditText
                                txtCodigo.setText(equipo.getString(TAG_CODIGO));
                                txtNombre_Equipo.setText(equipo.getString(TAG_NOMBRE_EQUIPO));
                                txtModelo.setText(equipo.getString(TAG_MODELO));
                                txtMarca.setText(equipo.getString(TAG_MARCA));
                                txtSerie.setText(equipo.getString(TAG_SERIE));
                                System.out.println("PRUEBAS!!!"+equipo.getString(TAG_ESTADO));
                                if(equipo.getString(TAG_ESTADO).equals("Operativo")){                                	
                                	txtEstado.setSelection(0);                                	
                                }else if (equipo.getString(TAG_ESTADO).equals("Dañado")){
                                	txtEstado.setSelection(1);
                                }else if (equipo.getString(TAG_ESTADO).equals("Malogrado")){
                                	txtEstado.setSelection(2);
                                }
                                System.out.println(txtEstado.getSelectedItem());
                                txtObservacion.setText(equipo.getString(TAG_OBSERVACION));
     
                            }else{
                                // product with pid not found
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
     
                return null;
            }
     
            /**
             * After completing background task Dismiss the progress dialog
             * **/
            protected void onPostExecute(String file_url) {
                // dismiss the dialog once got all details
                pDialog.dismiss();
            }
        }
     
        /**
         * Background Async Task to  Save product Details
         * */
        class SaveProductDetails extends AsyncTask<String, String, String> {
     
            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(DataActivity.this);
                pDialog.setMessage("Guardando equipo ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }
     
            /**
             * Saving product
             * */
            protected String doInBackground(String... args) {
     
                // getting updated data from EditTexts
                String codigo = txtCodigo.getText().toString();
                //String equipo = txtNombre_Equipo.getText().toString();
                //String modelo = txtModelo.getText().toString();
                //String marca = txtMarca.getText().toString();
                //String serie = txtSerie.getText().toString();                
                String estado = txtEstado.getSelectedItem().toString();
                String observacion = txtObservacion.getText().toString();
     
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();                
                params.add(new BasicNameValuePair(TAG_CODIGO, codigo));
                //params.add(new BasicNameValuePair(TAG_NOMBRE_EQUIPO, equipo));
                //params.add(new BasicNameValuePair(TAG_MODELO, modelo));
                //params.add(new BasicNameValuePair(TAG_MARCA, marca));
                //params.add(new BasicNameValuePair(TAG_SERIE, serie));
                params.add(new BasicNameValuePair(TAG_ESTADO, estado));
                params.add(new BasicNameValuePair(TAG_OBSERVACION, observacion));
     
                // sending modified data through http request
                // Notice that update product url accepts POST method
                JSONObject json = jsonParser.makeHttpRequest(url_update_product,
                        "POST", params);
     
                // check json success tag
                try {
                    int success = json.getInt(TAG_SUCCESS);
     
                    if (success == 1) {
                        // successfully updated
                        Intent i = getIntent();
                        // send result code 100 to notify about product update
                        setResult(100, i);
                        finish();
                    } else {
                        // failed to update product
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
     
                return null;
            }
     
            /**
             * After completing background task Dismiss the progress dialog
             * **/
            protected void onPostExecute(String file_url) {
                // dismiss the dialog once product uupdated
                pDialog.dismiss();
            }
        }   
        
     
            /**
             * After completing background task Dismiss the progress dialog
             * **/
            protected void onPostExecute(String file_url) {
                // dismiss the dialog once product deleted
                pDialog.dismiss();          
        
        }  

}
