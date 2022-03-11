package com.example.weatherapplication.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.weatherapplication.common.AppState
import com.example.weatherapplication.data.common.SharedPrefsContract
import dagger.Lazy
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideContextObservable(contextLazy: Lazy<Context>): Observable<Context> {
        return Observable.fromCallable(contextLazy::get)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceSingleObserver(contextObservable: Observable<Context>): Observable<SharedPreferences> {
        return Observable.create { subscribeSharedPrefs ->
            val t = contextObservable.subscribe { context ->
                subscribeSharedPrefs.onNext(
                    context.getSharedPreferences(
                        SharedPrefsContract.SHARED_PREFS_NAME,
                        Context.MODE_PRIVATE
                    )
                )
            }
        }
    }

    @Provides
    @Singleton
    fun provideAppState(): AppState {
        return AppState()
    }

    @Provides
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}