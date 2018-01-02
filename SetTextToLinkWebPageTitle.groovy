// @ExecutionModes({ON_SELECTED_NODE, ON_SELECTED_NODE_RECURSIVELY})

// Version history:
    // 2018-01-02_11.16.52: Added try-catch because it some cases the script fails to retrieved the title and if this script runs on multiple selected nodes, it was failing so no nodes had their titles set. So with try-catch only the nodes that fail are not set, the others will be set.

// Set the text of the node to the webpage title of it's link.

if (link =~ /http/) { // Is a url
    try {
        htmlText = link.text.toURL().text
        def title = (htmlText =~ /(?<=title>).*?(?=<)/)[0]
        node.text = title
    }
    catch (any) {}
}
