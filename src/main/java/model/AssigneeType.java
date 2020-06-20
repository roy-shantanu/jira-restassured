package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User: Shantanu Roy
 * Date: 19-Jun-20
 * Time: 10:18 PM
 */
@Getter
@AllArgsConstructor
public enum AssigneeType {

    PROJECT_LEAD("PROJECT_LEAD");

    private String typeName;
}
