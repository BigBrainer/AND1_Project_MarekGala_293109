package sk.martialhero.viewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;
import javax.inject.Named;

import sk.martialhero.adapter.JoinGroupAdapter;
import sk.martialhero.component.FirebaseComponent;
import sk.martialhero.model.Group;

import static sk.martialhero.utils.Constants.GROUPS_REF;
import static sk.martialhero.utils.Constants.GROUP_COLLECTION;
import static sk.martialhero.utils.Constants.KEY_GROUP_NAME;

public class JoinGroupViewModel {

    @Inject @Named(GROUPS_REF)
    CollectionReference groupRef;
    @Inject FirebaseFirestore db;

    @Inject
    public JoinGroupViewModel() {
    }

    public JoinGroupAdapter setUpRecyclerViewAdapter() {
        groupRef = db.collection(GROUP_COLLECTION);
        Query query = groupRef.orderBy(KEY_GROUP_NAME, Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Group> options = new FirestoreRecyclerOptions.Builder<Group>()
                .setQuery(query, Group.class)
                .build();

        return new JoinGroupAdapter(options);

    }
}
