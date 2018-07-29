package bid.dbo.ftracker.repository.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class AccountData {
    @Id
    private String id;
    private String name;
    private String userAccount;
    private Date openDate;
    private String description;
    private Boolean active;
}
