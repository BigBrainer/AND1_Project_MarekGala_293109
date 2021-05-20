package sk.martialhero.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import sk.martialhero.R;
import sk.martialhero.OnAuthStateChangeListener;
import sk.martialhero.component.FirebaseComponent;
import sk.martialhero.component.DaggerFirebaseComponent;
import sk.martialhero.component.DaggerViewModelComponent;
import sk.martialhero.component.ViewModelComponent;
import sk.martialhero.viewModel.AuthViewModel;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment implements OnAuthStateChangeListener {

    //Field injection provided by Dagger 2. We are able to retrieve all objects annotated with
    //@Inject by calling .inject() method on the component
    @Inject AuthViewModel authViewModel;
    //Inject FirebaseAuth instance
    @Inject FirebaseAuth firebaseAuth;


    //The component which builds our dependency graph
    //It is instantiated in the overridden onViewCreated method
    private ViewModelComponent viewModelComponent;
    private FirebaseComponent firebaseComponent;

    private MaterialButton registerButton;

    private final static int RC_SIGN_IN = 1;


    @Override
    public void onAuthStateChanged(boolean isUserLoggedOut) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerButton = view.findViewById(R.id.create_group_button);
        //set onclick listeners to buttons
        registerButton.setOnClickListener(listener);


        viewModelComponent = DaggerViewModelComponent.create();
        viewModelComponent.inject(this);

        firebaseComponent = DaggerFirebaseComponent.create();
        firebaseComponent.inject(this);
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create_group_button:
                    signIn();
                    break;
            }
        }
    };

    public void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.LoginTheme)
                .setAvailableProviders(providers)
                .build();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you want to pass your result to the activity.

        switch (requestCode) {
            case RC_SIGN_IN:

                if (requestCode == RC_SIGN_IN) {
                    IdpResponse response = IdpResponse.fromResultIntent(data);
                    if (resultCode == RESULT_OK) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        user.getIdToken(false);
                        if (response.isNewUser()) {
                            authViewModel.createUser(user);
                            authViewModel.userLiveData.observe(this, dataOrException -> {
                                if (dataOrException.data != null) {
                                    NavHostFragment.findNavController(this).navigate(R.id.action_nav_register_fragment_to_nav_join_group_fragment);
                                }

                                if (dataOrException.exception != null) {
                                    Log.w(this.getClass().getName(), dataOrException.exception.getMessage());
                                    Toast.makeText(getActivity(), "Could not create new user!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        // ...
                    } else {
                        Toast.makeText(getActivity(), "Sign in cancelled by user", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}