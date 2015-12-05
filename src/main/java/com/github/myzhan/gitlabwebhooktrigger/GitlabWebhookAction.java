package com.github.myzhan.gitlabwebhooktrigger;

import hudson.model.Action;
import hudson.model.Hudson;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by myzhan on 15-12-5.
 */
public class GitlabWebhookAction implements Action {


    public void doIndex(StaplerRequest request, StaplerResponse response) throws IOException, ServletException {
        if (request.getMethod().equals("GET")){
            response.getWriter().print("Hello World");
            //request.getView(this, "index.jelly").forward(request, response);
        }else {
        }
    }


    @Override
    public String getIconFileName() {
        return "clock.gif";
    }

    @Override
    public String getDisplayName(){
        return "Gitlab Webhook";
    }

    @Override
    public String getUrlName() {
        return "webhook";
    }
}
