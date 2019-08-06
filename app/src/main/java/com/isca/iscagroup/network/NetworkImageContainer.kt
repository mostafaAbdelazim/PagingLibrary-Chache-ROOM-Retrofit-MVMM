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

package com.isca.iscagroup.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@JsonClass(generateAdapter = true)
data class NetworkImageContainer(
        val photos: Photos,
        val stat: String
)

@JsonClass(generateAdapter = true)
data class Photos(
        val page: Int,
        val pages: Int,
        val perpage: Int,
        val photo: List<Photo>,
        val total: Int
)

@Parcelize
@Entity
@JsonClass(generateAdapter = true)
data class Photo(
        val url_l: String = "",
        var url_q: String = "",
        val farm: Int = 0,
        val height_l: String = "",
        val height_q: String = "",
        val width_q: String = "",
        @PrimaryKey
        val id: String = "",
        val isfamily: Int = 0,
        val isfriend: Int = 0,
        val ispublic: Int = 0,
        val owner: String = "",
        val secret: String = "",
        val server: String = "",
        var title: String = "No title provided",
        val width_l: String = ""
) : Parcelable