package bid.dbo.ftracker.accounts;

import lombok.Data;

@Data
public class CreateAccountCommand  {
    private String email;
    private String fullName;
    private String passwd;
}
