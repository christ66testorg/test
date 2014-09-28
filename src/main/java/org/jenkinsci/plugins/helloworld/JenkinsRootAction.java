package org.jenkinsci.plugins.helloworld;

import hudson.model.RootAction;

/**
 * Created by schristou88 on 6/16/14.
 */
public class JenkinsRootAction implements RootAction {
    public String getIconFileName() {
        return "/images/jenkins.png";
    }

    public String getDisplayName() {
        return "Jenkins home page";
    }

    public String getUrlName() {
        return "http://jenkins-ci.org";
    }
}
