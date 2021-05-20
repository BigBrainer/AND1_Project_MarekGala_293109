package sk.martialhero.viewModel;

import androidx.lifecycle.LiveData;


import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import sk.martialhero.model.DataOrException;
import sk.martialhero.model.User;
import sk.martialhero.repository.AuthRepository;

public class AuthViewModel {
    AuthRepository authRepository;
    public LiveData<DataOrException<User, Exception>> userLiveData;

    @Inject
    AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean checkIfUserIsAuthenticated() {
        return authRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    public String getUid() {
        return authRepository.getFirebaseUserUid();
    }

    public void setUid(String uid) {
        userLiveData = authRepository.getUserDataFromFirestore(uid);
    }

    public void createUser(FirebaseUser authenticatedUser) {
        userLiveData = authRepository.createUserInFirestore(authenticatedUser);
    }

}
