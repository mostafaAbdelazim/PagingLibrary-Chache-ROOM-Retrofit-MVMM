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

package com.isca.iscagroup.ui.fragment.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.isca.iscagroup.R
import com.isca.iscagroup.adapter.MainAdapter
import com.isca.iscagroup.database.getDatabase
import com.isca.iscagroup.databinding.FragmentMainBinding
import com.isca.iscagroup.repositroy.PhotoRepository
import com.isca.iscagroup.viewmodel.main.MainViewModel
import com.isca.iscagroup.viewmodel.main.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.popup_dialog.*

class MainFragment : Fragment() {
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory(PhotoRepository(getDatabase(context!!)))
    }

    private var adapter: MainAdapter? = null
    private lateinit var dialog: Dialog
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.pagedList.observe(viewLifecycleOwner, Observer {
            it?.apply {
                adapter?.submitList(it)
                refreshLayout.isRefreshing = it.isEmpty()
            }
        })
        mainViewModel.offline.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    refreshLayout.isRefreshing = false
                    mainViewModel.setNullOffline()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false).apply {
            lifecycleOwner = this@MainFragment
            viewModel = mainViewModel
        }
        (activity as AppCompatActivity).supportActionBar!!.show()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setupDialog()
        adapter = MainAdapter(MainAdapter.PhotoClick {
            view?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToPreviewFragment(it))
        }, MainAdapter.PhotoLongClick {
            dialog.dialogTitle.text = it.title
            dialog.values.text = "${it.id}\n\n${it.ownername}\n\n${it.views}\n\n${it.datetaken}\n\n${it.height_l}\n\n${it.width_l}\n\n${it.originalformat}\n\n${it.tags}"
            dialog.show()
        })
        binding.mainRecyclerView.adapter = adapter
        binding.refreshLayout.setOnRefreshListener {
            mainViewModel.refresh()
        }
        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        return binding.root
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun setupDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.popup_dialog)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}