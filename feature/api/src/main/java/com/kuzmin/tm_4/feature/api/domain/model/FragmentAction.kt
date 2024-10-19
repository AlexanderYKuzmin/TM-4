package com.kuzmin.tm_4.feature.api.domain.model

import android.os.Bundle
import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData

sealed class FragmentAction {
    data object HomeAction : FragmentAction()

    class LoginAction(val action: String, val data: Bundle? = null) : FragmentAction()

    class SiteListAction(val action: String, val data: Bundle? = null) : FragmentAction()

    class SingleSiteAction(val action: String, val data: Bundle? = null) : FragmentAction()

    class SiteCreationAction(val action: String, val data: Bundle? = null) : FragmentAction()

    class FilterAction(val action: String, val data: Bundle? = null) : FragmentAction()
}

