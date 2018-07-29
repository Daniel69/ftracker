package bid.dbo.ftracker.repository.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document
public class UserAccountData {
    @Id
    private String id;
    private Date creationDate;
    private List<String> owners;
}
