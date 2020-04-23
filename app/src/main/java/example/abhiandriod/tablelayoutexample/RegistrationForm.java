package example.abhiandriod.tablelayoutexample;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import example.abhiandriod.tablelayoutexample.accesodatos.ModelData;
import example.abhiandriod.tablelayoutexample.logicadenegocio.Usuario;

public class RegistrationForm extends AppCompatActivity {

    private EditText userNameR;
    private EditText passwordR;
    private FloatingActionButton addUserBtn;
    private ModelData modelData = ModelData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        userNameR = (EditText)findViewById(R.id.userNameR);
        passwordR = (EditText)findViewById(R.id.passwordR);
        addUserBtn = (FloatingActionButton) findViewById(R.id.addUserBtn);

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
            }
        });

        //Aca se prepara el popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int)(alto * 0.40));
    }

    public void AddUser(){
        if(validateForm()){
            Usuario u = new Usuario();
            u.setUsuario(userNameR.getText().toString());
            u.setClave(passwordR.getText().toString());
            u.setRol("estandar");

            modelData.getUsuarios().add(u);
            finish();
            Toast.makeText(this,"User added successfully", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validateForm() {
        int error = 0;
        if (TextUtils.isEmpty(this.userNameR.getText())) {
            userNameR.setError("Email required");//Asi se le coloca un mensaje de error en los campos en android
            error++;
        }
        if (TextUtils.isEmpty(this.passwordR.getText())) {
            passwordR.setError("Password required");
            error++;
        }
        if(this.passwordR.getText().length()<8){
            passwordR.setError("Password must be at least 8 characters");
            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Some errors", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
