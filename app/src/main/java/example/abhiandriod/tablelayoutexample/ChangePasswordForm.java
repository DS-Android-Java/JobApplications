package example.abhiandriod.tablelayoutexample;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import example.abhiandriod.tablelayoutexample.accesodatos.ModelData;
import example.abhiandriod.tablelayoutexample.logicadenegocio.Usuario;

public class ChangePasswordForm extends AppCompatActivity {

    private EditText userNameRU;
    private EditText passwordRU;
    private EditText oldPassword;
    private EditText passwordConfirmU;
    private FloatingActionButton chgPassBtnU;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_form);

        userNameRU = (EditText) findViewById(R.id.userNameRU);
        passwordRU = (EditText) findViewById(R.id.passwordRU);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        passwordConfirmU = (EditText) findViewById(R.id.passwordConfirmU);
        chgPassBtnU = (FloatingActionButton) findViewById(R.id.chgPassBtnU);

        chgPassBtnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });
        //Aca se prepara el popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int) (ancho * 0.90), (int) (alto * 0.53));
    }

    public void ChangePassword() {//Funcion de proceso de cambio de clave en la lista
        if (validateForm()) {
            ArrayList<Usuario> miListU = (ArrayList<Usuario>) ModelData.getInstance().getUsuarios();
            boolean existeUsuario = false;
            boolean existeClave = false;
            Usuario miUsuarioModificable = new Usuario();
            for (Usuario u : miListU) {//For que valida si el usario existe
                if (u.getUsuario().equals(userNameRU.getText().toString()) &&
                        u.getClave().equals(oldPassword.getText().toString())) {//Si algun usuario es compatible con el usuairo ingresado
                    miUsuarioModificable = u;
                    if (miUsuarioModificable.getClave().equals(passwordRU.getText().toString())) {
                        passwordRU.setError("The password can not be the same as the previous one");
                        existeClave = true;
                        //Toast.makeText(this,"The password can not be the same as the previous one", Toast.LENGTH_LONG).show();
                    } else {
                        miUsuarioModificable.setClave(passwordRU.getText().toString());//Se asigna la nueva clave al usuario
                        miListU.remove(u);
                        miListU.add(miUsuarioModificable);
                        existeUsuario = true;
                        break;
                    }
                }
            }

            if (existeUsuario && existeClave == false) {//Si el usuario que se desea modificar fue encontrado
                Toast.makeText(this, "Password successfully changed", Toast.LENGTH_LONG).show();
                finish();
            }else if(!existeUsuario && !existeClave){
                Toast.makeText(this, "This username was not found!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean validateForm() {//Funcion para validar los campo del usuario nuevo
        int error = 0;
        if (TextUtils.isEmpty(this.userNameRU.getText())) {
            userNameRU.setError("Email required");//Asi se le coloca un mensaje de error en los campos en android
            error++;
        }
        if (TextUtils.isEmpty(this.passwordRU.getText())) {
            passwordRU.setError("Password required");
            error++;
        }
        if(!this.userNameRU.getText().toString().contains("@")){
            userNameRU.setError("The user must be an email");
            error++;
        }
        if (this.passwordRU.getText().length() < 8) {
            passwordRU.setError("Password must be at least 8 characters");
            error++;
        }
        if (!this.passwordRU.getText().toString().equals(passwordConfirmU.getText().toString())) {
            passwordConfirmU.setError("The passwords must be the same");
            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Some errors", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
