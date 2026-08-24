package spacewalkerconsulting.engineering.spacewalkerhub.di

import androidx.room.Room
import spacewalkerconsulting.engineering.spacewalkerhub.data.database.PBUHCDatabase
import org.koin.dsl.module

private const val DB_NAME = "pbuhc_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = PBUHCDatabase::class.java,
        name = DB_NAME
        ).build()
    }

    single { get<PBUHCDatabase>().bookingDao()}

}