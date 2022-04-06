package com.example.sonsinavcalisma;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
ListView lvUsers;
int CALL_CODE=50;
    DatabaseClass databaseClass= new DatabaseClass(UserListActivity.this);
    ArrayList<String> contactList=new ArrayList<String>();
    ArrayList<String> contactListName=new ArrayList<String>();
    static int userid;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        lvUsers=findViewById(R.id.lv_Users);
        userList();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ac,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_call:
                callUser();
                break;
            case R.id.menu_newContact:
                Intent intent = new Intent(UserListActivity.this,UserRegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_deleteContact:
                userDelete();
                break;
            case R.id.menu_updateContact:
                userUpdate();
                break;
            case R.id.menu_exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callUser() {
    if(ActivityCompat.checkSelfPermission(UserListActivity.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
        callPhone();
    }if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Info");
            builder.setMessage("This application needs CALL permision to call someone");
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.show();

        }

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CALL_PHONE},CALL_CODE);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                Toast.makeText(this, "Call Phone Denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void callPhone() {
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String userId = contactList.get((i*2)).toString();
                String phoneNo=databaseClass.findUserPhoneNumber(userId);
             // Toast.makeText(UserListActivity.this,phoneNo,Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNo));
                startActivity(callIntent);
            }
        });

    }


    private void userUpdate() {
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private void userDelete() {
            lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int userId = Integer.parseInt(contactList.get((i*2)));
                    userid=userId;
                    databaseClass.deleteUser(userId);
                    userList();
                }
            });
    }
    public void userList(){
        contactList=databaseClass.viewAllUsers();
        for(int i=1;i<contactList.size();i+=2){
            contactListName.add(contactList.get(i));
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,contactListName);
        lvUsers.setAdapter(adapter);
    }

}