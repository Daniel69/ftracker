package bid.dbo.ftracker.transactions;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class Transaction {
    private final String account;
    private final Date date;
    private final BigDecimal amount;

    private final MetaData metaData;

    @Builder
    @Getter
    public static class MetaData {
        private final String ref1;
        private final String ref2;
        private final String description;
        private final List<String> tags;
        private final Map<String, String> extra;
    }

}
