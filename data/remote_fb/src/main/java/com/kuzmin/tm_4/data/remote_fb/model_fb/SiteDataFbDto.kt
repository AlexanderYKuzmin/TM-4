package com.kuzmin.tm_4.data.remote_fb.model_fb

import com.kuzmin.tm_4.core.network_fb.model.ConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.GroupFbDto
import com.kuzmin.tm_4.core.network_fb.model.MeasurementConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.MeasurementFbDto
import com.kuzmin.tm_4.core.network_fb.model.ResultFbDto
import com.kuzmin.tm_4.core.network_fb.model.SectionFbDto
import com.kuzmin.tm_4.core.network_fb.model.SiteFbDto
import com.kuzmin.tm_4.feature.api.model.site.SiteEquipment

data class SiteDataFbDto(
    val siteFbDto: SiteFbDto,
    val constructions: Map<String, List<ConstructionFbDto>>,
    val measurementConstructions: Map<String, List<MeasurementConstructionFbDto>>? = null,
    //val siteEquipment: Map<String, List<SiteEquipmentDto>>? = null
    val sections: Map<String, List<SectionFbDto>>? = null,
    val groups: Map<String, List<GroupFbDto>>? = null,
    val measurements: Map<String, List<MeasurementFbDto>>? = null,
    val results: Map<String, List<ResultFbDto>>? = null
)