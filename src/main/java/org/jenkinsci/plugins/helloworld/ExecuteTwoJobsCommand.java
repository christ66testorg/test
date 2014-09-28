package org.jenkinsci.plugins.helloworld;

import hudson.Extension;
import hudson.cli.CLICommand;
import hudson.model.AbstractProject;
import hudson.model.Item;
import hudson.model.User;
import jenkins.model.Jenkins;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;


@Extension
public class ExecuteTwoJobsCommand extends CLICommand {
    @Option(name="--first", usage="Name of first job to build",required=true)
    public String first;

    @Option(name="--second",usage="Name of second job to build",required=true)
    public String second;

    @Override
    public String getShortDescription() {
        return "Execute two jobs";
    }

    @Override
    protected int run() throws Exception {
        // First make sure user is logged in
        if (User.current() == null) {
            stderr.println("Must be logged in before executing...");
            return 1;
        }

        // First check to see if they exist
        Jenkins instance = Jenkins.getInstance();
        AbstractProject project1 = instance.getItemByFullName(first, AbstractProject.class);
        if (project1 == null) {
            throw new CmdLineException(null, "Project: " + first + " does not exist");
        }
        AbstractProject project2 = instance.getItemByFullName(second, AbstractProject.class);
        if (project2 == null) {
            throw new CmdLineException(null, "Project: " + second + " does not exist");
        }

        // Make sure user has permission to build.
        if (!project1.hasPermission(Item.BUILD) &&
            !project2.hasPermission(Item.BUILD)) {
            throw new CmdLineException(null, "You do not have permission to execute these jobs.");
        }

        // Let's build both projects.
        project1.scheduleBuild2(0);
        project2.scheduleBuild2(0);
        return 0;
    }
}