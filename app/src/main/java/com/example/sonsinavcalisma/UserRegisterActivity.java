package com.example.sonsinavcalisma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegisterActivity extends AppCompatActivity {
EditText etContactName,etTelephoneNo;
Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

etContactName=findViewById(R.id.et_userNameContact);
etTelephoneNo=findViewById(R.id.et_userTelNo);
btnRegister=findViewById(R.id.btn_Register);
btnRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(!TextUtils.isEmpty(etContactName.getText().toString())&&!TextUtils.isEmpty(etTelephoneNo.getText().toString())){
            DatabaseClass databaseClass=new DatabaseClass(UserRegisterActivity.this);
            if(databaseClass.insertUser(etContactName.getText().toString(),etTelephoneNo.getText().toString())){
                Toast.makeText(UserRegisterActivity.this,"Register Successfull",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserRegisterActivity.this,UserListActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(UserRegisterActivity.this,"Register Failed",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(UserRegisterActivity.this,"Fields Cannot Be Empty",Toast.LENGTH_SHORT).show();
        }
    }
});



    }
}