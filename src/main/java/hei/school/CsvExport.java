package hei.school;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Grades export for the registrar's office (opened in Excel, semicolon separator).
 */
@RestController
public class CsvExport {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/exports/grades.csv")
    public ResponseEntity<String> export() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT st.reference, st.full_name, st.email, st.cohort, e.title, e.deadline, s.submitted_at, s.raw_points, e.max_points, "
                        + "(s.submitted_at::date > e.deadline) AS late "
                        + "FROM submission s JOIN student st ON st.id = s.student_id JOIN exercise e ON e.id = s.exercise_id "
                        + "ORDER BY st.full_name, e.deadline");

        StringBuilder csv = new StringBuilder();
        csv.append("reference;etudiant;email;promotion;exercice;echeance;soumis_le;en_retard;note_sur_20\n");
        for (Map<String, Object> row : rows) {
            String note = "";
            if (row.get("raw_points") != null) {
                LocalDate deadline = ((java.sql.Date) row.get("deadline")).toLocalDate();
                LocalDateTime submittedAt = ((Timestamp) row.get("submitted_at")).toLocalDateTime();
                double n = penalizedGrade(
                        ((BigDecimal) row.get("raw_points")).doubleValue(),
                        ((BigDecimal) row.get("max_points")).doubleValue(),
                        deadline, submittedAt);
                note = String.valueOf(n).replace('.', ',');
            }
            csv.append(row.get("reference")).append(';')
                    .append(row.get("full_name")).append(';')
                    .append(row.get("email")).append(';')
                    .append(row.get("cohort")).append(';')
                    .append(row.get("title")).append(';')
                    .append(row.get("deadline")).append(';')
                    .append(row.get("submitted_at")).append(';')
                    .append(Boolean.TRUE.equals(row.get("late")) ? "oui" : "non").append(';')
                    .append(note).append('\n');
        }

        return ResponseEntity.ok()
                .header("Content-Type", "text/csv; charset=UTF-8")
                .header("Content-Disposition", "attachment; filename=\"notes-kilasy.csv\"")
                .body(csv.toString());
    }

    private static double penalizedGrade(double raw, double max, LocalDate deadline, LocalDateTime submittedAt) {
        if (max <= 0) {
            return 0;
        }
        double base = raw / max * 20;
        long daysLate = Math.max(0, ChronoUnit.DAYS.between(deadline, submittedAt.toLocalDate()));
        double penalty = Math.min(daysLate * 0.10, 0.50);
        return Math.max(0, Math.round(base * (1 - penalty) * 10) / 10.0);
    }
}
