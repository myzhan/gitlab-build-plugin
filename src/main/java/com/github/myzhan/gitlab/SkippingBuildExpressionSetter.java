package com.github.myzhan.gitlab;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Created by myzhan on 16/8/5.
 */
public class SkippingBuildExpressionSetter extends BuildWrapper {

    public String skippingBuildExpression;


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
