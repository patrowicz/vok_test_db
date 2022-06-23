package com.example.vok_test_db.entities

import com.github.vokorm.dataloader.SqlDataLoader
import com.github.vokorm.db
import com.gitlab.mvysny.jdbiorm.DaoOfAny
import java.time.LocalDateTime

data class QueryEncounterAndPatient(
    var patientId:String? = null,
    var encounterId:String? = null,
    var encounterStarted:LocalDateTime? = null,
    var encounterEnded:LocalDateTime? = null,

) {
    companion object:DaoOfAny<QueryEncounterAndPatient>(QueryEncounterAndPatient::class.java) {
        override fun findAll() = db {
            handle.createQuery(
                """select fp.id as patientId,e.id as encounterId,lower(e.dt_date) as encounterStarted,upper(e.dt_date) as encounterEnded from fhir_encounter e left join fhir_patient fp on e.ref1_subject=fp.id and e.tenant = fp.tenant"""
            )
                .map(rowMapper)
                .list()
        }
        fun provider() = SqlDataLoader<QueryEncounterAndPatient>(
            DaoOfAny(QueryEncounterAndPatient::class.java),
            """select fp.id as patientId,e.id as encounterId,lower(e.dt_date) as encounterStarted,upper(e.dt_date) as encounterEnded from fhir_encounter e left join fhir_patient fp on e.ref1_subject=fp.id and e.tenant = fp.tenant where 1=1 {{WHERE}} order by 1=1{{ORDER}} {{PAGING}}"""
        )

    }
}
