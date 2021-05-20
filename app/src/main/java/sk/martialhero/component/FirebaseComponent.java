package sk.martialhero.component;

import javax.inject.Singleton;

import dagger.Component;
import sk.martialhero.MainActivity;
import sk.martialhero.fragment.JoinGroupFragment;
import sk.martialhero.fragment.RegisterFragment;
import sk.martialhero.module.FirebaseModule;
import sk.martialhero.repository.AuthRepository;

@Singleton
@Component(modules = FirebaseModule.class)
public interface FirebaseComponent {
    void inject(MainActivity mainActivity);
    void inject(RegisterFragment registerFragment);
    void inject(AuthRepository authRepository);
}
