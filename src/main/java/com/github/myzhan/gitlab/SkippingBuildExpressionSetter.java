package com.github.myzhan.gitlab;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

/**
 * Created by myzhan on 16/8/5.
 */
public class SkippingBuildExpressionSetter extends BuildWrapper {

    public String skippingBuildExpression;


    @Override
    public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        return new Environment() {
            @Override
            public boolean tearDown(AbstractBuild build, BuildListener listener) throws IOException, InterruptedException {
                return super.tearDown(build, listener);
            }
        };
    }


    @DataBoundConstructor
    public SkippingBuildExpressionSetter(String skippingBuildExpression) {
        this.skippingBuildExpression = skippingBuildExpression;
    }


    @Extension
    public static class DescriptorImpl extends BuildWrapperDescriptor {
        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Set skipping build expression, when receiving webhook from gitlab.";
        }
    }
}
