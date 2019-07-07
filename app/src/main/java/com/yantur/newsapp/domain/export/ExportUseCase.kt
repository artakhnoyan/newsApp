package com.yantur.newsapp.domain.export

class ExportUseCase(private val exportRepository: ExportRepository) {
    operator fun invoke(objectToExport: Any, filePath: String) {
        exportRepository.exportToFile(objectToExport, filePath)
    }
}