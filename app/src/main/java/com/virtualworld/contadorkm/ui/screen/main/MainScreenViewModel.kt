package com.virtualworld.contadorkm.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
  //  userRepository: UserRepository
) : ViewModel() {
   // val doesUserExist =


     //   userRepository.doesUserExist
     //   .stateIn(
      //      viewModelScope,
     //       SharingStarted.Lazily,
     //       null
     //   )
}