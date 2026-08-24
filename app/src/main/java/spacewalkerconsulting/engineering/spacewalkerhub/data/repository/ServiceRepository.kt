package spacewalkerconsulting.engineering.spacewalkerhub.data.repository

import spacewalkerconsulting.engineering.spacewalkerhub.data.model.ServiceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalTime

class ServiceRepository {
    private val services: List<ServiceModel> = listOf(
        service(
            1,
            "Industrial Engineering Review",
            "Independent review of production systems, layouts, process risks, and opportunities for measurable operational improvement.",
            180.0,
            "Industrial Engineering",
            90,
            "https://images.unsplash.com/photo-1581092160562-40aa08e78837?w=1200",
        ),
        service(
            2,
            "Technical Project Audit",
            "Evidence-led assessment of design decisions, delivery controls, technical documentation, and readiness for the next project gate.",
            220.0,
            "Technical Expertise",
            120,
            "https://images.unsplash.com/photo-1503387762-592deb58ef4e?w=1200",
        ),
        service(
            3,
            "Energy Performance Audit",
            "A structured review of energy use, major loads, efficiency opportunities, and practical actions ranked by impact.",
            195.0,
            "Energy & Environment",
            90,
            "https://images.unsplash.com/photo-1473341304170-971dccb5ac1e?w=1200",
        ),
        service(
            4,
            "Materials Selection Advisory",
            "Expert comparison of materials against operating conditions, durability, manufacturability, compliance, and lifecycle cost.",
            160.0,
            "Materials Science",
            60,
            "https://images.unsplash.com/photo-1531053326607-9d349096d887?w=1200",
        ),
        service(
            5,
            "Systems Safety Assessment",
            "Identification of failure modes, safeguards, critical interfaces, and priority actions for safer engineered systems.",
            210.0,
            "Technical Expertise",
            120,
            "https://images.unsplash.com/photo-1581092918056-0c4c3acd3789?w=1200",
        ),
        service(
            6,
            "Engineering Design Consultation",
            "Focused consultation for complex requirements, concept selection, design trade-offs, and technical decision planning.",
            125.0,
            "Industrial Engineering",
            60,
            "https://images.unsplash.com/photo-1581092795360-fd1ca04f0952?w=1200",
        ),
        service(
            7,
            "Environmental Compliance Review",
            "Review of project impacts, compliance evidence, reporting gaps, and proportionate mitigation measures.",
            175.0,
            "Energy & Environment",
            90,
            "https://images.unsplash.com/photo-1497435334941-8c899ee9e8e9?w=1200",
        ),
        service(
            8,
            "Standards & Certification Support",
            "A clear route through applicable technical standards, conformity evidence, documentation, and certification planning.",
            145.0,
            "Standards",
            60,
            "https://images.unsplash.com/photo-1450101499163-c8848c66ca85?w=1200",
        ),
        service(
            9,
            "Asset Condition Survey",
            "On-site or remote condition assessment covering degradation, maintenance exposure, critical defects, and next actions.",
            240.0,
            "Technical Expertise",
            120,
            "https://images.unsplash.com/photo-1504917595217-d4dc5ebe6122?w=1200",
        ),
        service(
            10,
            "Innovation Feasibility Study",
            "Technical and commercial feasibility framing for new engineering concepts, technology adoption, and pilot programmes.",
            200.0,
            "Innovation",
            90,
            "https://images.unsplash.com/photo-1535378917042-10a22c95931a?w=1200",
        ),
        service(
            11,
            "Lifecycle Cost Analysis",
            "Whole-life comparison of engineering options using capital, maintenance, energy, risk, and replacement assumptions.",
            185.0,
            "Industrial Engineering",
            90,
            "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=1200",
        ),
        service(
            12,
            "Root Cause Investigation",
            "Disciplined investigation of technical incidents using evidence mapping, causal analysis, and corrective action design.",
            260.0,
            "Technical Expertise",
            120,
            "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=1200",
        ),
    )

    private fun service(
        id: Int,
        name: String,
        description: String,
        price: Double,
        category: String,
        durationMinutes: Int,
        imageUrl: String,
    ) = ServiceModel(
        id = id,
        name = name,
        description = description,
        price = price,
        availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(11, 30), LocalTime.of(14, 0)),
        imageUrl = imageUrl,
        category = category,
        durationMinutes = durationMinutes,
        features = listOf("Expert-led technical review", "Clear findings and priorities", "Actionable written recommendations", "Follow-up question window"),
    )

    fun observeAll(): Flow<List<ServiceModel>> {
        return flowOf(services)
    }

    fun observeById(id: Int): Flow<ServiceModel?> {
        val service = services.firstOrNull { service -> service.id == id }
        return flowOf(service)
    }

    fun getById(id: Int): ServiceModel? {
        return services.firstOrNull { service -> service.id == id }
    }
}
