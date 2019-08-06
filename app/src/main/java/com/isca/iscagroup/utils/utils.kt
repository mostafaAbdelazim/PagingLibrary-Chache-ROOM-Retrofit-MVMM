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

package com.isca.iscagroup.utils

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

const val BASE_URL = "https://api.flickr.com/services/"
const val SEARCH_METHOD = "flickr.photos.getRecent"
const val API_KEY = "26e248a131774a38de86823ec88bc4a5"
const val EXTRA_S = "license, date_upload, date_taken, owner_name, icon_server, original_format, last_update, geo, tags, machine_tags, o_dims, views, media, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o"
const val PER_PAGE = 20
const val FORMAT = "json"
const val NOJSONCALLBACK = "1"

fun isOnline(): Boolean {
    return try {
        val timeoutMs = 1500
        val sock = Socket()
        val sockaddr = InetSocketAddress("8.8.8.8", 53)

        sock.connect(sockaddr, timeoutMs)
        sock.close()

        true
    } catch (e: IOException) {
        false
    }

}