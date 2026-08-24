package spacewalkerconsulting.engineering.spacewalkerhub.di

import spacewalkerconsulting.engineering.spacewalkerhub.data.datastore.PBUHCOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { PBUHCOnboardingPrefs(androidContext()) }
}