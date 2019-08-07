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

package com.isca.iscagroup.ui.fragment.preview


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.isca.iscagroup.databinding.FragmentPreviewBinding
import com.isca.iscagroup.viewmodel.preview.PreviewViewModel
import com.isca.iscagroup.viewmodel.preview.PreviewViewModelFactory
import kotlin.math.abs


class PreviewFragment : Fragment() {
    private var downY: Float = 0.toFloat()
    private var upY: Float = 0.toFloat()

    private val args by navArgs<PreviewFragmentArgs>()
    private val previewViewModel by viewModels<PreviewViewModel> {
        PreviewViewModelFactory(args.photo)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentPreviewBinding>(inflater, com.isca.iscagroup.R.layout.fragment_preview, container, false).apply {
            lifecycleOwner = this@PreviewFragment
            viewModel = previewViewModel
        }
        (activity as AppCompatActivity).supportActionBar!!.hide()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.close.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.fullScreenImage.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downY = event.y
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        upY = event.y
                        val deltaY = downY - upY
                        if (abs(deltaY) > resources.displayMetrics.heightPixels / 6) {
                            if (deltaY > 0) {
                                view?.findNavController()?.popBackStack()
                                return true
                            }
                        }
                    }
                }
                return false
            }
        })
        return binding.root
    }
}