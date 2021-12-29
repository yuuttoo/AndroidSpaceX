/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.uspace.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.android.uspace.R
import com.raywenderlich.android.uspace.databinding.FragmentCrewBinding
import com.raywenderlich.android.uspace.repository.SpaceResult
import com.raywenderlich.android.uspace.ui.adapters.CrewAdapter
import com.raywenderlich.android.uspace.ui.viewmodels.CrewViewModel
import com.raywenderlich.android.uspace.utils.showSnackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CrewFragment : Fragment() {

  companion object {
    fun createInstance() = CrewFragment()
  }

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @Inject
  lateinit var adapter: CrewAdapter
  private val viewModel: CrewViewModel by viewModels { viewModelFactory }
  private var binding: FragmentCrewBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidSupportInjection.inject(this)
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCrewBinding.inflate(inflater)
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding?.viewModel = viewModel

    setupList()

    viewModel.result.observe(viewLifecycleOwner) { result ->
      handleResult(result)
    }

    viewModel.crewAgency.observe(viewLifecycleOwner) {
      adapter.addItems(viewModel.getFilteredCrew())
    }
    viewModel.getCrew()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    binding = null
  }

  private fun setupList() {
    binding?.apply {
      crewList.layoutManager = LinearLayoutManager(requireContext())
      crewList.adapter = adapter
      crewList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }
  }

  private fun handleResult(result: SpaceResult) {
    when (result) {
      SpaceResult.Error -> binding?.root.showSnackbar(R.string.error_loading_data, R.string.try_again) {
        viewModel.getCrew()
      }
      is SpaceResult.CrewResult -> {
        adapter.addItems(result.crew)
      }
    }
  }
}