# Jenkins Gitlab Build Plugin

This plugin adds an action to each jenkins project, receives gitlab webhook and triggers a parameterized build.

## Development

Follow the [official plugin tutorial](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial).

## Debug

```bash
mvn hpi:run
```

## Install

### Build hpi

```
mvn package
```

This should create target/gitlab-build.hpi, you can manually upload it to jenkins and install.

## Why?

In the beginning, [Gitlab Hook Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Gitlab+Hook+Plugin) was used to parse
the gitlab webhook and trigger a build. As architecture grows, every giant service is divided into multiple micro services.

Every time a gitlab repository is created, I have to create a corresponding jenkins project.Now, I can use this plugin
to pack multiple Gitlab repositories, in one jenkins project!

## How it works?

This plugin adds an action to each jenkins project, like this:

```
http://your-jenkins-server/job/<Your Jenkins Project Name>/webhook/
```

So, you can add this url to gitlab as webhook, only "Push Event" and "Tag Push event" are supported by now.

**Don't miss the trailing slash**, or you will just get a HTTP 302 response.

Once commits or tags are pushed to gitlab, it will post a json request to all webhooks.

This plugin extracts several parameters from the json request, and trigger a build. If your project contains other parameters, the default value will be used.

[Webhook Samples](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/project/integrations/webhooks.md).

## Exposed parameters

| Name | Type | Default Value | Value In Build | Note |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| GITLAB_BRANCH | String | - | master | branch being pushed to|
| GITLAB_TAG | String | - | v1.0.0 | pushed tag|
| GITLAB_OBJECT_KIND | String | - | push | gitlab events, only support push or tag_push|
| GITLAB_REF | String | - | refs/heads/master | full ref |
| GITLAB_BEFORE | String | - | 8066278648f4277d0842678114f3b1f141bb01ba | git checkout $GITLAB_BEFORE|
| GITLAB_AFTER | String | - | 8066278648f4277d0842678114f3b1f141bb01ba | git checkout $GITLAB_AFTER|
| GITLAB_CHECKOUT_SHA | String | - | 8066278648f4277d0842678114f3b1f141bb01ba | git checkout $GITLAB_CHECKOUT_SHA|
| GITLAB_USER_NAME | String | - | myzhan| |
| GITLAB_USER_EMAIL | String | - | scau.myzhan@gmail.com ||
| GITLAB_REPOSITORY_NAME | String | - | gitlab-build-plugin ||
| GITLAB_REPOSITORY_DESCRIPTION | String | - | a jenkins plugin ||
| GITLAB_REPOSITORY_HTTP_URL | String | - | https://github.com/myzhan/gitlab-build-plugin.git | git clone $GITLAB_REPOSITORY_HTTP_URL |
| GITLAB_REPOSITORY_SSH_URL | String | - | git@github.com:myzhan/gitlab-build-plugin.git | git clone $GITLAB_REPOSITORY_SSH_URL |

## For Jenkins 2.x

Before Jenkins 2.x, you don't have to define such GITLAB_* parameters in your project. But Jenkins 2.x fixed a security issue by filtering
the build parameters based on what is defined on the job. Check [this article](https://wiki.jenkins-ci.org/display/SECURITY/Jenkins+Security+Advisory+2016-05-11) for details.

If you are using Jenkins 2.x, define the GITLAB_* parameters as you need, and everything works as usual.

## CSRF Protection

This plugin adds a CrumbExclusion to bypass the CSRF protection of jenkins.

See [https://github.com/elvanja/jenkins-gitlab-hook-plugin/issues/30](https://github.com/elvanja/jenkins-gitlab-hook-plugin/issues/30)