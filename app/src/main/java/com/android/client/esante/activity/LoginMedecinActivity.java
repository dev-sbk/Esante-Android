package com.android.client.esante.activity;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.client.esante.R;
import com.android.client.esante.task.AuthenticationSync;

public class LoginMedecinActivity extends Activity {
    private Button btnLogIn;
    private Button bntSignUp;
    TextInputLayout usernameWrapper;
    TextInputLayout passwordWrapper;
    EditText inputEmail;
    EditText inputPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_medecin);
        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogIn=(Button) findViewById(R.id.btnLogin);
        bntSignUp=(Button) findViewById(R.id.btnSignUp);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }
    public void validation() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (email.isEmpty()) {
            usernameWrapper.setError("Adresse e-mail non valide!");
        } else if (password.isEmpty()) {
            passwordWrapper.setError("Mot de passe incorrecte!");
        } else {
            usernameWrapper.setErrorEnabled(false);
            passwordWrapper.setErrorEnabled(false);
            new AuthenticationSync(this).execute(email,password);
        }
    }

}