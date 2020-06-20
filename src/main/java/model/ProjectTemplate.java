package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User: Shantanu Roy
 * Date: 19-Jun-20
 * Time: 10:13 PM
 */
@Getter
@AllArgsConstructor
public enum ProjectTemplate {
    SOFTWARE_SCRUM("com.pyxis.greenhopper.jira:gh-scrum-template"),
    SOFTWARE_KANBAN("com.pyxis.greenhopper.jira:gh-kanban-template"),
    SOFTWARE_BASIC("com.pyxis.greenhopper.jira:basic-software-development-template"),
    BUSINESS_PROJECT("com.atlassian.jira-core-project-templates:jira-core-project-management"),
    BUSINESS_TASK("com.atlassian.jira-core-project-templates:jira-core-task-management"),
    BUSINESS_PROCESS("com.atlassian.jira-core-project-templates:jira-core-process-management");

    private String projectTemplateKey;
}
