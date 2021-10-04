package com.example.parliamentproject.network

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.parliamentproject.R
import com.example.parliamentproject.data.data_classes.Member
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/** This script uses Retrofit to fetch wanted data from the wanted URL. The fetched data is later parsed using Moshi. */

// The destination URL where the data will be fetched.
private const val URL = " https://users.metropolia.fi/~peterh/"

// The destination URL where the member images are fetched
private const val IMG_URL = "https://avoindata.eduskunta.fi/"


// Creating a instance of Moshi.
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Creating a retrofit object which fetches data as a JSON format from the wanted URL.
// Setting the moshi object as the converter which will parse the JSON data.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(URL)
    .build()


interface MemberApiService {

    // uses the HTTP GET method to retrieve data from the given endpoint.
    @GET("mps.json")
    suspend fun getProperties(): List<Member> // Parsing the JSON data to a list of MemberProperty objects.
}

// A singleton for initializing the retrofit service.
object MembersApi {
    // Using by lazy to initialize the service only when it is needed.
    val retrofitService : MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java)
    }

    /** Fetches the member's image utilizing Glide an setting it in the chosen ImageView.
     * By default, Glide caches the images. */
    fun setMemberImage(endpoint: String, imageView: ImageView, fragment: Fragment) {
        GlideApp.with(fragment)
            .load("$IMG_URL$endpoint")
            .fitCenter()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_baseline_search_24)
            .into(imageView)
    }
}