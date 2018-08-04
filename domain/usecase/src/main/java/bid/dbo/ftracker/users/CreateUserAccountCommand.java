package bid.dbo.ftracker.users;

import lombok.Data;

@Data
public class CreateUserAccountCommand {
    private String email;
    private String fullName;
    private String passwd;
}
