package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User: Shantanu Roy
 * Date: 19-Jun-20
 * Time: 10:11 PM
 */

@Getter
@AllArgsConstructor
public enum ProjectType {
    SOFTWARE("software"),
    BUSINESS("business");

    String projectTypeKey;
}
