package example.abhiandriod.tablelayoutexample;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.abhiandriod.tablelayoutexample.accesodatos.ModelData;
import example.abhiandriod.tablelayoutexample.adapter.ApplicationAdapter;
import example.abhiandriod.tablelayoutexample.helper.RecyclerItemTouchHelper;
import example.abhiandriod.tablelayoutexample.logicadenegocio.JobApplication;

public class JobApplicationsActivity extends AppCompatActivity
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, ApplicationAdapter.JobApplicationAdapterListener {

    private RecyclerView mRecyclerView;
    private ApplicationAdapter mAdapter;
    private List<JobApplication> jobApplicationList;
    private CoordinatorLayout coordinatorLayout;
    private SearchView searchView;
    private FloatingActionButton fab;
    private ModelData model = ModelData.getInstance();
    private Window window;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_applications);
        /*Toolbar toolbar = findViewById(R.id.toolbarC);
        setSupportActionBar(toolbar);

        //toolbar fancy stuff
        getSupportActionBar().setTitle(getString(R.string.my_application));*/

        String primaryDark = "#3791a4";
        String primary = "#1fa1bc";
        String background = "#b8141b";

        this.window = getWindow();
        window.setStatusBarColor(Color.parseColor(primaryDark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primaryDark));


        mRecyclerView = findViewById(R.id.recycler_cursosFld);
        jobApplicationList = new ArrayList<>();
        jobApplicationList= model.getListajobApplications();
        mAdapter = new ApplicationAdapter(jobApplicationList, this);
        coordinatorLayout = findViewById(R.id.coordinator_layoutC);

        // white background notification bar
        whiteNotificationBar(mRecyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        // go to update or add career
        fab = findViewById(R.id.addBtnC);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddUpdJobApplication();
            }
        });

        //delete swiping left and right
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        //should use database info


        // Receive the Carrera sent by AddUpdCarreraActivity
        checkIntentInformation();

        //refresh view
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (direction == ItemTouchHelper.START) {
            if (viewHolder instanceof ApplicationAdapter.MyViewHolder) {
                // get the removed item name to display it in snack bar
                String name = jobApplicationList.get(viewHolder.getAdapterPosition()).getFirstName();

                // save the index deleted
                final int deletedIndex = viewHolder.getAdapterPosition();
                // remove the item from recyclerView
                mAdapter.removeItem(viewHolder.getAdapterPosition());

                // showing snack bar with Undo option
                Snackbar snackbar = Snackbar.make(coordinatorLayout, name + " removed!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // undo is selected, restore the deleted item from adapter
                        mAdapter.restoreItem(deletedIndex);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        } else {
            //If is editing a row object
            JobApplication aux = mAdapter.getSwipedItem(viewHolder.getAdapterPosition());
            //send data to Edit Activity
            /*Intent intent = new Intent(this, AddUpdCursoActivity.class);
            intent.putExtra("editable", true);
            intent.putExtra("jobApplication", aux);*/
            mAdapter.notifyDataSetChanged(); //restart left swipe view
            //startActivity(intent);
        }
    }

    @Override
    public void onItemMove(int source, int target) {
        mAdapter.onItemMove(source, target);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds cursoList to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView   !IMPORTANT
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change, every type on input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        Intent a = new Intent(this, NavDreawerActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(JobApplication application) { //TODO get the select item of recycleView
        Toast.makeText(getApplicationContext(), "Selected: " + application.getFirstName() + ", " + application.getPosition(), Toast.LENGTH_LONG).show();
    }


    private void checkIntentInformation() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            JobApplication aux;
            aux = (JobApplication) getIntent().getSerializableExtra("addApplication");
            if (aux == null) {
                aux = (JobApplication) getIntent().getSerializableExtra("editApplication");
                if (aux != null) {
                    //found an item that can be updated
                    boolean founded = false;
                    for (JobApplication jobApplication : jobApplicationList) {
                        if (jobApplication.getFirstName().equals(aux.getFirstName()) &&
                                jobApplication.getFirstName().equals(aux.getPosition()) &&
                                jobApplication.getFirstName().equals(aux.getCountry())) {
                            /*jobApplication.setFirstName(aux.getFirstName());
                            jobApplication.setLastName(aux.getLastName());
                            jobApplication.setPosition(aux.getPosition());*/
                            jobApplicationList.remove(aux);
                            jobApplicationList.add(aux);
                            founded = true;
                            break;
                        }
                    }
                    //check if exist
                    if (founded) {
                        Toast.makeText(getApplicationContext(), aux.getFirstName() + " editado correctamente", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), aux.getFirstName() + " no encontrado", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                //found a new Curso Object
                jobApplicationList.add(aux);
                Toast.makeText(getApplicationContext(), aux.getFirstName() + " added succesfully", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void goToAddUpdJobApplication() {
        /*Intent intent = new Intent(this, AddUpdCursoActivity.class);
        intent.putExtra("editable", false);
        startActivity(intent);*/
    }
}
