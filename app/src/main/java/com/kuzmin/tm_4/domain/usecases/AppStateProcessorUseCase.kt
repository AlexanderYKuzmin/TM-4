package com.kuzmin.tm_4.domain.usecases

import android.util.Log
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZATION_CANCELLED
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZATION_COMPLETED
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZATION_FAILED
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZATION_STARTED
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZED
import com.kuzmin.tm_4.common.util.CommonConstants.FRAGMENT_ON_FINISH
import com.kuzmin.tm_4.common.util.CommonConstants.FRAGMENT_ON_START
import com.kuzmin.tm_4.common.util.CommonConstants.IS_FILTER_SET
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_LOCAL
import com.kuzmin.tm_4.domain.model.ToolbarState
import com.kuzmin.tm_4.domain.model.sealed.AppState
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction.FilterAction
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction.HomeAction
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction.LoginAction
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction.SiteListAction
import javax.inject.Inject

class AppStateProcessorUseCase @Inject constructor(

) {
    operator fun invoke(
        toolbarState: ToolbarState,
        fragmentAction: FragmentAction
    ): Pair<AppState, ToolbarState> {

        return when (fragmentAction) {
            is HomeAction -> {
                handleHomeAction(toolbarState)
            }

            is LoginAction -> {
                handleLoginAction(fragmentAction.action, toolbarState)
            }

            is FilterAction -> {
                handleFilterAction(fragmentAction, toolbarState)
            }

            is SiteListAction -> {
                handleSiteListAction(fragmentAction, toolbarState)
            }

            else -> {
                Log.d("AppStateProcessorUseCase", "Unknown action: $fragmentAction")
                Pair(AppState.HomeState, toolbarState)
            }
        }
    }

    private fun handleHomeAction(
        toolbarState: ToolbarState
    ): Pair<AppState, ToolbarState> {
        return Pair(
            AppState.HomeState,
            toolbarState.copy(
                isLogoVisible = true
            )
        )
    }

    private fun handleFilterAction(
        fragmentAction: FilterAction,
        toolbarState: ToolbarState
    ): Pair<AppState, ToolbarState> {
        return when (fragmentAction.action) {
            FRAGMENT_ON_START -> {
                Pair(
                    AppState.FilterState(
                        actionKey = fragmentAction.action,
                        isFilterSet = false,
                        storage = fragmentAction.data?.getInt(STORAGE) ?: STORAGE_LOCAL
                    ),
                    toolbarState.copy(
                        isLogoVisible = true
                    )
                )
            }

            FRAGMENT_ON_FINISH -> {
                Pair(
                    AppState.FilterState(
                        actionKey = fragmentAction.action,
                        isFilterSet = fragmentAction.data?.getBoolean(IS_FILTER_SET) ?: false,
                        storage = fragmentAction.data?.getInt(STORAGE) ?: STORAGE_LOCAL
                    ),
                    toolbarState
                )
            }
            else -> {
                throw RuntimeException("Unknown action: ${fragmentAction.action}")
            }
        }
    }

    private fun handleSiteListAction(
        fragmentAction: SiteListAction,
        toolbarState: ToolbarState
    ): Pair<AppState, ToolbarState> {
        return when (fragmentAction.action) {
            FRAGMENT_ON_START -> {
                Pair(
                    AppState.SiteListSate(
                        actionKey = fragmentAction.action,
                        storage = fragmentAction.data?.getInt(STORAGE) ?: throw RuntimeException("Unknown storage"),
                        isFilterSet = fragmentAction.data?.getBoolean(IS_FILTER_SET) ?: false
                    ),
                    toolbarState.copy(
                        isLogoVisible = false
                    )
                )
            }

            FRAGMENT_ON_FINISH -> {
                TODO()
            }
            else -> {
                throw RuntimeException("Unknown action: ${fragmentAction.action}")
            }
        }
    }


    private fun handleLoginAction(
        actionKey: String,
        toolbarState: ToolbarState
    ): Pair<AppState, ToolbarState> {
        val tbs = when (actionKey) {
            AUTHORIZATION_STARTED -> {
                toolbarState.copy(
                    isLogoVisible = false,
                    isLoginCompleted = false
                )
            }
            AUTHORIZED -> {
                toolbarState.copy(
                    isLogoVisible = true,
                    isLoginCompleted = true
                )
            }
            AUTHORIZATION_COMPLETED -> {
                toolbarState.copy(
                    isLogoVisible = true,
                    isLoginCompleted = true
                )
            }
            AUTHORIZATION_CANCELLED -> {
                toolbarState.copy(
                    isLogoVisible = true,
                    isLoginCompleted = false
                )
            }
            AUTHORIZATION_FAILED -> {
                toolbarState.copy(
                    isLogoVisible = true,
                    isLoginCompleted = false
                )
            }
            else -> {
                throw RuntimeException("Unknown action: $actionKey")
            }
        }

        return Pair(AppState.LoginState(actionKey), tbs)
    }



}