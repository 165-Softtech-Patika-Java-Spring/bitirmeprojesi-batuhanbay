package com.softtechbootcamp.bitirme.app.rpt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExportReportDto {
    Long id;
    String name;
}
