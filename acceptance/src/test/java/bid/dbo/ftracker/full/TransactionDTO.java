package bid.dbo.ftracker.full;

import bid.dbo.ftracker.transactions.Transaction;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {
    private String id;
    private String account;
    private Date date;
    private Double amount;
    private String categoryId;
    private Transaction.MetaData metaData;
}
