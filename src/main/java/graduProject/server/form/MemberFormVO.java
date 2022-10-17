package graduProject.server.form;

import graduProject.server.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormVO {
    private String name;
    private String email;
    private String password;
}
