package bid.dbo.ftracker.transactions;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import reactor.core.publisher.Mono;

import javax.xml.crypto.Data;

import static reactor.core.publisher.Mono.*;
import static bid.dbo.ftracker.common.StringUtil.isEmpty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.BiFunction;

public interface TransactionFactory {

    default Mono<Transaction> create(String account, Double amount, Transaction.MetaData data, String categoryId){
        if(!isEmpty(account) &&  amount != null && !isEmpty(categoryId)){
            return just(Transaction.builder().account(account).amount(amount).categoryId(categoryId).metaData(data).build());
        }else {
            return error(BusinessValidationException.Type.INVALID_TRANSACTION_INITIAL_DATA.build());
        }
    }

    default BiFunction<String, Date, Transaction> prepareNew(Transaction transaction){
        return (id, date) ->  transaction.toBuilder().id(id).date(date).build();
    }


}
