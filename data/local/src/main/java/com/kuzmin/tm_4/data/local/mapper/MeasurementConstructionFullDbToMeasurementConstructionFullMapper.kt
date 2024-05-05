package com.kuzmin.tm_4.data.local.mapper

import com.kuzmin.tm_4.core.database.delivery.McDbFull
import com.kuzmin.tm_4.feature.api.model.model_complex_obj.McFull
import javax.inject.Inject

class MeasurementConstructionFullDbToMeasurementConstructionFullMapper @Inject constructor()
    : SiteDbToSiteModelMapper() {
    fun mapMcFullDbToMcFull(mcDbFull: McDbFull): McFull {
        return McFull(
            mc = mapMeasurementConstructionDbToMeasurementConstruction(mcDbFull.mcDb),
            groups = mapGroupListDbToGroupList(mcDbFull.groups) ?: emptyList(),
            measurements = mapMeasurementListDbToMeasurementList(mcDbFull.measurements) ?: emptyList(),
            results = mapResultListDbToResultList(mcDbFull.results) ?: emptyList()
        )
    }
}