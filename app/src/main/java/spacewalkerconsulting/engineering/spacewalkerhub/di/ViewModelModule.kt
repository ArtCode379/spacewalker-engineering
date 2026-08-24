package spacewalkerconsulting.engineering.spacewalkerhub.di

import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.BookingViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.CheckoutViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.PBUHCOnboardingVM
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.ServiceDetailsViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.ServiceViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.PBUHCSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        PBUHCSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        PBUHCOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ServiceViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        ServiceDetailsViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        BookingViewModel(
            bookingRepository = get(),
            serviceRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            bookingRepository = get(),
        )
    }
}