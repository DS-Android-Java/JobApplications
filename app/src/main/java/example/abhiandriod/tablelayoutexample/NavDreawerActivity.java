package example.abhiandriod.tablelayoutexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import example.abhiandriod.tablelayoutexample.logicadenegocio.Usuario;

public class NavDreawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private Usuario ul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_dreawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ul = new Usuario();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Aca se saca el objeto
        ul = (Usuario) getIntent().getSerializableExtra("usuarioLogueado");
        System.out.println("Rol usuario: "+ ul.getRol());
        this.userPrivileges();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.openDrawer(GravityCompat.START);
    }

    private void userPrivileges() {
        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        String privilegio = prefs.getString(getString(R.string.preference_user_key), defaultValue);

        privilegio = ul.getRol();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem holder;
        //using privileges to lock data
        switch (privilegio) {
            case "administrador"://Si es administrador solo la opcion de lista de aplicaciones
                holder = menu.findItem(R.id.nav_apply_list);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_form_apply);
                holder.setEnabled(false);
                break;
            case "estandar"://Si es estandar solo se le habilitara el form de aplicacion
                holder = menu.findItem(R.id.nav_apply_list);
                holder.setEnabled(false);
                holder = menu.findItem(R.id.nav_form_apply);
                holder.setEnabled(true);
                break;
            default:    //if is none
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.moveTaskToBack(true);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_dreawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        String privilegio = prefs.getString(getString(R.string.preference_user_key), defaultValue);

        if (id == R.id.nav_form_apply) {
            Toast.makeText(getApplicationContext(), "Application Form", Toast.LENGTH_SHORT).show();
            abrirJobApplication();
        } else if (id == R.id.nav_apply_list) {
            Toast.makeText(getApplicationContext(), "Applications List", Toast.LENGTH_SHORT).show();
            abrirApplicationsList();
        } else if (id == R.id.nav_logout) {
            Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
            abrirLogin();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abrirLogin() {
        finish();
        Intent a = new Intent(this, MainActivity.class);
        startActivity(a);
    }

    public void abrirJobApplication() {
        Intent intent = new Intent(this, formActivity.class);
        intent.putExtra("usuarioLogueado", ul);
        startActivity(intent);
    }

    public void abrirApplicationsList() {
        Intent intent = new Intent(this, JobApplicationsActivity.class);
        intent.putExtra("usuarioLogueado", ul);
        startActivity(intent);
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}
