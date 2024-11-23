package com.core.feature_api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureApi {
    fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder)
}

interface HomeApi {
    fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder)
}

interface ProfileApi {
    fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder)
}