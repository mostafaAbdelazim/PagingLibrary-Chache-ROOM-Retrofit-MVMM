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

package com.isca.iscagroup.viewmodel.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isca.iscagroup.network.Photo
import com.isca.iscagroup.utils.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PreviewViewModel(val photo: Photo) : ViewModel() {
    private val job = Job()
    private val coroutines = CoroutineScope(job + Dispatchers.IO)
    private val _clickedPhoto = MutableLiveData<Photo>().apply { value = photo }
    val clickedPhoto: LiveData<Photo>
        get() = _clickedPhoto
    private val _highestURL = MutableLiveData<String>()
    val highestURL: LiveData<String>
        get() = _highestURL

    init {
        coroutines.launch {
            if (isOnline()) {
                val listOfUrls = listOf(photo.url_o, photo.url_l, photo.url_z, photo.url_n, photo.url_m)
                for (url in listOfUrls) {
                    if (url.isNotEmpty()) {
                        _highestURL.postValue(url)
                        break
                    }
                }
            } else {
                _highestURL.postValue(photo.url_m)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}