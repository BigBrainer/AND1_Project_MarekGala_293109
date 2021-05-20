package sk.martialhero.module;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static sk.martialhero.utils.Constants.GROUPS_REF;
import static sk.martialhero.utils.Constants.GROUP_COLLECTION;
import static sk.martialhero.utils.Constants.USERS_REF;
import static sk.martialhero.utils.Constants.USER_COLLECTION;

@Module
public class FirebaseModule {
    @Singleton
    @Provides
    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    //Collection references
    @Singleton
    @Provides
    @Named(USERS_REF)
    public static CollectionReference provideUserCollectionReference(FirebaseFirestore rootRef) {
        return rootRef.collection(USER_COLLECTION);
    }

    @Singleton
    @Provides
    @Named(GROUPS_REF)
    public static CollectionReference provideGroupCollectionReference(FirebaseFirestore rootRef) {
        return rootRef.collection(GROUP_COLLECTION);
    }

    @Provides
    public FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }
}
