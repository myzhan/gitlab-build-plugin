package com.github.myzhan.gitlabwebhooktrigger;

import net.sf.json.JSONArray;

import java.util.Map;

/**
 * Created by myzhan on 2015/12/9.
 */
public class GitlabWebHookPayload {

    private String object_kind;
    private String before;
    private String after;
    private String ref;
    private String checkout_sha;
    private String message;
    private int user_id;
    private String user_name;
    private String user_email;
    private int project_id;
    private Map repository;
    private JSONArray commits;
    private int total_commits_count;

    public String getObject_kind() {
        return object_kind;
    }

    public void setObject_kind(String object_kind) {
        this.object_kind = object_kind;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getCheckout_sha() {
        return checkout_sha;
    }

    public void setCheckout_sha(String checkout_sha) {
        this.checkout_sha = checkout_sha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Map getRepository() {
        return repository;
    }

    public void setRepository(Map repository) {
        this.repository = repository;
    }

    public JSONArray getCommits() {
        return commits;
    }

    public void setCommits(JSONArray commits) {
        this.commits = commits;
    }

    public int getTotal_commits_count() {
        return total_commits_count;
    }

    public void setTotal_commits_count(int total_commits_count) {
        this.total_commits_count = total_commits_count;
    }

    @Override
    public String toString(){
        return this.getCheckout_sha();
    }

}
