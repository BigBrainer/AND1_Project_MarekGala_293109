package sk.martialhero.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;
import javax.inject.Named;

import sk.martialhero.component.FirebaseComponent;
import sk.martialhero.model.DataOrException;
import sk.martialhero.model.Group;
import sk.martialhero.model.User;

import static sk.martialhero.utils.Constants.GROUPS_REF;

public class CreateGroupRepository {

    private CollectionReference groupsRef;
    private FirebaseAuth firebaseAuth;

    @Inject
    public CreateGroupRepository(@Named(GROUPS_REF) CollectionReference groupsRef, FirebaseAuth firebaseAuth) {
        this.groupsRef = groupsRef;
        this.firebaseAuth = firebaseAuth;
    }



    public MutableLiveData<DataOrException<Group, Exception>> createGroupInFirestore(Group group) {
        MutableLiveData<DataOrException<Group, Exception>> dataOrExceptionMutableLiveData = new MutableLiveData<>();
        DocumentReference uidRef = groupsRef.document();
        String id = uidRef.getId();
        group.setUid(id);
        group.setGroup_admin(firebaseAuth.getCurrentUser().getUid());
        uidRef.set(group).addOnCompleteListener(groupCreationTask -> {
            DataOrException<Group, Exception> dataOrException = new DataOrException<>();
            if (groupCreationTask.isSuccessful()) {
                dataOrException.data = group;
            } else {
                dataOrException.exception = groupCreationTask.getException();
            }
            dataOrExceptionMutableLiveData.setValue(dataOrException);
        });
        return dataOrExceptionMutableLiveData;
    }
}
