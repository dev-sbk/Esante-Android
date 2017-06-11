package com.android.client.esante.activity;
import android.app.Activity;
import android.content.ActivityNotFoundException;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.client.esante.R;
import com.android.client.esante.task.AuthenticationPatientQrCodeSync;
import com.android.client.esante.task.AuthenticationSync;

public class LoginActivity extends Activity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Button btnCrCode;
    private Button btnLogIn;
    private Button bntSignUp;
    TextInputLayout usernameWrapper;
    TextInputLayout passwordWrapper;
    EditText inputEmail;
    EditText inputPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnCrCode=(Button) findViewById(R.id.btnSacnner);
        btnLogIn=(Button) findViewById(R.id.btnLogin);
        bntSignUp=(Button) findViewById(R.id.btnSignUp);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        btnCrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR(view);
            }
        });
       // new AuthenticationPatientQrCodeSync(this).execute("123456");
    }
    public void validation() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (email.isEmpty()) {
            usernameWrapper.setError("Not a valid email address!");
        } else if (password.isEmpty()) {
            passwordWrapper.setError("Not a valid password!");
        } else {
            usernameWrapper.setErrorEnabled(false);
            passwordWrapper.setErrorEnabled(false);
            new AuthenticationSync(this).execute(email,password);
        }
    }
    public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(LoginActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                new AuthenticationPatientQrCodeSync(this).execute(contents);
            }
        }
    }
}