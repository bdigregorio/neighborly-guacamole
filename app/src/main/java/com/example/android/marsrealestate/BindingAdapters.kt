/*
 * Copyright 2018, The Android Open Source Project
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
 *
 */

package com.example.android.marsrealestate

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.realestate.response.MarsPropertiesResponse
import com.example.android.marsrealestate.overview.PhotoGridAdapter

@BindingAdapter("loadImageFrom")
fun loadImageFrom(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let { url ->
        val uri = url.toUri()
            .buildUpon()
            .scheme("https")
            .build()

        Glide.with(imageView.context)
            .load(uri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            ).into(imageView)
    }
}

@BindingAdapter("photoListBoundTo")
fun photoListBoundTo(recyclerView: RecyclerView, response: MarsPropertiesResponse) {
    when (response) {
        is MarsPropertiesResponse.Success -> {
            val adapter = recyclerView.adapter as PhotoGridAdapter
            adapter.submitList(response.marsProperties)
        }
        is MarsPropertiesResponse.Loading, is MarsPropertiesResponse.Error -> {}
    }
}

@BindingAdapter("imageSourceBoundTo")
fun imageSourceBoundTo(imageView: ImageView, response: MarsPropertiesResponse) {
    when (response) {
        is MarsPropertiesResponse.Loading -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_animation)
        }
        is MarsPropertiesResponse.Success -> {
            imageView.visibility = View.GONE
        }
        is MarsPropertiesResponse.Error -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_broken_image)
            Log.e("BindingAdapters::imageSourceBoundTo", "${response.exception.message}")
        }
    }
}

@BindingAdapter("formatPriceFromDouble")
fun formatPriceFromDouble(textView: TextView, price: Double) {
    textView.text = "$$price"
}
