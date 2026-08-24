package spacewalkerconsulting.engineering.spacewalkerhub.di

import spacewalkerconsulting.engineering.spacewalkerhub.data.repository.BookingRepository
import spacewalkerconsulting.engineering.spacewalkerhub.data.repository.PBUHCOnboardingRepo
import spacewalkerconsulting.engineering.spacewalkerhub.data.repository.ServiceRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        PBUHCOnboardingRepo(
            pbuhcOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ServiceRepository() }

    single{
        BookingRepository(
            bookingDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}