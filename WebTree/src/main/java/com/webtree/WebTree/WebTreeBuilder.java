package com.webtree.WebTree;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WebTreeBuilder {
    private final String rootURL;
    private WebNode rootNode = null;
    private final Set<WebNode> visitedNodes;
    private int maxChildNodes = Integer.MAX_VALUE;
    private int size = 0;

    public WebTreeBuilder(String rootURL) {
        this.rootURL = rootURL;
        this.visitedNodes = new HashSet<>();
    }

    public WebTreeBuilder(String rootURL, int maxChildNodes) {
        this.rootURL = rootURL;
        this.visitedNodes = new HashSet<>();
        this.maxChildNodes = maxChildNodes;
    }

    public void print() {
        printNode(this.rootNode, "", false);
        System.out.printf("nodes in tree: %d\n", size());
    }

    private void printNode(WebNode node, String indent, boolean last) {
        if (node == null) return;

        System.out.print(indent);
        if (last) {
            System.out.print("└── ");
            indent += "    ";
        } else {
            System.out.print("├── ");
            indent += "|   ";
        }

        System.out.println(node);

        String finalIndent = indent;
        List<WebNode> childrenList = node.getChildWebNodes().stream().collect(Collectors.toList());

        IntStream.range(0, childrenList.size())
                .forEach(i -> printNode(childrenList.get(i), finalIndent, i == childrenList.size() - 1));
    }

    public WebNode getWebTree() {
        return this.rootNode;
    }

    public WebNode buildWebTree(int depth) {
        this.rootNode = new WebNode(this.rootURL, null);

        searchForDepth(depth, null, Set.of(this.rootNode));

        return this.rootNode;
    }

    public int size() {
        return this.size;
    }

    private void searchForDepth(int depth, WebNode parentWebNode, Set<WebNode> nodesToVisit) {
        this.size += nodesToVisit.size();

        if (depth != 0) {
            nodesToVisit.stream()
                    .forEach(node -> {
                        this.visitedNodes.add(node);

                        Document doc = urlGetRequest(node.getUrl());

                        if (doc != null) {
                            Elements elements = getNodeHrefValues(doc);

                            Set<WebNode> newNodesToVisit = elements.stream()
                                    .map(href -> new WebNode(href.attr("href"), parentWebNode))
                                    .filter(newNode -> !this.visitedNodes.contains(newNode)
                                            && newNode.getUrl().startsWith("https"))
                                    .limit(this.maxChildNodes)
                                    .collect(Collectors.toSet());

                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            node.setChildWebNodes(newNodesToVisit);

                            searchForDepth(depth - 1, node, newNodesToVisit);
                        }
                    });
        }
    }

    private Document urlGetRequest(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/124.0.0.0 " +
                            "Safari/537.36")
                    .header("Accept-Language", "*")
                    .get();
        } catch (IOException e) {
            return null;
        }
    }

    private Elements getNodeHrefValues(Document doc) {
        return doc.select("a[href]");
    }
}
