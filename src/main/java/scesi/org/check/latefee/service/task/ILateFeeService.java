package scesi.org.check.latefee.service.task;

import scesi.org.check.latefee.model.projection.ILateFeeProjection;
import scesi.org.check.latefee.model.output.LateFeeReportOutput;

import java.util.List;

public interface ILateFeeService {
    List<ILateFeeProjection> getAllLateFees();

    Boolean changeTypeLateFeeById(Long id);

    LateFeeReportOutput getLateFeeNotPayedReport();
}
