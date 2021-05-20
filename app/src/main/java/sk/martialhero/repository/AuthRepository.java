package sk.martialhero.repository;


import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import sk.martialhero.MainActivity;
import sk.martialhero.MyApp;
import sk.martialhero.model.DataOrException;
import sk.martialhero.model.User;

import static sk.martialhero.utils.Constants.KEY_EMAIL;
import static sk.martialhero.utils.Constants.KEY_FIRST_NAME;
import static sk.martialhero.utils.Constants.KEY_LAST_NAME;
import static sk.martialhero.utils.Constants.KEY_UID;
import static sk.martialhero.utils.Constants.USERS_REF;

@Singleton
public class AuthRepository {

    private CollectionReference usersRef;
    private FirebaseAuth firebaseAuth;

    @Inject
    AuthRepository(FirebaseAuth auth, @Named(USERS_REF) CollectionReference usersRef) {
        this.firebaseAuth = auth;
        this.usersRef = usersRef;
    }

    public boolean checkIfUserIsAuthenticatedInFirebase() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public String getFirebaseUserUid() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser.getUid();
    }

    public MutableLiveData<DataOrException<User, Exception>> getUserDataFromFirestore(String uid) {
        MutableLiveData<DataOrException<User, Exception>> userMutableLiveData = new MutableLiveData<>();
        usersRef.document(uid).get().addOnCompleteListener(userTask -> {
            DataOrException<User, Exception> dataOrException = new DataOrException<>();
            if (userTask.isSuccessful()) {
                DocumentSnapshot userDoc = userTask.getResult();
                if (userDoc.exists()) {
                    dataOrException.data = userDoc.toObject(User.class);
                }
            } else {
                dataOrException.exception = userTask.getException();
            }
            userMutableLiveData.setValue(dataOrException);
        });
        return userMutableLiveData;
    }


    public MutableLiveData<DataOrException<User, Exception>> createUserInFirestore(FirebaseUser authenticatedUser) {
        User userToAdd = getUser(authenticatedUser);
        MutableLiveData<DataOrException<User, Exception>> dataOrExceptionMutableLiveData = new MutableLiveData<>();
        DocumentReference uidRef = usersRef.document(authenticatedUser.getUid());
        uidRef.set(userToAdd).addOnCompleteListener(userCreationTask -> {
            DataOrException<User, Exception> dataOrException = new DataOrException<>();
            if (userCreationTask.isSuccessful()) {
                dataOrException.data = userToAdd;
            } else {
                dataOrException.exception = userCreationTask.getException();
            }
            dataOrExceptionMutableLiveData.setValue(dataOrException);
        });
        return dataOrExceptionMutableLiveData;
    }


    private User getUser(FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        ArrayList<String> firstAndLastName = formatName(name);
        String firstName = firstAndLastName.get(0);
        String lastName = firstAndLastName.get(1);
        String email = firebaseUser.getEmail();
        return new User(uid, firstName, lastName, email);
    }

    private void saveUser() {
        User currentUser = getUser(firebaseAuth.getCurrentUser());
        Map<String, Object> user = new HashMap<>();
        user.put(KEY_UID, firebaseAuth.getCurrentUser().getUid());
        user.put(KEY_FIRST_NAME, currentUser.getFirstName());
        user.put(KEY_LAST_NAME, currentUser.getLastName());
        user.put(KEY_EMAIL, currentUser.getEmail());

    }

    private ArrayList<String> formatName(String nameToFormat) {
        String lastName = "";
        String firstName = "";
        if (nameToFormat.split("\\w+").length > 1) {

            lastName = nameToFormat.substring(nameToFormat.lastIndexOf(" ") + 1);
            firstName = nameToFormat.substring(0, nameToFormat.lastIndexOf(' '));
        } else {
            firstName = nameToFormat;
        }
        ArrayList<String> nameList = new ArrayList<String>();
        nameList.add(firstName);
        nameList.add(lastName);
        return nameList;
    }
}
