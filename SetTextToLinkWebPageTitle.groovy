// @ExecutionModes({ON_SELECTED_NODE, ON_SELECTED_NODE_RECURSIVELY})

// Set the text of the node to the webpage title of it's link

if (link =~ /http/) { // Is a url
    htmlText = link.text.toURL().text
    def title = (htmlText =~ /(?<=title>).*?(?=<)/)[0]
    node.text = title
}
	