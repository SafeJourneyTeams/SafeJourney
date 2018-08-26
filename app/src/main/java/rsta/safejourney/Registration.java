package rsta.safejourney;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import rsta.safejourney.Urls;

public class Registration extends AppCompatActivity {

    private EditText name,phone,password;
    private Button reg;
    private RequestQueue requestQueue;
    private RadioGroup gender, driver;
    private StringRequest request;
    private String genderSend,driverSend;
c
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=(EditText) findViewById(R.id.regName);
        phone=(EditText) findViewById(R.id.regPhone);
        password=(EditText) findViewById(R.id.pswd);
        reg=(Button) findViewById(R.id.regButton);

        requestQueue = Volley.newRequestQueue(this);

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

                if (TextUtils.isEmpty(sname)) {
                    name.setError("Please enter name");
                    name.requestFocus();
                    return;
                }

                if(checkForMobile()){
                    phone.setError("Enter a valid phone number");
                    phone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(spassword)) {
                    password.setError("Enter a password");
                    password.requestFocus();
                    return;
                }

                    reg(sname,sphone,spassword,genderSend,driverSend);
                    SharedPreferences preferences=getSharedPreferences("regPreference", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.clear();
                    editor.putString("name",sname);
                    editor.putString("phone",sphone);
                    editor.putString("password",spassword);
                    editor.putString("gender",genderSend);
                    editor.putString("driver",driverSend);
                    editor.apply();
                    show();

            }
        });
    }
    public void show(){
        Intent intent=new Intent(Registration.this,RegisterSuccess.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void reg(final String sname, final String sphone, final String spassword, final String gender, final  String driver){

        request = new StringRequest(Request.Method.POST, Urls.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject.names().get(0).equals("success")) {
                        Toast.makeText(getApplicationContext(),"Welcome\n"+jsonobject.getString("name"),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplication(),RegisterSuccess.class));
                    }
                    if(jsonobject.has("exist")){
                        Toast.makeText(getApplicationContext(),""+jsonobject.getString("exist"),Toast.LENGTH_LONG).show();
                    }
                    if(jsonobject.has("missingParam")){
                        Toast.makeText(getApplicationContext(),""+jsonobject.getString("missingParam"),Toast.LENGTH_LONG).show();
                    }
                    if(jsonobject.has("dataError")){
                        Toast.makeText(getApplicationContext(),""+jsonobject.getString("dataError"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("name", sname);
                hashMap.put("phone", sphone);
                hashMap.put("password", spassword);
                hashMap.put("gender", genderSend);
                hashMap.put("driver", driverSend);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }
    public boolean checkForMobile() {
        Context c;
        String mStrMobile = phone.getText().toString();
        if (android.util.Patterns.PHONE.matcher(mStrMobile).matches()) {
            return true;
        }
        else
            return false;
    }
}
