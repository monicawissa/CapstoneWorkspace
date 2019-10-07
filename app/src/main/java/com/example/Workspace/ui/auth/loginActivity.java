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
import com.example.Workspace.network.workspaceEndPoint;
import com.example.Workspace.ui.main.MainActivity;
import com.example.Workspace.ui.splash.splash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

public class loginActivity extends AppCompatActivity {
    EditText email;
    TextInputLayout password;
    TextView Signup_tv;
    TextView forget_tv;
    private FirebaseAuth mAuth;
    Button login_button;
    Button google_button;
    Button face_button;
//    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llogin);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.login_email_editText);

        password=findViewById(R.id.login_password_editText);

        Signup_tv=findViewById(R.id.login_new_account_text_view);
        forget_tv=findViewById(R.id.login_forget_password_text_view);

        login_button=findViewById(R.id.login_button);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar_bb);
        google_button=findViewById(R.id.login_google_button);
        face_button=findViewById(R.id.login_facebook_button);
    }
    public void sign_up(View view) {
        Intent intent=new Intent(new Intent(this, Registration.class));
        startActivity(intent);
        finish();

    }
    public void login(View view) {
        String emaill = email.getText().toString().trim();
        String passwordd = password.getEditText().getText().toString().trim();

        if (!validateForm()) {
            return;
        }
//        progressBar.setVisibility(View.VISIBLE);
        //create user
        mAuth.signInWithEmailAndPassword(emaill, passwordd)
                .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(loginActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(loginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
        //login using the api

        //        workspaceEndPoint service =new client().getRetrofit().create(workspaceEndPoint.class);
        //        JsonObject json=new JsonObject();
        //        json.addProperty("email",email.getText().toString());
        //        json.addProperty("password",password.getEditText().getText().toString());
        //        Call<Token> call=service.login(json);
        //        call.enqueue(new Callback<Token>() {
        //             @Override
        //             public void onResponse(@NotNull Call<Token> call, @NotNull Response<Token> response) {
        //                 if(response.code()==200){
        //                     assert response.body() != null;
        //                     String s=response.body().getXAuthToken();
        //                     Log.d("Tagggg","token"+s);
        //
        //                     Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        //                     intent.putExtra("x-auth-token",s);
        //                     startActivity(intent);
        //                     finish();
        //                 }
        //                 else{
        //                     Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
        //                 }
        //             }
        //
        //             @Override
        //             public void onFailure(@NotNull Call<Token> call, @NotNull Throwable t) {
        //                 Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
        //             }
        //
        //        });
    }

    public void forget_your_password(View view) {
    }
    public void login_facebook(View view) {
    }
    public void login_google(View view) {
    }
    private boolean validateForm() {
        boolean valid = true;
        String emaill = email.getText().toString().trim();
        String passwordd = password.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(emaill)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        if (TextUtils.isEmpty(passwordd)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

}
