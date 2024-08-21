package com.webtree.WebTree;

import java.util.HashSet;
import java.util.Set;

public class WebNode {
    private String url;
    private Set<WebNode> childWebNodes;
    private WebNode parentWebNode;

    public WebNode(String url, WebNode parentWebNode) {
        this.url = url;
        this.childWebNodes = new HashSet<>();
        this.parentWebNode = parentWebNode;
    }

    public void setChildWebNodes(Set<WebNode> childWebNodes) {
        this.childWebNodes = childWebNodes;
    }

    public String getUrl() {
        return this.url;
    }

    public WebNode getParentWebNode() {
        return this.parentWebNode;
    }

    public Set<WebNode> getChildWebNodes() {
        return this.childWebNodes;
    }

    @Override
    public String toString() {
        return this.url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebNode webNode = (WebNode) o;
        return url.equals(webNode.url);
    }

}
