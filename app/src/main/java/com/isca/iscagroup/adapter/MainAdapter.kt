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

package com.isca.iscagroup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.isca.iscagroup.R
import com.isca.iscagroup.databinding.RecyclerCellBinding
import com.isca.iscagroup.network.Photo

class MainAdapter(private val onClickListener: PhotoClick, private val onLongClickListener: PhotoLongClick)
    : PagedListAdapter<Photo, MainAdapter.MainViewhodler>(object : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewhodler {

        val withDataBinding: RecyclerCellBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                MainViewhodler.LAYOUT,
                parent,
                false)
        return MainViewhodler(withDataBinding)

    }

    override fun onBindViewHolder(holder: MainViewhodler, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.viewDataBinding.also {
                if (photo.title.isEmpty()) photo.title = "Untitled"
                if (photo.tags.isEmpty()) photo.tags = "No tags"
                it.photo = photo
                it.root.setOnClickListener { onClickListener.onClick(photo) }
                it.root.setOnLongClickListener {
                    onLongClickListener.onLongClick(photo)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    class MainViewhodler(val viewDataBinding: RecyclerCellBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.recycler_cell
        }
    }

    class PhotoClick(val onClickListener: (photo: Photo) -> Unit) {
        fun onClick(photo: Photo) {
            onClickListener(photo)
        }
    }

    class PhotoLongClick(val onLongClickListener: (photo: Photo) -> Unit) {
        fun onLongClick(photo: Photo) {
            onLongClickListener(photo)
        }
    }
}