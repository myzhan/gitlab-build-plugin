# Jenkins Gitlab Build Plugin

This plugin adds a webhook action to each jenkins project, receives webhook payload from gitlab and triggers a parameterized build.

## Why?

In the beginning, [Gitlab Hook Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Gitlab+Hook+Plugin) was used to parse the Gitlab

payload json and trigger a build to do packaging. As architecture grows, every giant service is divided into multiple micro services.

Every time a new Gitlab repository is created, I have to create a corresponding jenkins project.

Now, I can use this plugin to pack multiple Gitlab repositories, in one jenkins project!

## How it works?

This plugin adds a webhook action to each jenkins project, like this:

```
http://your-jenkins-server/job/<Your Jenkins Project Name>/webhook/
```

So, you can add this url to Gitlab as webhook, only "Push Event" and "Tag Push event" are supported by now.

*Don't miss the trailing slash* , or you will just get a HTTP 302 response.

Once commits or tags are pushed to Gitlab, it will post a json payload to all webhooks via HTTP. [Webhook Samples](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/web_hooks/web_hooks.md).

This plugin extracts several parameters from the json payload, and trigger a build. If your project contains other parameters, the default value will be used.

## Exposed parameters

| Name | Type | Default Value | Value In Build | Note |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| GITLAB_BRANCH | String | - | master | branch being pushed to|
| GITLAB_TAG | String | - | v1.0.0 | pushed tag|
| GITLAB_OBJECT_KIND | String | - | push | gitlab events, only support push or tag_push|
| GITLAB_REF | String | - | refs/heads/master | full ref |
| GITLAB_CHECKOUT_SHA | String | - | 8066278648f4277d0842678114f3b1f141bb01ba | git checkout $GITLAB_CHECKOUT_SHA|
| GITLAB_USER_NAME | String | - | myzhan| |
| GITLAB_USER_EMAIL | String | - | scau.myzhan@gmail.com ||
| GITLAB_REPOSITORY_NAME | String | - | gitlab-build-plugin ||
| GITLAB_REPOSITORY_DESCRIPTION | String | - | a jenkins plugin ||
| GITLAB_REPOSITORY_HTTP_URL | String | - | https://github.com/myzhan/gitlab-build-plugin.git | git clone $GITLAB_REPOSITORY_HTTP_URL |
| GITLAB_REPOSITORY_SSH_URL | String | - | git@github.com:myzhan/gitlab-build-plugin.git | git clone $GITLAB_REPOSITORY_SSH_URL |
