package scesi.org.check.latefee.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scesi.org.check.core.service.TemplateService;
import scesi.org.check.latefee.model.entity.LateFeeEntity;
import scesi.org.check.latefee.model.entity.ReportGenerationErrorException;
import scesi.org.check.latefee.model.entity.TypeLateFeeEntity;
import scesi.org.check.latefee.model.exception.LateFeeNotFoundException;
import scesi.org.check.latefee.model.projection.ILateFeeProjection;
import scesi.org.check.latefee.model.projection.ILateFeeReportProjection;
import scesi.org.check.latefee.model.repository.ILateFeeRepository;
import scesi.org.check.latefee.model.repository.ITypeLateFeeRepository;
import scesi.org.check.latefee.model.output.LateFeeReportOutput;

import java.io.ByteArrayOutputStream;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Service
public class LateFeeServiceImpl implements ILateFeeService {
    private final ILateFeeRepository iLateFeeRepository;
    private final ITypeLateFeeRepository iTypeLateFeeRepository;
    private final TemplateService templateService;

    public LateFeeServiceImpl(ILateFeeRepository iLateFeeRepository,
                              ITypeLateFeeRepository iTypeLateFeeRepository,
                              TemplateService templateService) {
        this.iLateFeeRepository = iLateFeeRepository;
        this.iTypeLateFeeRepository = iTypeLateFeeRepository;
        this.templateService = templateService;
    }


    @Override
    public List<ILateFeeProjection> getAllLateFees() {
        return iLateFeeRepository.findAllLateFees();
    }

    @Override
    @Transactional
    public Boolean changeTypeLateFeeById(Long id) {
        LateFeeEntity lateFeeToChange = iLateFeeRepository.findById(id).orElseThrow(LateFeeNotFoundException::new);
        TypeLateFeeEntity typeLateFee = iTypeLateFeeRepository.getReferenceById(1L);
        lateFeeToChange.setTypeLateFeeEntity(typeLateFee);
        iLateFeeRepository.save(lateFeeToChange);
        return true;
    }

    @Override
    public LateFeeReportOutput getLateFeeNotPayedReport() {
        List<ILateFeeReportProjection> lateFees = iLateFeeRepository.findAllLateFeeReport();
        Map<String, Object> variables = Map.of(
                "lateFees", lateFees
        );
        byte[] pdfBytes = generatePdf(variables);
        YearMonth period = YearMonth.now();
        return new LateFeeReportOutput(pdfBytes, period);
    }

    private byte[] generatePdf(Map<String, Object> variables) {
        String htmlContent = templateService.getTemplate("pdf/late-fee-report", variables);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ReportGenerationErrorException();
        }
    }
}
