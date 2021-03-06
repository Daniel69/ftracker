package bid.dbo.ftracker.transactions;

import bid.dbo.ftracker.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionCommand {
    private String account;
    private Double amount;
    private String categoryId;
    private User user;
    private Transaction.MetaData metaData;

}
