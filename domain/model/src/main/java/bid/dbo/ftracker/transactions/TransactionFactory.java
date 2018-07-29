package bid.dbo.ftracker.transactions;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import reactor.core.publisher.Mono;
import static reactor.core.publisher.Mono.*;
import static bid.dbo.ftracker.common.StringUtil.isEmpty;

import java.math.BigDecimal;

public interface TransactionFactory {

    default Mono<Transaction> create(String account, BigDecimal amount, Transaction.MetaData data){
        if(!isEmpty(account) &&  amount != null){
            return just(Transaction.builder().account(account).amount(amount).metaData(data).build());
        }else {
            return error(BusinessValidationException.Type.INVALID_TRANSACTION_INITIAL_DATA.build());
        }
    }


}
