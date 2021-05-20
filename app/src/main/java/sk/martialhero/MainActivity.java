package sk.martialhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Locale;

import javax.inject.Inject;


import sk.martialhero.component.FirebaseComponent;
import sk.martialhero.component.DaggerFirebaseComponent;


public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener
{
    @Inject FirebaseAuth firebaseAuth;
    private AppBarConfiguration appBarConfiguration;
    private FirebaseComponent component;
    private NavController navController;
    private Context context;

    private MenuItem menuSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        menuSignOut = findViewById(R.id.menu_sign_out);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_splash_fragment, R.id.nav_join_group_fragment).setOpenableLayout(drawer).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        component = DaggerFirebaseComponent.create();
        component.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addFirebaseAuthListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeFirebaseAuthListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // adds items to toolbar menu when present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action bar clicks are handled here
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_sign_out) {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            signOut();
            return true;
        }
        if(id == R.id.menu_switch_to_english)
        {
            setLocale(MainActivity.this, "en");
            invalidateOptionsMenu();
            return true;
        }
        if(id == R.id.menu_switch_to_slovak)
        {
            setLocale(MainActivity.this, "sk");
            invalidateOptionsMenu();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth auth)
    {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            signOut();
            navController.navigate(R.id.nav_register_fragment);
        }
        else
        {
            navController.navigate(R.id.nav_join_group_fragment);
        }
    }

    private MutableLiveData<Boolean> signOut() {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseAuth.signOut();
            mutableLiveData.setValue(true);
        }
        return mutableLiveData;
    }

    void addFirebaseAuthListener() {
        firebaseAuth.addAuthStateListener(this);
    }

    void removeFirebaseAuthListener() {
        firebaseAuth.removeAuthStateListener(this);
    }

    public  void setLocale(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }
}