// @ExecutionModes({ON_SELECTED_NODE, ON_SELECTED_NODE_RECURSIVELY})

// ####################################################################################################
// # Documentation
// #################################################################################################### 

    // History
        // 2018-02-03_14.24.58: Added verification that the file is saved to the disk before to run the script otherwise there is no file path and mapName to use. Added also check if html is not empty and html is not the same as before when loading back the html files, to avoid for example creating a details when there was none before or setting the html for nothing when it is the same as before. 
        // 2018-02-01_23.28.32: Added the 'Edit nodes' features which basically create 4 child nodes that are used to open the html files created (3 nodes) and 1 node to browse to the folder. This helps open the html files more quickly instead of having to lookup for them in the temp directory. These 'Edit nodes' are remove when the script is executed again to read back the html files into the node. 
        // 2018-02-01_16.27.29: Changed a condition to check if html tags exists in the core text, and changed the regex to match the body tag (to lazy matching) because it was removing the text from the core text. 
        // 2018-01-31_18.20.42: Initial version.

    // Description: This script saves the text, details and note from a node to html files for external edition. When saved, a flag icon is added to the node to indicate that the node is being edited. Once the external edition is done, the script is run again and if the flag icons is found then the script will read the external files to the text, details and note instead of writing to these files when the icon is absent. Associate the .html files with Open Office sweb.exe, then clicking the files will open them in Open Office html editor. Show the node ID in the status bar for convenience. 

    // Todo
        // s0 Add that if there was no details before editing and it was not edited externally, then don't create a empty detail when reading the files (if there was not before) 
        // s0 Fix issue that we need to click or edit the text and details so it renders the html, otherwise the html code is displayed. 

// ####################################################################################################
// # Imports
// #################################################################################################### 

        import org.apache.commons.io.FilenameUtils

    // To use global Constants (the other option is just to remove '@Field def', not defining the variable will make it "global".
        import groovy.transform.Field

// ####################################################################################################
// # Declarations
// #################################################################################################### 

    // ====================================================================================================
    // = Constants
    // ==================================================================================================== 
        @Field def OUT_DIR = 'c:/Temp/'
        @Field def FLAG_ICON = 'PalmIcons/aOffice/Computer/Word'
        def EMPTY_HTML = '<html><body></body></html>'

    // ====================================================================================================
    // = Variables
    // ==================================================================================================== 
        def iconsText = node.icons.collect{it.toString()}.join(';')

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
                htmlStr = htmlStr.replaceAll('<body.*?>', '<body>')
            // Delete the file
                file.delete()
        }
        return htmlStr
    }

    // ====================================================================================================
    def makeEdit(type, pNode, text, mapName, editNode) { // =
    // ==================================================================================================== 
        if (text == null)
            nodeText = '<html><body></body></html>'
        else {
            nodeText = text
            // Add html tags if it is only text or OpenOffice will open writer instead of sweb
                if (!(nodeText =~ /<html>/))
                    nodeText = "<html><body>$nodeText</body></html>"
        }
        def fileName = mapName + '_' + pNode.id + '_' + type + '.html'
        def filePath = OUT_DIR + fileName 
        writeToHtmlFile(filePath, nodeText)
        // Add the child node
            childNode = editNode.createChild()
            childNode.text = 'Edit ' + type + ' (' + fileName + ')'
            childNode.link.text = 'file:/' + filePath
            childNode.icons.add(FLAG_ICON)
    }

// ####################################################################################################
// # Main
// #################################################################################################### 

    // Get the filename
        def mapName = ''
        try {
            URI mapUri = node.map.file.absoluteFile.toURI();
            def mapPath = mapUri.toString().replace('%20', '_')
            mapName = FilenameUtils.getBaseName(mapPath) // Get file name of source file
        }
        catch (all) {
            m('Please make sure the file is saved to disk before to run the script!')
            return
        }

    // ====================================================================================================
    // = READ: If there is the flag icon, then READ the html files
    // ==================================================================================================== 
        if (iconsText =~ '(^|;)(' + FLAG_ICON + ')') {
            icons.removeIcon(FLAG_ICON)
            // Text
                def nodeText = readHtmlFileToString(OUT_DIR + mapName + '_' + node.id + '_text.html')
                    if (nodeText != EMPTY_HTML && node.text != nodeText) // Check if html is not empty and html is not the same as before
                        node.text = nodeText
            // Details
                def nodeDetails = readHtmlFileToString(OUT_DIR + mapName + '_' + node.id + '_details.html')
                    if (nodeDetails != EMPTY_HTML && node.details != nodeDetails) // Check if html is not empty and html is not the same as before
                        node.details = nodeDetails
            // Note
                def nodeNote = readHtmlFileToString(OUT_DIR + mapName + '_' + node.id + '_note.html') // Check if html is not empty and html is not the same as before
                    if (nodeNote != EMPTY_HTML && node.note != nodeNote)
                        node.note = nodeNote
            // Remove the edit node            
                node.children.find{ it.text == 'Edit nodes' && it.icons[0].toString() == FLAG_ICON }.delete()
        }
    // ====================================================================================================
    // = WRITE: If there is NOT the flag icon, then WRITE the html files 
    // ==================================================================================================== 
        else {
            // Add the child node that will contains the other nodes
                editNode = node.createChild()
                editNode.text = 'Edit nodes'
                editNode.icons.add(FLAG_ICON)
                editNode.noteText = 'Tip: In Windows Explorer associate html file type with Open Office Html Editor (sweb.exe) which produce html that is compatible with Freeplane.'
                // Add the folder node
                    folderNode = editNode.createChild()
                    folderNode.text = 'Browse...'
                    folderNode.link.text = 'file:/' + OUT_DIR
                    folderNode.icons.add('folder')
            // Text
                makeEdit('text', node, node.text, mapName, editNode)
            // Details
                makeEdit('details', node, node.detailsText, mapName, editNode)
            // Note
                makeEdit('note', node, node.noteText, mapName, editNode)
            icons.removeIcon(FLAG_ICON)
            icons.addIcon(FLAG_ICON)
        }

