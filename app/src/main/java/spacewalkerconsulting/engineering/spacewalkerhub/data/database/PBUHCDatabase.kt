package spacewalkerconsulting.engineering.spacewalkerhub.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import spacewalkerconsulting.engineering.spacewalkerhub.data.dao.BookingDao
import spacewalkerconsulting.engineering.spacewalkerhub.data.database.converter.Converters
import spacewalkerconsulting.engineering.spacewalkerhub.data.entity.BookingEntity

@Database(
    entities = [BookingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PBUHCDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}

