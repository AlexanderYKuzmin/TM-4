package com.kuzmin.tm_4.feature.sites.ui

import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllSitesUseCase
import javax.inject.Inject

class SitesViewModel @Inject constructor(
    private val getAllSitesUseCase: GetAllSitesUseCase
) {

}