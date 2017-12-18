// Copy a links to files, folders or embedded images to map, and urls to the clipboard.

// Get/Set the content of the clipboard
	import java.awt.datatransfer.StringSelection
	import java.awt.Toolkit
	import java.awt.datatransfer.*

// Copy link to clipboard
final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()

// ====================================================================================================
// = Get operating system
// ==================================================================================================== 
    String OS = System.getProperty("os.name").toLowerCase();
    String os = ''
    if (OS.contains("win"))
        os = 'windows'
    else if (OS.contains("mac"))
        os = 'mac'
    else if (OS =~ /nix|nux|aix/)
        os = 'unix'
    else
        os = 'other'

// ====================================================================================================
// = Main
// ==================================================================================================== 
    // Get the path from a link or embedded image
        if (link.text != null)
            linkPath = node.link.text
        else if (externalObject.uri != null) // Copy path of embedded image
            linkPath = externalObject.uri.toString()

    // Remove and replace stuff in copied paths/url
        //if ((linkPath.contains('file:/') && linkPath.contains('.mm#ID_')) { // Is a freeplane link
        if (linkPath.contains('file:/') && linkPath.contains('mm#ID_')) { // Is a freeplane link
            // Do nothing
        }
        else if (linkPath =~ /\/\w:\//) { // Is a path
            linkPath = linkPath.replace('file:/', '')
            if (os == 'windows')
                linkPath = linkPath.replace('/', '\\')
            linkPath = linkPath.replace('%20', ' ')
        }
        else if (linkPath =~ /http/) // Is a url
            linkPath = linkPath.replace('file:/', '')

    clipboard.setContents(new StringSelection(linkPath), null)
