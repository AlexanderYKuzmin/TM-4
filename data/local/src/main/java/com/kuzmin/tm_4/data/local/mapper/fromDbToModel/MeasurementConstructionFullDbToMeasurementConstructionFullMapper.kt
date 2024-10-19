package com.kuzmin.tm_4.data.local.mapper.fromDbToModel

import com.kuzmin.tm_4.core.database.model.delivery.McDbFull
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.McFull
import javax.inject.Inject

open class MeasurementConstructionFullDbToMeasurementConstructionFullMapper @Inject constructor()
    : SiteDbToSiteModelMapper() {

    fun mapMcDbFullListToMcFullList(mcDbFullList: List<McDbFull>): List<McFull> {
        return mcDbFullList.map {
            mapMcFullDbToMcFull(it)
        }
    }

    fun mapMcFullDbToMcFull(mcDbFull: McDbFull): McFull {
        return McFull(
            mc = mapMeasurementConstructionDbToMeasurementConstruction(mcDbFull.mcDb),
            groups = mapGroupListDbToGroupList(mcDbFull.groups) ?: emptyList(),
            measurements = mapMeasurementListDbToMeasurementList(mcDbFull.measurements) ?: emptyList(),
            results = mapResultListDbToResultList(mcDbFull.results) ?: emptyList(),
            levelsInfo = mapLevelsInfoDbToLevelsInfo(mcDbFull.levelsInfo)
        )
    }
}