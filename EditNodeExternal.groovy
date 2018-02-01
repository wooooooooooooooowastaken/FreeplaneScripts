// @ExecutionModes({ON_SELECTED_NODE, ON_SELECTED_NODE_RECURSIVELY})

// ####################################################################################################
// # Documentation
// #################################################################################################### 

    // History
        // 2018-01-31_18.20.42: Initial version.

    // Description: This script saves the text, details and note from a node to html files for external edition. When saved, a flag icon is added to the node to indicate that the node is being edited. Once the external edition is done, the script is run again and if the flag icons is found then the script will read the external files to the text, details and note instead of writing to these files when the icon is absent. Associate the .html files with Open Office sweb.exe, then clicking the files will open them in Open Office html editor. Show the node ID in the status bar for convenience. 

    // Todo
        // s0 Fix issue that we need to click or edit the text and details so it renders the html, otherwise the html code is displayed. 

// ####################################################################################################
// # Functions
// #################################################################################################### 

    // ====================================================================================================
    def m(message) { // = Message box (mainly to debug)
    // ==================================================================================================== 
        ui.informationMessage(message + '') // + '' to convert numeric types to string
    }

    // ====================================================================================================
    def writeToHtmlFile(path, htmlStr) { // =
    // ==================================================================================================== 
        def htmlFile = new File(path)
        htmlFile.write(htmlStr, 'utf-8')
    }

    // ====================================================================================================
    def readHtmlFileToString(path) { // =
    // ==================================================================================================== 
        File file = new File(path)
        def htmlStr = ''
        if (file.exists()) {
            htmlStr = file.text
           // Remove strings generated by Open Office (sweb.exe)
                htmlStr = htmlStr.replaceAll('<!DOCTYPE.*>', '')
                htmlStr = htmlStr.replace('<head>', '')
                    htmlStr = htmlStr.replace('<title>', '')
                    htmlStr = htmlStr.replace('</title>', '')
                    htmlStr = htmlStr.replaceAll('<meta.*/>', '')
                htmlStr = htmlStr.replace('</head>', '')
                htmlStr = htmlStr.replaceAll('<body.*>', '<body>')
            // Delete the file
                file.delete()
        }
        return htmlStr
    }

// ####################################################################################################
// # Main
// #################################################################################################### 

    // Constants
        def FLAG_ICON = 'PalmIcons/aOffice/Computer/Word'
        def OUT_DIR = 'c:/Temp/'

    // Variables
        def iconsText = node.icons.collect{it.toString()}.join(';')

    // Get the filename
        import org.apache.commons.io.FilenameUtils
        URI mapUri = node.map.file.absoluteFile.toURI();
        def mapPath = mapUri.toString().replace('%20', '_')
        def mapName = FilenameUtils.getBaseName(mapPath) // Get file name of source file

    // ====================================================================================================
    // = READ: If there is the flag icon, then READ the html files
    // ==================================================================================================== 
        if (iconsText =~ '(^|;)(' + FLAG_ICON + ')') {
            icons.removeIcon(FLAG_ICON)
            node.text = readHtmlFileToString(OUT_DIR + mapName + '_' + node.id + '_text.html')
            node.details = readHtmlFileToString(OUT_DIR + mapName + '_' + node.id + '_details.html')
            node.note = readHtmlFileToString(OUT_DIR + mapName + '_' + node.id + '_note.html')
            
        }
    // ====================================================================================================
    // = WRITE: If there is NOT the flag icon, then WRITE the html files 
    // ==================================================================================================== 
        else {
            // ----------------------------------------------------------------------------------------------------
            // - Text
            // ---------------------------------------------------------------------------------------------------- 
                if (node.text == null)
                    nodeText = '<html><body></body></html>'
                else {
                    nodeText = node.text
                    // Add html tags if it is only text or OpenOffice will open writer instead of sweb
                        if (nodeText !=~ /<html>/)
                            nodeText = "<html><body>$nodeText</body></html>"
                }
                writeToHtmlFile(OUT_DIR + mapName + '_' + node.id + '_text.html', nodeText)
            // ----------------------------------------------------------------------------------------------------
            // - Details
            // ---------------------------------------------------------------------------------------------------- 
                if (node.detailsText == null)
                    nodeDetails = '<html><body></body></html>'
                else
                    nodeDetails = node.detailsText
                writeToHtmlFile(OUT_DIR + mapName + '_' + node.id + '_details.html', nodeDetails)
            // ----------------------------------------------------------------------------------------------------
            // - Note
            // ---------------------------------------------------------------------------------------------------- 
                if (node.noteText == null)
                    nodeNote = '<html><body></body></html>'
                else
                    nodeNote = node.noteText
                writeToHtmlFile(OUT_DIR + mapName + '_' + node.id + '_note.html', nodeNote)
            icons.removeIcon(FLAG_ICON)
            icons.addIcon(FLAG_ICON)
        }

