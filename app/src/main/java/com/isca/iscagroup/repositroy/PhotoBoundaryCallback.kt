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

import androidx.paging.PagedList
import com.isca.iscagroup.database.PhotosDatabase
import com.isca.iscagroup.network.Network
import com.isca.iscagroup.network.Photo
import com.isca.iscagroup.utils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoBoundaryCallback(private val database: PhotosDatabase) : PagedList.BoundaryCallback<Photo>() {
    private var isRequestInProgress = false

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
    private var lastRequestedPage = 1
    private var pageLimit = 2
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        lastRequestedPage = 1
        requestAndSavePhotos()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Photo) {
        super.onItemAtEndLoaded(itemAtEnd)
        requestAndSavePhotos()
    }


    private fun requestAndSavePhotos() {
        GlobalScope.launch {
            if (isRequestInProgress) return@launch
            isRequestInProgress = true
            try {
                if (isOnline()) {
                    if (lastRequestedPage < pageLimit) {
                        val photos = Network.retrofitService.searchAsync(SEARCH_METHOD, API_KEY,
                                EXTRA_S, FORMAT, NOJSONCALLBACK, NETWORK_PAGE_SIZE.toString(),
                                lastRequestedPage.toString()).await()

                        val result = photos.photos
                        lastRequestedPage = result.page + 1
                        pageLimit = result.pages
                        database.photoDao.insertAll(result.photo)
                        isRequestInProgress = false
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                isRequestInProgress = false
            }

        }
    }
}