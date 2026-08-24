package spacewalkerconsulting.engineering.spacewalkerhub.data.repository

import spacewalkerconsulting.engineering.spacewalkerhub.data.datastore.PBUHCOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PBUHCOnboardingRepo(
    private val pbuhcOnboardingStoreManager: PBUHCOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return pbuhcOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            pbuhcOnboardingStoreManager.setOnboardedState(state)
        }
    }
}