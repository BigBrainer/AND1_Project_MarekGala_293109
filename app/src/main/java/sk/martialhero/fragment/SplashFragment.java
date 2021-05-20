package sk.martialhero.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.inject.Inject;


import sk.martialhero.component.DaggerViewModelComponent;
import sk.martialhero.component.ViewModelComponent;
import sk.martialhero.R;
import sk.martialhero.model.User;
import sk.martialhero.viewModel.AuthViewModel;


public class SplashFragment extends Fragment{
    //Field injection provided by Dagger 2
    //For more info go to RegisterFragment.class
    @Inject
    AuthViewModel authViewModel;
    private ViewModelComponent component;
    private static int SPLASH_TIME_OUT = 3000;
    private boolean handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        View root = inflater.inflate(R.layout.fragment_splash, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        component = DaggerViewModelComponent.create();
        component.inject(this);

        navigateBasedOnAuthState();
    }

    private void navigateBasedOnAuthState() {
        boolean isUserAuthenticated = authViewModel.checkIfUserIsAuthenticated();
        if (isUserAuthenticated) {
            String uid = authViewModel.getUid();
            getUserData(uid);
            NavHostFragment.findNavController(this).navigate(R.id.action_nav_splash_fragment_to_nav_join_group_fragment);
        } else {
            NavHostFragment.findNavController(this).navigate(R.id.action_nav_splash_fragment_to_nav_register_fragment);
        }
    }

    private void getUserData(String uid) {
        authViewModel.setUid(uid);
        authViewModel.userLiveData.observe(getViewLifecycleOwner(), dataOrException -> {
            if (dataOrException.data != null) {
                User user = dataOrException.data;
                NavHostFragment.findNavController(this).navigate(R.id.action_nav_splash_fragment_to_nav_join_group_fragment);
            }

            if (dataOrException.exception != null) {
                Log.w(this.getClass().getName(), getString(R.string.something_went_wrong), dataOrException.exception);
            }
        });
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
