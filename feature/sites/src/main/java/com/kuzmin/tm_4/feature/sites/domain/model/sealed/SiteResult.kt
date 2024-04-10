package com.kuzmin.tm_4.feature.sites.domain.model.sealed

import com.kuzmin.tm_4.feature.sites.domain.model.samples.SiteSample
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Site

sealed class SiteResult {
    class Success(val sites: List<SiteSample>): SiteResult()

    class SuccessSingle(val site: Site): SiteResult()
    class Error(val throwable: Throwable): SiteResult()

    data object Loading : SiteResult()
}