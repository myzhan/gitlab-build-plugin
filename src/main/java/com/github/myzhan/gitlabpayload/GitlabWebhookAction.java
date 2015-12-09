package com.github.myzhan.gitlabpayload;

import hudson.model.*;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by myzhan on 15-12-5.
 */
public class GitlabWebhookAction implements Action {

    public void doIndex(StaplerRequest request, StaplerResponse response) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            response.sendRedirect("readme");
            return;
        }

        Job project = getProject();
        if (project == null){
            response.getWriter().print("Can't find any project, make sure your url is correct");
            return;
        }

        List<ParameterValue> values = new ArrayList<ParameterValue>();

        // add all parameters with its default value
        ParametersDefinitionProperty paramDefProp = (ParametersDefinitionProperty)project.getProperty(ParametersDefinitionProperty.class);

        if (paramDefProp != null){
            List<String> params = paramDefProp.getParameterDefinitionNames();
            for(String item: params){
                ParameterDefinition pd = paramDefProp.getParameterDefinition(item);
                ParameterValue defaultValue = pd.getDefaultParameterValue();
                if (defaultValue != null) {
                    values.add(defaultValue);
                }
            }
        }

        // add gitlab webhook parameters
        values.addAll(getParametersFromJsonPayload(request));

        List<Action> actions = new ArrayList<Action>();
        actions.add(new ParametersAction(values));

        Hudson.getInstance().getQueue().schedule2((Queue.Task) project, 0, actions);
        response.getWriter().print("ok, scheduled.");

    }

    private List<ParameterValue> getParametersFromJsonPayload(StaplerRequest request){
        String body = readBodyFromRequest(request);
        if (body == null){
            return new ArrayList<ParameterValue>();
        }else{
            // body is ok, start parsing
            List<ParameterValue> values = new ArrayList<ParameterValue>();
            JSONObject jsonPayLoad = JSONObject.fromObject(body);
            GitlabWebHookPayload payload = (GitlabWebHookPayload)JSONObject.toBean(jsonPayLoad, GitlabWebHookPayload.class);

            // exposed environment vars
            StringParameterValue objectKind = new StringParameterValue("GITLAB_OBJECT_KIND", payload.getObject_kind());
            StringParameterValue ref = new StringParameterValue("GITLAB_REF", payload.getRef());
            StringParameterValue checkoutSha = new StringParameterValue("GITLAB_CHECKOUT_SHA", payload.getCheckout_sha());
            StringParameterValue userName = new StringParameterValue("GITLAB_USER_NAME", payload.getUser_name());
            StringParameterValue userEmail = new StringParameterValue("GITLAB_USER_EMAIL", payload.getUser_email());
            StringParameterValue repositoryName = new StringParameterValue("GITLAB_REPOSITORY_NAME", (String)payload.getRepository().get("name"));
            StringParameterValue repositoryDescription = new StringParameterValue("GITLAB_REPOSITORY_DESCRIPTION", (String)payload.getRepository().get("description"));
            StringParameterValue repositoryHttpUrl = new StringParameterValue("GITLAB_REPOSITORY_HTTP_URL", (String)payload.getRepository().get("git_http_url"));
            StringParameterValue repositorySshUrl = new StringParameterValue("GITLAB_REPOSITORY_SSH_URL", (String)payload.getRepository().get("git_ssh_url"));

            values.add(objectKind);
            values.add(ref);
            values.add(checkoutSha);
            values.add(userName);
            values.add(userEmail);
            values.add(repositoryName);
            values.add(repositoryDescription);
            values.add(repositoryHttpUrl);
            values.add(repositorySshUrl);

            return values;
        }
    }

    private String readBodyFromRequest(StaplerRequest request) {
        try {
            return IOUtils.toString(request.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getIconFileName() {
        return "clock.gif";
    }

    @Override
    public String getDisplayName() {
        return "Gitlab Webhook";
    }

    @Override
    public String getUrlName() {
        return "webhook";
    }

    /**
     * Method will return current project.
     *
     * @return currentProject.
     */
    public Job getProject() {
        Job currentProject = null;
        StaplerRequest request = Stapler.getCurrentRequest();
        if (request != null) {
            currentProject = request.findAncestorObject(Job.class);
        }
        return currentProject;
    }


}
