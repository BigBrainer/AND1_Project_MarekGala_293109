package sk.martialhero.component;

import javax.inject.Singleton;

import dagger.Component;
import sk.martialhero.fragment.CreateGroupFragment;
import sk.martialhero.fragment.JoinGroupFragment;
import sk.martialhero.fragment.RegisterFragment;
import sk.martialhero.fragment.SplashFragment;
import sk.martialhero.module.FirebaseModule;
import sk.martialhero.module.ContextModule;

@Singleton
@Component(modules = {FirebaseModule.class, ContextModule.class})
public interface ViewModelComponent {

    void inject(SplashFragment splashFragment);

    void inject (RegisterFragment registerFragment);

    void inject (JoinGroupFragment joinGroupFragment);

    void inject(CreateGroupFragment createGroupFragment);

}
