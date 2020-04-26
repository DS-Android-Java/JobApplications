package example.abhiandriod.tablelayoutexample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import example.abhiandriod.tablelayoutexample.accesodatos.ModelData;
import example.abhiandriod.tablelayoutexample.logicadenegocio.JobApplication;
import example.abhiandriod.tablelayoutexample.logicadenegocio.Usuario;
import example.abhiandriod.tablelayoutexample.ui.DatePickerFragment;

public class formActivity extends AppCompatActivity {

    private Spinner spinnerP;
    private Spinner spinnerAP;
    private Button btnUpload;
    private EditText etDatePicker;
    private Window window;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etStreetAddress;
    private EditText etStreetAddress2;
    private EditText etCity;
    private EditText etState;
    private EditText etPostalCode;
    private EditText etEmail;
    private EditText etAreaCode;
    private EditText etPhoneNumber;
    private Button sentBtn;

    private Usuario ul = new Usuario();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        String primaryDark = "#3791a4";
        String primary = "#1fa1bc";
        String background = "#b8141b";

        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etStreetAddress = (EditText)findViewById(R.id.etStreetAddress);
        etStreetAddress2 = (EditText)findViewById(R.id.etStreetAddress2);
        etCity = (EditText)findViewById(R.id.etCity);
        etState = (EditText)findViewById(R.id.etState);
        etPostalCode = (EditText)findViewById(R.id.etPostalCode);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etAreaCode = (EditText)findViewById(R.id.etAreaCode);
        etPhoneNumber= (EditText)findViewById(R.id.etPhoneNumber);
        sentBtn = (Button)findViewById(R.id.btnSent);

        //Saco de nuevo el usuariologueado
        ul = (Usuario) getIntent().getSerializableExtra("usuarioLogueado");

        this.window = getWindow();
        window.setStatusBarColor(Color.parseColor(primaryDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));


        ArrayList<String> paises = new ArrayList<>();
        paises.add("Costa Rica");
        paises.add("USA");
        paises.add("Rusia");
        paises.add("Mexico");
        paises.add("Inglaterra");
        paises.add("España");
        paises.add("Panama");
        spinnerP = findViewById(R.id.spinnerP);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, paises);
        spinnerP.setAdapter(adaptador);


        ArrayList<String> puestos = new ArrayList<>();
        puestos.add("System Engineer");
        puestos.add("Software Tester");
        puestos.add("Data Analist");
        puestos.add("Electronic Engineer");
        puestos.add("Data Miner");
        spinnerAP = findViewById(R.id.spinnerAP);
        ArrayAdapter<String> adaptadorAP = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, puestos);
        spinnerAP.setAdapter(adaptadorAP);

        etDatePicker = (EditText)findViewById(R.id.etDatePicker);
        etDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.etDatePicker:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        btnUpload = (Button)findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadResume();
            }
        });

        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addJobApplication();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etDatePicker.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void uploadResume(){
        Toast.makeText(this,"Uploading Resume" , Toast.LENGTH_LONG).show();
    }

    public void addJobApplication(){
        if(validate()){//Si los campos son validados puede hacer la insersion
            JobApplication mijapplication = new JobApplication();
            ArrayList<JobApplication> miJAS = (ArrayList<JobApplication>) ModelData.getInstance().getListajobApplications();

            mijapplication.setFirstName(etFirstName.getText().toString());
            mijapplication.setLastName(etLastName.getText().toString());
            mijapplication.setStreetAddress(etStreetAddress.getText().toString());
            mijapplication.setSecondStreetAddress(etStreetAddress2.getText().toString());
            mijapplication.setCity(etCity.getText().toString());
            mijapplication.setState(etState.getText().toString());
            mijapplication.setPostalCode(etPostalCode.getText().toString());
            mijapplication.setEmail(etEmail.getText().toString());
            mijapplication.setAreaCode(etAreaCode.getText().toString());
            mijapplication.setPhoneNumber(etPhoneNumber.getText().toString());
            mijapplication.setDate(etDatePicker.getText().toString());
            mijapplication.setPosition(spinnerAP.getSelectedItem().toString());
            mijapplication.setCountry(spinnerP.getSelectedItem().toString());

            miJAS.add(mijapplication);

            Intent i = new Intent(this, NavDreawerActivity.class);
            i.putExtra("usuarioLogueado", ul);
            startActivity(i);

            Toast.makeText(this, "Job Application submitted succesfully!",Toast.LENGTH_LONG).show();
        }
    }

    public boolean validate(){
        int valido=0;

        if (TextUtils.isEmpty(this.etFirstName.getText())) {
            etFirstName.setError("Required First Name");//Asi se le coloca un mensaje de error en los campos en android
            valido++;
        }
        if(TextUtils.isEmpty(this.etLastName.getText())){
            etLastName.setError("Required Last Name");
            valido++;
        }
        if(TextUtils.isEmpty(this.etStreetAddress.getText())){
            etStreetAddress.setError("Required Street Addres");
            valido++;
        }
        if(TextUtils.isEmpty(this.etStreetAddress2.getText())){
            etStreetAddress2.setError("Required Second Street Address");
            valido++;
        }
        if(TextUtils.isEmpty(this.etCity.getText())){
            etCity.setError("Required City");
            valido++;
        }
        if(TextUtils.isEmpty(this.etState.getText())){
            etState.setError("Required State");
            valido++;
        }
        if(TextUtils.isEmpty(this.etPostalCode.getText())){
            etPostalCode.setError("Required Postal Code");
            valido++;
        }
        if(TextUtils.isEmpty(this.etEmail.getText())){
            etEmail.setError("Required Email");
            valido++;
        }
        if(TextUtils.isEmpty(this.etAreaCode.getText())){
            etAreaCode.setError("Required Area Code");
            valido++;
        }
        if(TextUtils.isEmpty(this.etPhoneNumber.getText())){
            etPhoneNumber.setError("Required Phone Number");
            valido++;
        }
        if(TextUtils.isEmpty(this.etDatePicker.getText())){
            etDatePicker.setError("Required Date");
            valido++;
        }
        if (valido > 0) {
            Toast.makeText(getApplicationContext(), "You must complete all the fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
