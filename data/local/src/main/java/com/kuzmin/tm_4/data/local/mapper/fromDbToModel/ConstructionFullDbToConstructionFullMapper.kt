package com.kuzmin.tm_4.data.local.mapper.fromDbToModel

import com.kuzmin.tm_4.core.database.model.delivery.ConstructionAndSectionsDb
import com.kuzmin.tm_4.core.database.model.delivery.ConstructionFullDb
import com.kuzmin.tm_4.core.database.model.delivery.McDbFull
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionAndSections
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionFull
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.McFull
import javax.inject.Inject

class ConstructionFullDbToConstructionFullMapper @Inject constructor() : SiteDbToSiteModelMapper() {
    fun mapConstructionFullDbToConstructionFull(constructionFullDb: ConstructionFullDb): ConstructionFull {
        with(constructionFullDb) {
            return ConstructionFull(
                constructionAndSections = mapConAndSecDbToConAndSec(constructionAndSectionsDb),
                measurementConstructionFullList = mapMcFullDbListToMcFullList(mcFullDbList)
            )
        }
    }

    private fun mapMcFullDbListToMcFullList(mcFullDbList: List<McDbFull>): List<McFull> {
        return mcFullDbList.map {
            mapMcDbFullToMcFull(it)
        }
    }

    private fun mapMcDbFullToMcFull(mcDbFull: McDbFull): McFull {
        with(mcDbFull) {
            return McFull(
                mc = mapMeasurementConstructionDbToMeasurementConstruction(mcDb),
                groups = mapGroupListDbToGroupList(groups),
                measurements = mapMeasurementListDbToMeasurementList(measurements),
                results = mapResultListDbToResultList(results)
            )
        }
    }

    private fun mapConAndSecDbToConAndSec(constructionAndSectionsDb: ConstructionAndSectionsDb): ConstructionAndSections {
        with(constructionAndSectionsDb) {
            return ConstructionAndSections(
                construction = mapConstructionDbToConstruction(constructionDb),
                sections = mapSectionListDbToSectionList(sections)
            )
        }
    }


}