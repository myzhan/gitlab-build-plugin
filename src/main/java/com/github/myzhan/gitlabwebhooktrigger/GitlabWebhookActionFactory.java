package com.github.myzhan.gitlabwebhooktrigger;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.TransientProjectActionFactory;

import java.util.Collection;

import static java.util.Collections.singleton;

/**
 * Created by myzhan on 15-12-5.
 */
@Extension
public class GitlabWebhookActionFactory extends TransientProjectActionFactory {

    @Override
    public Collection<? extends Action> createFor(AbstractProject abstractProject) {
        return singleton(new GitlabWebhookAction());
    }

}
