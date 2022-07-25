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

package com.example.android.marsrealestate.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentOverviewBinding
import com.example.android.marsrealestate.network.realestate.PropertyTypeApiFilter
import com.example.android.marsrealestate.network.realestate.model.MarsProperty

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels()
    private val binding by lazy {
        FragmentOverviewBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root.also {
        initializeBinding()
        setHasOptionsMenu(true)
        subscribeToNavigationEvent()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_rent_menu -> viewModel.updateFilter(PropertyTypeApiFilter.RENT)
            R.id.show_buy_menu -> viewModel.updateFilter(PropertyTypeApiFilter.BUY)
            R.id.show_all_menu -> viewModel.updateFilter(PropertyTypeApiFilter.ALL)
            else -> return false
        }
        return true
    }

    private fun initializeBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter(
            PhotoGridAdapter.PhotoClickListener { viewModel.displayPropertyDetails(it) }
        )
    }

    private fun subscribeToNavigationEvent() {
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner) { marsProperty ->
            if (marsProperty != null) {
                findNavController().navigate(OverviewFragmentDirections.actionShowDetail(marsProperty))
                viewModel.displayPropertyDetailsComplete()
            }
        }
    }
}
