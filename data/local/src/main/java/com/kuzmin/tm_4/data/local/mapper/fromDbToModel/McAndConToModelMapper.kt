package com.kuzmin.tm_4.data.local.mapper.fromDbToModel

import com.kuzmin.tm_4.core.database.model.delivery.ConstructionAndMcFullSingle
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.McAndConstruction
import javax.inject.Inject

class McAndConToModelMapper @Inject constructor() : MeasurementConstructionFullDbToMeasurementConstructionFullMapper() {
    fun mapMcAndConDbToModel(mcAndConDb: ConstructionAndMcFullSingle): McAndConstruction {
        return McAndConstruction(
            mapConstructionDbToConstruction(mcAndConDb.constructionDb),
            mapMcFullDbToMcFull(mcAndConDb.mcDbFull)
        )
    }
}