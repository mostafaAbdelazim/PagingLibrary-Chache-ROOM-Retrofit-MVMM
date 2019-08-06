/*
 * Copyright 2019  , MostafaAbdelazim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.isca.iscagroup.database

import android.content.Context
import androidx.paging.DataSource
import androidx.room.*
import com.isca.iscagroup.network.Photo


@Dao
interface PhotosDao {
    @Query("select * from photo")
    fun getPhotos(): DataSource.Factory<Int, Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<Photo>)

    @Query("delete from photo")
    fun deleteOldCache()
}

@Database(entities = [Photo::class], version = 1)
abstract class PhotosDatabase : RoomDatabase() {
    abstract val photoDao: PhotosDao
}

private lateinit var INSTANCE: PhotosDatabase

fun getDatabase(context: Context): PhotosDatabase {
    synchronized(PhotosDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    PhotosDatabase::class.java,
                    "photos").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}