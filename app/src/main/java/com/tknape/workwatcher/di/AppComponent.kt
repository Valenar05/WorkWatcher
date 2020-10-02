package com.tknape.workwatcher.di

import android.app.Application
import com.tknape.workwatcher.BaseApplication
import com.tknape.workwatcher.Clock.Clock
import com.tknape.workwatcher.ClockFragment
import com.tknape.workwatcher.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
    fun inject(mainActivity: MainActivity)

    fun inject(clockFragment: ClockFragment)

}