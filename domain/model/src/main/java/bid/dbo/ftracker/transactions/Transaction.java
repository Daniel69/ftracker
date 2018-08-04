package bid.dbo.ftracker.transactions;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Builder(toBuilder = true)
@Getter
public class Transaction {
    private final String id;
    private final String account;
    private final Date date;
    private final Double amount;

    private final MetaData metaData;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MetaData {
        private String ref1;
        private String ref2;
        private String description;
        private List<String> tags;
        private Map<String, String> extra;
    }

}
