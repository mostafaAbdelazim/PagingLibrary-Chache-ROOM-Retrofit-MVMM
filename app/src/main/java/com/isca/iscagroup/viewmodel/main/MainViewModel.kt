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

package com.isca.iscagroup.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.isca.iscagroup.network.Photo
import com.isca.iscagroup.repositroy.PhotoRepository
import com.isca.iscagroup.utils.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private var photoRepository: PhotoRepository) : ViewModel() {
    private val job = Job()
    private val coroutine = CoroutineScope(job + Dispatchers.IO)
    val pagedList: LiveData<PagedList<Photo>> = photoRepository.photos

    init {
        coroutine.launch {
            if (isOnline()) {
                photoRepository.database.photoDao.deleteOldCache()
                Log.e("ISCA", "Old cache deleted2")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}