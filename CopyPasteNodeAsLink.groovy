// (F9 to copy then F9 to paste) Copy the text of the node and it's node URI and then "paste it somewhere", that is, create a new node with the copied text and put the node URI as the link, so to link this new node with the previous "copied".

// Get/Set the content of the clipboard
    import java.awt.datatransfer.*
    import java.awt.Toolkit

    final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    clipboardContents = clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor)
	
// Paste the node-link
    if (clipboardContents.contains('¬')) {
        // Get the information from the clipboard
            // (text, link) = clipboard.tokenize('Â§Â§')
            (text, link) = clipboardContents.tokenize('¬')
        // Create a new node with the information from the clipboard
            child = node.createChild()
            child.text = text
            child.link.text = link
        // Reset the clipboard
            clipboard.setContents(new StringSelection(''), null)
    }
// Copy the node-link
    else {
        // Make sure the map is saved (not a new map) to have a file name to copy. 
            URI mapUri = node.map.file.absoluteFile.toURI();
		clipboard.setContents(new StringSelection(node.text + '¬' + mapUri.toString() + '#' + node.id), null)
    }
