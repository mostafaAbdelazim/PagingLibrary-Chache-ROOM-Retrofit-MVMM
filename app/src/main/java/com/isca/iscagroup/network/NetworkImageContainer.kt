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
        val accuracy: Int = 0,
        val context: Int = 0,
        val datetaken: String = "",
        val datetakengranularity: String = "",
        val datetakenunknown: String = "",
        val dateupload: String = "",
        val farm: Int = 0,
        val height_c: String = "",
        val height_l: String = "",
        val height_m: String = "",
        val height_n: String = "",
        val height_o: String = "",
        val height_q: String = "",
        val height_s: String = "",
        val height_sq: Int = 0,
        val height_t: String = "",
        val height_z: String = "",
        val iconfarm: Int = 0,
        val iconserver: String = "",
        @PrimaryKey
        val id: String = "",
        val isfamily: Int = 0,
        val isfriend: Int = 0,
        val ispublic: Int = 0,
        val lastupdate: String = "",
        val license: String = "",
        val machine_tags: String = "",
        val media: String = "",
        val media_status: String = "",
        val o_height: String = "",
        val o_width: String = "",
        val originalformat: String = "",
        val originalsecret: String = "",
        val owner: String = "",
        val ownername: String = "",
        val secret: String = "",
        val server: String = "",
        val tags: String = "",
        var title: String = "No title provided",
        val url_c: String = "",
        val url_l: String = "",
        val url_m: String = "",
        val url_n: String = "",
        val url_o: String = "",
        val url_q: String = "",
        val url_s: String = "",
        val url_sq: String = "",
        val url_t: String = "",
        val url_z: String = "",
        val views: String = "",
        val width_c: Int = 0,
        val width_l: String = "",
        val width_m: String = "",
        val width_n: Int = 0,
        val width_o: String = "",
        val width_q: String = "",
        val width_s: String = "",
        val width_sq: Int = 0,
        val width_t: String = "",
        val width_z: String = ""
) : Parcelable
