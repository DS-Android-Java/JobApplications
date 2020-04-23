package example.abhiandriod.tablelayoutexample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import example.abhiandriod.tablelayoutexample.accesodatos.ModelData;
import example.abhiandriod.tablelayoutexample.logicadenegocio.Usuario;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerBtn;
    private EditText username;
    private EditText password;
    private Window window;
    private ModelData modelData = ModelData.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initiate a button
        loginButton = (Button) findViewById(R.id.loginBtn);//Primero casting con el boton
        registerBtn = (Button) findViewById(R.id.registerBtn);
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        String primaryDark = "#3791a4";
        String primary = "#1fa1bc";
        String background = "#b8141b";

        this.window = getWindow();
        window.setStatusBarColor(Color.parseColor(primaryDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));

        // perform click event on the button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Evento escuchador del boton
                valida();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });
    }

    public void registration(){
        Intent i = new Intent(this, RegistrationForm.class);
        startActivity(i);
    }
    public void valida() {
        //ModelData md = new ModelData();
        Usuario userSingin = new Usuario();
        List<Usuario> users = modelData.getUsuarios();
        Boolean founded = false;

        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this, "You must complete all the fields", Toast.LENGTH_LONG).show();
        } else {
            if (!username.getText().toString().contains("@")) {
                Toast.makeText(this, "The user must be a email", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Validating", Toast.LENGTH_LONG).show();
                for (Usuario u : users) {
                    if (u.getUsuario().equals(username.getText().toString()) && u.getClave().equals(password.getText().toString())) {
                        founded = true;
                        userSingin = u;
                    }
                }
                if(founded == false){
                    Toast.makeText(this, "The username or password are incorrect", Toast.LENGTH_LONG).show();
                }else{

                    Intent i = new Intent(this, NavDreawerActivity.class);
                    i.putExtra("usuarioLogueado", userSingin);
                    startActivity(i);
                }
            }
        }
    }

}
