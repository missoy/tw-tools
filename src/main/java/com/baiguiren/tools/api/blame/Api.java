package com.baiguiren.tools.api.blame;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Api {

    private String cls;

    private String developer;

    private String docsUrl;

    private String reference;

    private String rawDocs;

    public String getCls() {
        return cls;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getDocsUrl() {
        return docsUrl;
    }

    public String getReference() {
        return reference;
    }

    public String getRawDocs() {
        return rawDocs;
    }

    public Api(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            this.cls = jsonObject.getString("class");
            this.developer = jsonObject.getString("developer");
            this.docsUrl = jsonObject.getString("docs_url");
            this.reference = jsonObject.getString("reference");
            this.rawDocs = jsonObject.getString("raw_docs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
