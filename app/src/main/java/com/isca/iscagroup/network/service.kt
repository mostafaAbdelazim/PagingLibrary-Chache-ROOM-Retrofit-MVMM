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

import com.isca.iscagroup.BuildConfig
import com.isca.iscagroup.utils.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MyInterface {
    @GET("rest")
    fun searchAsync(@Query("method") method: String, @Query("api_key") api_key: String,
                    @Query("extras") size: String, @Query("format") format: String,
                    @Query("nojsoncallback") noJsonCallBack: String,
                    @Query("per_page") pageLimit: String,
                    @Query("page") pageCount: String): Deferred<NetworkImageContainer>
}

val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )
).readTimeout(500, TimeUnit.SECONDS)
        .writeTimeout(500, TimeUnit.SECONDS)
        .connectTimeout(500, TimeUnit.SECONDS).build()

val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

object Network {
    val retrofitService: MyInterface by lazy {
        retrofit.create(MyInterface::class.java)
    }
}