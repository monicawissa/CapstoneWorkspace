package com.example.Workspace.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Workspace.R;
import com.example.Workspace.network.Token;
import com.example.Workspace.network.client;
import com.example.Workspace.network.user;
import com.example.Workspace.network.workspaceEndPoint;
import com.example.Workspace.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Registration extends AppCompatActivity {
    EditText email,firstName,lastName,phone;
    TextInputLayout password,confirmPass;
    TextView Signup_tv;
    TextView forget_tv;
    //private ProgressBar progressBar;

    Button reg_button;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.editText_email);
        password=findViewById(R.id.editText_password);
        firstName=findViewById(R.id.editText_first);
        lastName=findViewById(R.id.editText_last);

        phone=findViewById(R.id.editText_phone);
        confirmPass=findViewById(R.id.editText_confirm);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar_back);

        reg_button=findViewById(R.id.create_account);
    }
    public void Register(View view) {
        final String firstname = firstName.getText().toString().trim();
        final String lastname = lastName.getText().toString().trim();

        final String phonee = phone.getText().toString().trim();

        String emaill = email.getText().toString().trim();
        String passwordd = password.getEditText().getText().toString().trim();
        String password_conf = confirmPass.getEditText().getText().toString().trim();

        if (!validateForm()) {
            return;
        }
//        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emaill, passwordd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            user user = new user(firstname,lastname,emaill,phonee,passwordd);

                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Registration.this, "Registration success", Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Log.d("taggg","Failed on saving data." + task.getException());
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        //        workspaceEndPoint service =new client().getRetrofit().create(workspaceEndPoint.class);
        //        JsonObject json=new JsonObject();
        //        json.addProperty("firstName",firstName.getText().toString());
        //        json.addProperty("lastName",lastName.getText().toString());
        //        json.addProperty("email",email.getText().toString());
        //        json.addProperty("phone",phone.getText().toString());
        //        json.addProperty("password",password.getEditText().getText().toString());
        //
        //        Call<Token> call=service.login(json);
        //        call.enqueue(new Callback<Token>() {
        //            @Override
        //            public void onResponse(@NotNull Call<Token> call, @NotNull Response<Token> response) {
        //                if(response.code()==200){
        //                    assert response.body() != null;
        //                    String s=response.body().getXAuthToken();
        //                    Log.d("Tagggg","token"+s);
        //
        //                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        //                    intent.putExtra("x-auth-token",s);
        //                    startActivity(intent);
        //                    finish();
        //                }
        //                else{
        //                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
        //                }
        //            }
        //
        //            @Override
        //            public void onFailure(@NotNull Call<Token> call, @NotNull Throwable t) {
        //                Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
        //            }
        //
        //        });
    }
    private boolean validateForm() {
        boolean valid = true;
        final String firstname = firstName.getText().toString().trim();
        final String lastname = lastName.getText().toString().trim();

        final String phonee = phone.getText().toString().trim();

        String emaill = email.getText().toString().trim();
        String passwordd = password.getEditText().getText().toString().trim();
        String password_conf = confirmPass.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(emaill)) {
            email.setError("Required.");
            email.requestFocus();
            valid = false;
        } else {
            email.setError(null);
        }
        if (TextUtils.isEmpty(firstname)) {
            firstName.setError("Required.");
            firstName.requestFocus();
            valid = false;
        } else {
            firstName.setError(null);
        }
        if (TextUtils.isEmpty(lastname)) {
            lastName.setError("Required.");
            lastName.requestFocus();
            valid = false;
        } else {
            lastName.setError(null);
        }
        if (TextUtils.isEmpty(phonee)||(phonee.length()!=10)) {
            phone.setError("Required 10 digit.");
            phone.requestFocus();
            valid = false;
        } else {
            phone.setError(null);
        }
        if (TextUtils.isEmpty(passwordd)||passwordd.length()<6) {
            password.setError("Required.");
            password.requestFocus();

            valid = false;
        } else {
            password.setError(null);
        }
        if (TextUtils.isEmpty(password_conf)) {
            confirmPass.setError("Required.");
            confirmPass.requestFocus();

            valid = false;
        } else {
            confirmPass.setError(null);
        }
        if (!password_conf.equals(passwordd)) {
            confirmPass.setError("confirm password incorrect.");
            confirmPass.requestFocus();

            valid = false;
        } else {
            confirmPass.setError(null);
        }

        return valid;
    }


}
