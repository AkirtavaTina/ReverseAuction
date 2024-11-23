package com.android.example.reverseauction

import androidx.lifecycle.ViewModel
import com.core.common.local.ActivityUseCase
import com.core.common.local.JwtTokenDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(activityUseCase: ActivityUseCase): ViewModel() {

}