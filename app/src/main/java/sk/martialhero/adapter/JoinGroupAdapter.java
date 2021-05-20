package sk.martialhero.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import sk.martialhero.R;
import sk.martialhero.model.Group;

public class JoinGroupAdapter extends FirestoreRecyclerAdapter<Group, JoinGroupAdapter.NoteHolder> {

    public JoinGroupAdapter(@NonNull @NotNull FirestoreRecyclerOptions<Group> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NotNull NoteHolder holder, int position, @NonNull Group model) {
        holder.groupName.setText(model.group_name);
        holder.groupLocation.setText(model.group_location);
    }

    @NotNull
    @Override
    public NoteHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView groupName;
        TextView groupLocation;

        public NoteHolder(@NotNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name);
            groupLocation = itemView.findViewById(R.id.group_location);
        }
    }
}
