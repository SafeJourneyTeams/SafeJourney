package rsta.safejourney;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    EditText name,phone,password;
    Button reg;
    String url="";
    private RadioGroup gender, driver;
    String genderSend,driverSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=(EditText) findViewById(R.id.regName);
        phone=(EditText) findViewById(R.id.regPhone);
        password=(EditText) findViewById(R.id.pswd);
        reg=(Button) findViewById(R.id.regButton);

        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String sname=name.getText().toString();
                String sphone=phone.getText().toString();
                String spassword=password.getText().toString();

                gender = (RadioGroup) findViewById(R.id.regGenderGp);
                int radioButtonID = gender.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) gender.findViewById(radioButtonID);
                genderSend = (String) radioButton.getText();

                driver = (RadioGroup) findViewById(R.id.dlicense);
                int driverSelected = driver.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) driver.findViewById(driverSelected);
                driverSend = (String) rb.getText();

                if(sname.isEmpty() || spassword.length() <6 || sphone.length() !=8){
                    Snackbar.make(null, "Please Fill Form Fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    reg(sname,sphone,spassword,genderSend,driverSend);
                    SharedPreferences preferences=getSharedPreferences("regPreference", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.clear();
                    editor.putString("name",sname);
                    editor.putString("phone",sphone);
                    editor.putString("password",spassword);
                    editor.putString("gender",genderSend);
                    editor.putString("driver",driverSend);
                    editor.commit();
                    show();
                }
            }
        });
    }
    public void show(){
        Intent intent=new Intent(Registration.this,RegisterSuccess.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void reg(final String name, final String phone, final String password, final String gender, final  String driver){
       RequestQueue requestQueue = Volley.newRequestQueue(Registration.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(Registration.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("name",name);
                stringMap.put("phone",phone);
                stringMap.put("password",password);
                stringMap.put("gender",genderSend);
                stringMap.put("driver",driverSend);
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
