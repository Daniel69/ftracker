package bid.dbo.ftracker.repository.data;

import bid.dbo.ftracker.transactions.Transaction;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document
public class TransactionData {
    @Id
    private String id;
    private String account;
    private Date date;
    private BigDecimal amount;
    private Transaction.MetaData metaData;
}
