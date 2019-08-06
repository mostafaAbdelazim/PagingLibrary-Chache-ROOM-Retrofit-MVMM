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

package com.isca.iscagroup.repositroy

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.isca.iscagroup.database.PhotosDatabase
import com.isca.iscagroup.network.Photo

class PhotoRepository( val database: PhotosDatabase) {

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }

    val photos: LiveData<PagedList<Photo>> = refreshPhotos()

    private fun refreshPhotos(): LiveData<PagedList<Photo>> {
        val dataSource = database.photoDao.getPhotos()
        val photoBoundaryCallback = PhotoBoundaryCallback(database)
        val config = PagedList.Config.Builder().setPageSize(DATABASE_PAGE_SIZE)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(30).setPrefetchDistance(5).build()
        return LivePagedListBuilder(dataSource, config).setBoundaryCallback(photoBoundaryCallback).build()
    }
}
