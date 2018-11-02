package com.gdsp.platform.schedule.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew Payne JobDefVO is model bean used in providing a repository
 *         of Registered Job Types. JobDefinitions help supply infomative that
 *         can used to provide a wrapper of requirements around a job. i.e.
 *         provide a listing of what types of jobs there are and the input
 *         parameters for those jobs.
 *
 */
public class JobDefVO {

    private String                   name;
    private String                   group;
    private String                   description;
    private String                   className;
    private final List<JobParameter> parameters = new ArrayList<JobParameter>();

    /**
         * 
         */
    public JobDefVO() {}

    public JobDefVO(String jobName, String desc, String cName) {
        this.name = jobName;
        this.description = desc;
        this.className = cName;
    }

    /**
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return
     */
    public List<JobParameter> getParameters() {
        return parameters;
    }

    /**
     * @param string
     */
    public void setClassName(String string) {
        className = string;
    }

    /**
     * @param string
     */
    public void setDescription(String string) {
        description = string;
    }

    public void addParameter(JobParameter param) {
        this.parameters.add(param);
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}