package bid.dbo.ftracker.repository.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
@NoArgsConstructor
public class AccountData {
    @Id
    private String id;
    private String name;
    private String userAccount;
    private Date openDate;
    private String description;
    private Boolean active;

    public AccountData(String userAccount) {
        this.userAccount = userAccount;
    }
}
