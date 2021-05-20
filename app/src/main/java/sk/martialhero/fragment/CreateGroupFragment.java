package sk.martialhero.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import sk.martialhero.R;
import sk.martialhero.component.DaggerViewModelComponent;
import sk.martialhero.component.ViewModelComponent;
import sk.martialhero.model.DataOrException;
import sk.martialhero.model.Group;
import sk.martialhero.viewModel.CreateGroupViewModel;
import sk.martialhero.viewModel.JoinGroupViewModel;


public class CreateGroupFragment extends Fragment {

    private ViewModelComponent viewModelComponent;
    @Inject
    CreateGroupViewModel createGroupViewModel;

    private Button createGroupButton;
    private Group groupToSend;
    private TextView tapToChangeTextView;
    private TextInputLayout nameLayout;
    private TextInputLayout locationLayout;
    private TextInputLayout descriptionLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_group, container, false);
        return root;


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModelComponent = DaggerViewModelComponent.create();
        viewModelComponent.inject(this);

        nameLayout = view.findViewById(R.id.text_layout_name);
        locationLayout = view.findViewById(R.id.text_layout_location);
        descriptionLayout = view.findViewById(R.id.text_layout_description);


        createGroupButton = view.findViewById(R.id.create_group_button);
        tapToChangeTextView = view.findViewById(R.id.change_image_text_view);

        tapToChangeTextView.setTextColor(getResources().getColor(R.color.blackTextColor));
        createGroupButton.setOnClickListener(listener);



    }

    private boolean validateName () {
        if (groupToSend.group_name.isEmpty()) {
            nameLayout.setError(getResources().getText(R.string.name_error));
            return false;
        }
        else {
            nameLayout.setError(null);
            return true;
        }
    }

    private boolean validateLocation () {
        if (groupToSend.group_location.isEmpty()) {
            locationLayout.setError(getResources().getText(R.string.location_error));
            return false;
        }
        else {
            locationLayout.setError(null);
            return true;
        }
    }

    private boolean validateDescripion () {
        if (groupToSend.group_description.isEmpty() ) {
            descriptionLayout.setError(getResources().getText(R.string.description_error));
            return false;
        }
        else {
            descriptionLayout.setError(null);
            return true;
        }
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create_group_button:

                    String name = nameLayout.getEditText().getText().toString();
                    String location = locationLayout.getEditText().getText().toString();
                    String description = descriptionLayout.getEditText().getText().toString();
                    groupToSend = new Group(name, location, description);

                    if (!validateName() || !validateLocation() || !validateDescripion()) {
                        return;
                    }

                    createGroupViewModel.createGroupInFirestore(groupToSend);
                    createGroupViewModel.groupLiveData.observe(getViewLifecycleOwner(), dataOrException -> {
                        if (dataOrException.data != null) {
                            NavHostFragment.findNavController(CreateGroupFragment.this).navigate(R.id.action_nav_create_group_fragment_to_nav_join_group_fragment);
                        }

                        if (dataOrException.exception != null) {
                            Log.w(this.getClass().getName(), dataOrException.exception.getMessage());
                            Toast.makeText(getActivity(), "Could not create new user!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };
}