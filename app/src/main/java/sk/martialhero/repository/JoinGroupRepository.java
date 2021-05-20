package sk.martialhero.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import sk.martialhero.OnAuthStateChangeListener;

@Singleton
public class JoinGroupRepository implements FirebaseAuth.AuthStateListener {
    private OnAuthStateChangeListener onAuthStateChangeListener;
    @Inject FirebaseAuth firebaseAuth;

    @Inject
    JoinGroupRepository(FirebaseAuth firebaseAuth)
    {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null)
        {
            onAuthStateChangeListener.onAuthStateChanged(true);
        }
    }

    void addFirebaseAuthListener()
    {
        firebaseAuth.addAuthStateListener(this);
    }

    void removeFirebaseAuthListener()
    {
        firebaseAuth.removeAuthStateListener(this);
    }

    MutableLiveData<Boolean> signOut() {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseSignOut();
            mutableLiveData.setValue(true);
        }
        return mutableLiveData;
    }

    private void firebaseSignOut() {
        firebaseAuth.signOut();
    }
}
