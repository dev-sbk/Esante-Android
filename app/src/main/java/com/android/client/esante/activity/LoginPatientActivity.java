package com.android.client.esante.activity;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.client.esante.R;
import com.android.client.esante.domain.Docteur;
import com.android.client.esante.domain.Patient;
import com.android.client.esante.repository.EsanteRepository;
import com.android.client.esante.task.AuthenticationPatientQrCodeSync;
public class LoginPatientActivity extends Activity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Button btnCrCode;
    private Button btnLogIn;
    private EsanteRepository repository;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        this.repository = new EsanteRepository(this);
        btnCrCode = (Button) findViewById(R.id.btnSacnner);
        btnLogIn = (Button) findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginMedecinActivity.class);
                finish();
                startActivity(i);
            }
        });
        btnCrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR(view);
            }
        });
        Object ob = repository.getUserLogged();
        if (ob != null) {
            if (ob instanceof Patient) {
                Patient pat = (Patient) ob;
                Intent i = new Intent(this, PatientActivity.class);
                i.putExtra("idPat", pat.getIdPatient());
                if (pat.getLastName() != null)
                    i.putExtra("nom", pat.getLastName());
                else i.putExtra("nom", "");
                if (pat.getFirstName() != null)
                    i.putExtra("prenom", pat.getFirstName());
                else i.putExtra("prenom", "");

                if (pat.getEmail() != null)
                    i.putExtra("email", pat.getEmail());
                else i.putExtra("email", "");
               this.finish();
               this.startActivity(i);
            } else if (ob instanceof Docteur) {
                Docteur doc = (Docteur) ob;
                Log.v("Docteur",doc.toString());
                Intent i = new Intent(this, DocteurActivity.class);
                i.putExtra("id", doc.getIdDocteur());
                if (doc.getLastName() != null)
                    i.putExtra("nom", doc.getLastName());
                else i.putExtra("nom", "");
                if (doc.getFirstName() != null)
                    i.putExtra("prenom", doc.getFirstName());
                else i.putExtra("prenom", "");
                if (doc.getEmail() != null)
                    i.putExtra("email", doc.getEmail());
                else i.putExtra("email", "");
                this.finish();
                this.startActivity(i);
            }
        }
       //new AuthenticationPatientQrCodeSync(this).execute("123456");
    }

    public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(LoginPatientActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
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