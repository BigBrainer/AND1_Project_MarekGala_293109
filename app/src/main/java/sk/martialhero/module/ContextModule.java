package sk.martialhero.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sk.martialhero.MyApp;

@Module
public class ContextModule {
    @Provides
    public Context appContext()
    {
        return MyApp.getContext();
    }
}
