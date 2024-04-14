package com.kuzmin.tm_4.feature.sites.domain.model.sealed

import com.kuzmin.tm_4.feature.api.model.sample.SiteSample
import com.kuzmin.tm_4.feature.api.model.site.Site

sealed class SiteResult {
    class Success(val sites: List<SiteSample>): SiteResult()

    class SuccessSingle(val site: Site): SiteResult()
    class Error(val throwable: Throwable): SiteResult()

    data object Loading : SiteResult()
}