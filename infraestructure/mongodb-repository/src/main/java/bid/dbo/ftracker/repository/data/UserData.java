package bid.dbo.ftracker.repository.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class UserData {
    @Id
    private String email;
    private String fullName;
    private Date singUpDate;
    private String mainAccount;
}
