package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * User: Shantanu Roy
 * Date: 19-Jun-20
 * Time: 9:44 PM
 */

@Getter
@Setter
@AllArgsConstructor
public class User {

    private String username;

    private String password;

    private String sessionId;
}
