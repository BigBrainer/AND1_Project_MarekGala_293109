package sk.martialhero.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import sk.martialhero.MyApp;
import sk.martialhero.R;
import sk.martialhero.adapter.JoinGroupAdapter;
import sk.martialhero.component.DaggerViewModelComponent;
import sk.martialhero.component.ViewModelComponent;
import sk.martialhero.viewModel.JoinGroupViewModel;

public class JoinGroupFragment extends Fragment {

    private ViewModelComponent viewModelComponent;
    @Inject JoinGroupViewModel joinGroupViewModel;
    private JoinGroupAdapter adapter;
    private FloatingActionButton joinGroupButton;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_join_group, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModelComponent = DaggerViewModelComponent.create();
        viewModelComponent.inject(this);

        //Injected view model handles adapter setup
        adapter = joinGroupViewModel.setUpRecyclerViewAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.join_group_recycler_view);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    //Hide toolbar with this code
  /*  @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
*/
}