// ####################################################################################################
// # Documentation
// #################################################################################################### 

    // ====================================================================================================
    // = Version history
    // ==================================================================================================== 
        // 2018-02-20_10.27.40: Added a sort by icons which allows to group files and folders together.
        // 2018-02-16_11.38.16: Added lowercase sorting to sort by type and icon/priority.
        // 2018-02-15_18.35.26: Changed normalPath variable name by normPath. Changed some comments.
        // 2018-02-14_23.01.39: Remove the " from the sorting by name.
        // 2018-02-14_18.14.59: Added cloud to delimit projects visually.
        // 2018-02-14_15.08.39: Initial upload.

    // ====================================================================================================
    // = Description
    // ==================================================================================================== 
        // This is a script that tries to deal with project files, like one would find in a text editor project panel. 
        // NOTE: View the nodes in Outline View so it takes less horizontal space (View > View settings > Outline view).

        // The script is intented to be used only (or mostly) on its own branch, which I named "Workspace" (it can have any name really) then you would created child nodes that are the projects and put your files there.
        // I use only the script on this "Workspace" branch at the moment, but some projects could be created elsewhere and the scripts could be used on them. It is really only for nodes containing links to files and folders, not on other nodes like text or notes or images etc.
        // I personally create a docked view with that map where the "Workspace" node is located, so I see my projects and files always while opening other maps on the right view.

    // ====================================================================================================
    // = Usage
    // ==================================================================================================== 
        // The script has 2 modes, the Project mode and the Folder content mode:
            // - Project node: If the script is run on a node has maybe a link to a folder and has children nodes that are link to folders and files, then this will be considered a project node. Then an input menu will show that will allow to sort the files and folder by name, type, date, priority (icon 1, 2, 3, etc), path and size. This is useful if you want to know which files in a project was last modified, or the files that have the most content in the project, or the most important ones for you if sorted by priority. If for some files the linked files doesn't exist anymore, a red X icon will be added to the nodes that link to those files. Some icons are added to file types to help identify the files. The number of files in the project is added in the project core text. Also when the files are sorted, some info about the sort is added to the details of the nodes, like the size of the file or the full path for example. You may choose to hide the attributes also if you don't want to see the DateModified and the Size of the files and folders attributes (View > Node attributes > Hide all attributes).
            // - FolderContent: If the script is run on a node that has the 'blue-tree' icon and that node has a link to a folder, then an input box will be opened to input a regular expression to select files in a folder, like '.*\.java' for example to select all java files in that folder and its subfolder. Once the files are added as links to these files, the 'blue-tree' icon is removed, the regex used is added as an attribute. If the 'blue-tree' icon is added again the regex in attribute will be used to select again the files, but they will be added anew, which means that all child nodes are remove and replaced by the new ones retrieved using the regex. Once files are retrieved, the node becomes a project node and then it is treated as a project node. 
        // NOTES: The script has classes that tries to extend the Freeplane nodes, specially the Path, Link and NodeExtension classes. These can be reuse in other scripts. 

    // ====================================================================================================
    // = Todo
    // ==================================================================================================== 
        // s2 Add sort by icons, the icons would be sorted then the nodes would be sorted by the icons strings. This would allow to group files and folders by icons.
        // s2 Maybe add sortbyicon so that nodes are grouped together by the icons they have, this would first sort by priority icons, then by the contatenation of the other icons.
        // s0 Add sort by number of lines?
        // s0 FR Make optional the addition of icons?
        // s0 Maybe add a class to grep code files and then display there types info in subnodes? Maybe create a "TypeInfo" subnode and a "TypeInfo" class? And a Grep and CodeGrep classes also above it. So like, Project > Directory > File > Grep > CodeGrep > TypeInfo? 
        // s0 p3 t1 d0 FR: Maybe add directory and file class, and in the file class for example I could have types of files like a code file etc, and I could have grep and code stats methods in it.
        // s0 p3 t2 Document usage in the document section of the code, and also interation with project management (maybe this would be documented in a larger document in a mind map for the project management as a whole)
        // s0 p3 t3 Put + in input box to recurse the sorting?
        // s0 p2 p3 Do function that finds the 'old' projects, those where the latest modified file is old. Maybe sort the project by that date?
        // s0 p2 t3 Add a sort by path that creates the directory structure from the list of paths retrieved from subdirectories... Put icons to identify the temporary struture and then delete it later.
        // s0 Issue: FolderContent keeps its path so doesn't become fully a project node after with the icon. Allow paths in project nodes (better solution).
        // s0 p2 t3 d0 FR: Put project Management script in the project code, with database code etc
        // s0 Keep the flat view of the directory display as a possibility if I decide to put these paths in subfolder nodes. I would be nice to have both views.
        // s0  Do documentation above.
        // s-1 p2 t3 d0 FR: Create a node class to do simulations and run it in eclipse (without Freeplane)
        // s0 p2 t3 d0 FR: Include project management into this script
        // s0 p3 t2 d0 FR: Do conversion of new status info (s0 p1 t1 etc) into node attribute for project management
        // s-1 Do better setting of attribute... now I remove and add it back, but there should be a way to set it using attributes['myattrib'] = value... but I get type mismatch.
        // s-1 p3 t3 Put + in input box to recurse the sorting?
        // s0 I should review the determineLinkType because now I can set the properties in the class... so it seems there is redundance. I should continue these .bm sections: // // = path = true // = set normal and fp path properties // = Put set node code to file panel
        // s0 p2 t3 d0 FR: Add a sort by path that creates the directory structure from the list of paths retrieved from subdirectories... Put icons to identify the temporary struture and then delete it later.
        // s0 p2 t3 d0 FR: Do function that finds the 'old' projects, those where the latest modified file is old. Maybe sort the project by that date? 
        // s0 p3 p1 d0 BU: SQLite icon is not correct
        // s1 Maybe put inside functions the block so I could re-run the checking and setting of attributes after the files and folder are fetched...? 
        // s1 p3 t2 d0 BU: After fetching files in a directory, if I sort by date it writes null in the details... it is because it is looping the children of the current node, and other children are added. This could be considered a known limitation... to see later.
        // s1 p3 t2 d1 DO: Document usage in the document section of the code, and also interation with project management (maybe this would be documented in a larger document in a mind map for the project management as a whole) 
        // s2 Add 'Show path in details'
        // s2 Add file attributes as node attributes (specially the date modified)
        // s2 Add in the Project nodes the number of children then contain in parentesis
        // s2 Add sorting by date modified
        // s2 Add sorting by priority
        // s2 Add specific icons depending on the file type (.groovy, .txt, .vim etc)
        // s2 Add toString() functions to all types to debug them mainly
        // s2 Convert bytes to kb
        // s2 Determine the node type = Project node
        // s2 Maybe extend some of the classes from NodeUI and put some stuff there.. like utils 
        // s2 Refactor the code to have better verification (less redundant): hasPath, File file = new File() etc
        // s2 Remove button cancel if the file is again available
        // s2 Sort is not working.. fix it. The add the code for the foldercontent.
        // s2 The project icon doesn't work... 
        // s2 There is a red X on the project node FreeplaneScripts... there should be the project icon, fix that
        // s2 p1 t1 d2 PR: Try to put details text in lightgray (check the forum) For now I have set the text in bold when details are shown, but remove that later if I have a solution for the gray details text. 
        // s2 p1 t2 d0 FR: Maybe add date modified to folder also?
        // s2 p1 t2 d0 PR: Modify the sortNodesByNames() to include the new priority and time flags p1 t2 d2 MA: etc...  it.plainText.toLowerCase().replaceAll('^(s-1|s0|s1|s2|s3)\\s', '') 
        // s2 p1 t2 d2 FR: Add attribute FileFilter (*) if the icon is the tree and then filter files by it 
        // s2 s1 p1 t2 d0 BU: Check why the folders are identified as FILEs
    // Test
        // "D:\Temp\ProjectManagement_Workspace_TestFile.txt"

// ####################################################################################################
// # Import
// #################################################################################################### 

    import java.text.* // SimpleDateFormat

    import org.apache.commons.io.FileUtils
    import org.apache.commons.io.FilenameUtils

    import groovy.transform.Field // To use global Constants (the other option is just to remove '@Field def', not defining the variable will make it "global".

// ####################################################################################################
class Utilities { // # Utilities common to most classes.
// #################################################################################################### 
   
    // ====================================================================================================
    // = Constants 
    // ==================================================================================================== 
        // Debug
            private boolean DEBUG = false 
            private String DEBUG_DIR = 'c:/Temp/'
            private String DEBUG_FILE_PATH = DEBUG_DIR + 'debug.txt'

    // ====================================================================================================
    // = Variables 
    // ==================================================================================================== 
        private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-yy hh:mm:ss") // Used in the debug function 

    // ====================================================================================================
    // = Public methods
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        public String m(message) { // - Message box
        // ---------------------------------------------------------------------------------------------------- 
            javax.swing.JOptionPane.showMessageDialog(null, message); 
        }

        // ----------------------------------------------------------------------------------------------------
        public void d(message) { // - To write debug messages to a file
        // ----------------------------------------------------------------------------------------------------
            if (DEBUG) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(DEBUG_FILE_PATH, true));
                Date date = new Date();
                bw.write(df.format(date) + ' ' + message);
                bw.newLine();
                bw.flush();
                bw.close();
                }
            }
            // Specific debug functions 
                public void C(message) { d('### Constructor: ' + message) }
                public void c(message) { d('# Constructor: ' + message) }
                public void F(message) { d('=== Function: ' + message) }
                public void f(message) { d('= Function: ' + message) }

} // Utilities

// ####################################################################################################
class NodeUI { // # Class to work with nodes on the map.
// #################################################################################################### 

    // ====================================================================================================
    // = Constants
    // ==================================================================================================== 
        protected final String ICON_CODE = 'PalmIcons/aOffice/Computer/Coding'
        protected final String ICON_DATABASE = '3-columns'
        protected final String ICON_DOCUMENT = 'PalmIcons/aOffice/Computer/Word'
        protected final String ICON_EXE = 'executable'
        protected final String ICON_FOLDER = 'folder'
        protected final String ICON_FOLDER_CONTENT = 'PalmIcons/dFileMan/Trees/Blue-tree'
        protected final String ICON_INTERNET = 'internet'
        protected final String ICON_MINDMAP = 'bee'
        protected final String ICON_PATH_NOT_EXISTS = 'button_cancel'
        protected final String ICON_PLAIN = 'list'
        protected final String ICON_PROJECT = 'mindmap'
        protected final String ICON_SPREADSHEET = 'PalmIcons/aOffice/Computer/Excel'

        // Colors
            protected final String COL_DETAILS = 'silver'

        protected final String TODO_INDICATORS_SIMPLE = '^(s-1|s0|s1|s2|s3)\\s'
        protected final String TODO_INDICATORS_EXTENDED = '^(s-1|s0|s1|s2|s3)\\s(p1|p2|3)\\s(t1|t2|t3)\\s(d0|d1|d2|d3)\\s\\w\\w:\\s'

    // ====================================================================================================
    // = Variables 
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        // - Private variables
        // ---------------------------------------------------------------------------------------------------- 

            protected utils = new Utilities()

    // ====================================================================================================
    // = Private functions
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        protected void SetAttribute(pNode, name, value) {  // - Add/Set an attribute to a node
        // ---------------------------------------------------------------------------------------------------- 
            if (pNode.attributes.containsKey(name))
                pNode.attributes.remove(name)
            pNode.attributes.add([name, value])
        }

        // ----------------------------------------------------------------------------------------------------
        // Icons
        // ---------------------------------------------------------------------------------------------------- 

            // ····································································································
            protected void AddIcon(pNode, iconsText, iconName) { // ·
            // ···································································································· 
                utils.F('AddIcon')
                utils.d('iconsText = ' + iconsText)
                utils.d('iconName = ' + iconName)
                if (!(iconsText =~ iconName)) // There is not yet the icon
                    pNode.icons.add(iconName) // Add it
                utils.f('AddIcon')
            }

            // ····································································································
            protected void RemoveIcon(pNode, iconsText, iconName) { // ·
            // ···································································································· 
                utils.F('RemoveIcon')
                // If the list of icons is not specified then create make it
                    if (iconsText == '')
                        iconsText = pNode.icons.collect{ it.toString() }.join(';')
                // If the icon is there remove it
                    if (iconsText =~ iconName) {
                        pNode.icons.remove(iconName)
                    }
                utils.f('RemoveIcon')
            }

        // ----------------------------------------------------------------------------------------------------
        // - Sorting
        // ---------------------------------------------------------------------------------------------------- 

                // ····································································································
                protected void sortNodesByAttribute(pNode, attribute, reverse) { // · Sort files by added (custom) attributes
                // ···································································································· 
                    def sorted = new ArrayList(pNode.children).sort { 
                        def attributeText = it.attributes[attribute] 
                        // Convert the size into KB
                            if (attribute == 'Size' && attributeText != null) {
                                attributeText = (attributeText.toInteger().div(1000)).toFloat().round(2) + ' KB'
                            }
                        it.detailsText = '<html><body><font color="' + COL_DETAILS + '">' + attributeText + '</font></body></html>'
                        // Sort criteria
                            attributeText
                    }
                    if (reverse)
                        sorted = sorted.reverse()
                    sorted.eachWithIndex { it, i ->
                        it.moveTo(pNode, i)
                    }
                }

                // ····································································································
                protected void sortNodesByName(pNode) { // ·
                // ···································································································· 
                    def sorted = new ArrayList(pNode.children).sort { 
                        it.detailsText = null 
                        def sortCriteria = it.plainText.toLowerCase().replace('"', '')
                        sortCriteria = sortCriteria.replaceAll(TODO_INDICATORS_SIMPLE, '')
                        sortCriteria = sortCriteria.replaceAll(TODO_INDICATORS_EXTENDED, '')
                        sortCriteria
                    } // Ignores the personal notes flags
                    sorted.eachWithIndex { it, i ->
                        it.moveTo(pNode, i)
                    }
                }

                // ····································································································
                protected void sortNodesByType(pNode) { // · Sort the files by extension
                // ···································································································· 
                    NodeExtension nodeExt = null
                    def sorted = new ArrayList(pNode.children).sort { 
                        it.detailsText = null
                        nodeExt = new NodeExtension(it)
                        if (nodeExt?.getLink()?.getPath().getIsFile())
                            // Sort criteria
                                nodeExt?.getLink().getPath().getExtension() + nodeExt?.getLink().getPath().getExtension().toLowerCase() 
                        else
                            // Sort with this if the path is not a file that exists
                                'zzz' + it.plainText.toLowerCase() 
                        }
                    sorted.eachWithIndex { it, i ->
                        it.moveTo(pNode, i)
                    }
                }

                // ····································································································
                protected void sortNodesByLink(pNode) { // · Sort by link or path
                // ···································································································· 
                    def sorted = new ArrayList(pNode.children).sort { 
                        if (it.link.text != null) {
                            it.detailsText = '<html><body><font color="' + COL_DETAILS + '">' + it.link.text + '</font></body></html>'
                            // Sort criteria
                                it.link.text.toLowerCase() 
                        }
                    }
                    sorted.eachWithIndex { it, i ->
                        it.moveTo(pNode, i)
                    }
                }

                // ····································································································
                protected void sortNodesByPriorityNumbers(pNode, nodeExt) { // · Sort by the icons that are numbers used for priority: 1, 2, 3 etc
                // ···································································································· 
                    def sorted = new ArrayList(pNode.children).sort { 
                        it.detailsText = null
                        nodeExt.iconsText = it.icons.collect{ it2 -> it2.toString() }.join(';')
                        if (nodeExt.iconsText =~ /full-\d/) {
                            def match = nodeExt.iconsText =~ /full-\d/
                            // Sort criteria
                                match[0] + it.plainText.toLowerCase() 
                        }
                        else
                            // Sort criteria
                                'zzz' + it.plainText.toLowerCase() 
                    }
                    sorted.eachWithIndex { it, i ->
                        it.moveTo(pNode, i)
                    }
                }

                // ····································································································
                protected void sortNodesByIcons(pNode, nodeExt) { // · Sort by icons names (to group the nodes)
                // ···································································································· 
                    def sorted = new ArrayList(pNode.children).sort { 
                        it.detailsText = null
                        nodeExt.iconsText = it.icons.collect{ it2 -> it2.toString() }.join(';').split(';').sort().join(';') // Get icons, put them in list, sort the list, put back in text
                        // Sort criteria
                            nodeExt.iconsText + it.plainText.toLowerCase() 
                    }
                    sorted.eachWithIndex { it, i ->
                        it.moveTo(pNode, i)
                    }
                }

    // ====================================================================================================
    // = Public functions
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        public NodeUI() {  // - Constructor
        // ---------------------------------------------------------------------------------------------------- 
            utils.C('NodeUI')
            utils.c('NodeUI')
        } // Construtor

        // ----------------------------------------------------------------------------------------------------
        public NodeUI(pNode) { // - Constructor
        // ---------------------------------------------------------------------------------------------------- 
            utils.C('NodeUI(pNode)')
            // Loop the subchildren and the sub-subchildren recursively

            utils.c('NodeUI(pNode)')
        } // Constructor

} // NodeUI

// ####################################################################################################
class FilePanel extends NodeUI { // # Class to manage files like in a project view panel in a text editor (like EditPad Pro).
// #################################################################################################### 

    // ====================================================================================================
    // = Properties
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        private void SetNode(pNode, nodeExt) { // - Set the node properties from the type of node that is detected 
        // ---------------------------------------------------------------------------------------------------- 
            utils.F('FilePanel.SetNode')

            // Specific: EditPad Pro projects: Node is a EditPad pro project remove the extension .epp
                if (pNode.plainText =~ '.epp$')
                    pNode.text = FilenameUtils.getBaseName(nodeText).replace('.epp', '')

            // If there was no link but there was a path in the core text then set it as a link
                if (!nodeExt.getHasLinkText() && nodeExt.getLink()?.getIsLink()) 
                    pNode.link.text = nodeExt?.getLink()?.getPath().getFpPath()

            // Is folder content
                if (nodeExt.getIsFolderContent())
                    FolderContent folderContent = new FolderContent(pNode, nodeExt)

            // If is a project
                if (nodeExt.getIsProject()) {
                    // Add the number of files in the project in parentesis
                        pNode.text = pNode.plainText.replaceAll('\\s\\(\\d+\\)', '') // Remove the previous count
                        pNode.text = pNode.plainText + ' (' + pNode.children.size() + ')'
                    // Add the icon for the project
                        AddIcon(pNode, nodeExt.iconsText, ICON_PROJECT)
                        pNode.style.font.bold = true
                        // Add cloud to delimit the project visually
                            pNode.cloud.enabled = true
                            pNode.cloud.shape = 'RECT' // either 'ARC', 'STAR', 'RECT' or 'ROUND_RECT'
                    // s0 Create a project object? The class is already below in comments?
                }

            // ····································································································
            // · File doesn't exist (red X): Path is there but file/folder dosen't exist anymore'
            // ···································································································· 
                if (nodeExt?.getLink()?.getPath()?.getIsLikePath()) // If the path seems like a path (but it is not sure if it exists or is fully valid yet)
                    if (nodeExt?.getLink()?.getPath()?.getIsPath()) // If the path exists remove the icon
                        RemoveIcon(pNode, nodeExt.iconsText,  ICON_PATH_NOT_EXISTS)
                    else // If the path doesn't exist add the icon
                        AddIcon(pNode, nodeExt.iconsText, ICON_PATH_NOT_EXISTS)

            // ····································································································
            // · Add folder icon if it is a folder
            // ···································································································· 
                if (nodeExt?.getLink()?.getPath()?.getIsDirectory())
                    AddIcon(pNode, nodeExt.iconsText, ICON_FOLDER)

            // ····································································································
            // · Add file icon depending on the file type
            // ···································································································· 
                if (nodeExt?.getLink()?.getPath()?.getIsFile())

                    switch (nodeExt?.getLink()?.getPath()?.getName()) {
                        // Code
                            case '_pentadactylrc':
                            case 'vifmrc':
                            case 'vimrc':
                            case 'vimrcgui':
                            case 'vimrcold':
                            case 'vimrcplugins':
                            case 'vimrcwin':
                                AddIcon(pNode, nodeExt.iconsText, ICON_CODE)
                                break
                        // Plain
                            case 'hosts':
                                AddIcon(pNode, nodeExt.iconsText, ICON_PLAIN)
                                break
                    }

                    switch (nodeExt?.getLink()?.getPath()?.getExtension()) {
                        // Code
                            case 'ahk':
                            case 'cs':
                            case 'groovy':
                            case 'java':
                            case 'js':
                            case 'sql':
                            case 'vim':
                            case 'wf':
                                AddIcon(pNode, nodeExt.iconsText, ICON_CODE)
                                break
                        // Database
                            case 'sqlite':
                            case 'sqlite3':
                                AddIcon(pNode, nodeExt.iconsText, ICON_DATABASE)
                                break
                        // Document
                            case 'docx':
                            case 'pdf':
                                AddIcon(pNode, nodeExt.iconsText, ICON_DOCUMENT)
                                break
                        // Executable
                            case 'bat':
                            case 'exe':
                                AddIcon(pNode, nodeExt.iconsText, ICON_EXE)
                                break
                        // Internet
                            case 'htm':
                            case 'html':
                            case 'md':
                            case 'xml':
                                AddIcon(pNode, nodeExt.iconsText, ICON_INTERNET)
                                break
                        // MindMap
                            case 'mm':
                                AddIcon(pNode, nodeExt.iconsText, ICON_MINDMAP)
                                break
                        // Plain
                            case 'txt':
                            case 'ini':
                            case 'log':
                                AddIcon(pNode, nodeExt.iconsText, ICON_PLAIN)
                                break
                        // Spreadsheet
                            case 'csv':
                            case 'xlsx':
                                AddIcon(pNode, nodeExt.iconsText, ICON_SPREADSHEET)
                                break
                    }

            // ····································································································
            // · Add/set file attributes 
            // ···································································································· 

                // Files and directories
                    if (nodeExt?.getLink().getIsPath())
                        // Date modified
                            SetAttribute(pNode, 'DateModified', nodeExt.getLink().getPath().getDateModified())

                // Only files
                    if (nodeExt?.getLink()?.getPath()?.getIsFile()) // File only, size doesn't get the directory size
                        // Size
                            SetAttribute(pNode, 'Size', nodeExt?.getLink()?.getPath()?.getSize())

            utils.f('FilePanel.SetNode')
        }

    // ====================================================================================================
    // = Public functions
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        public FilePanel(selectedNode) { // - Constructor
        // ---------------------------------------------------------------------------------------------------- 
            super(selectedNode)

            utils.C('FilePanel')

            NodeExtension nodeExt = null

            // Determine the node type of the selected node and change it's properties accordingly
                nodeExt = new NodeExtension(selectedNode)
                SetNode(selectedNode, nodeExt)

            // Loop the children (only, not the subchildren)
                selectedNode.children.each { n ->
                    nodeExt = new NodeExtension(n)
                    SetNode(n, nodeExt)
                }

            // ····································································································
            // · Sort nodes
            // ···································································································· 
                def sortMethod = javax.swing.JOptionPane.showInputDialog('Enter the sort method:\n[n] Name\n[d] Date modified\n[s] Size\n[t] Type\n[p] Path\n[1] Priority numbers\n[i] Icons\n[Cancel] No sorting\n');

                switch (sortMethod) {
                    case 'n': // Name
                        sortNodesByName(selectedNode)
                        break
                    case 'd': // Date modified
                        sortNodesByAttribute(selectedNode, 'DateModified', true)
                        break
                    case 's': // Size
                        sortNodesByAttribute(selectedNode, 'Size', true)
                        break
                    case 't': // Type
                        sortNodesByType(selectedNode)
                        break
                    case 'p': // Path
                        sortNodesByLink(selectedNode)
                        break
                    case '1': // Priority numbers
                        sortNodesByPriorityNumbers(selectedNode, nodeExt)
                        break
                    case 'i': // Icons
                        sortNodesByIcons(selectedNode, nodeExt)
                        break
                    default: // No sorting
                        break
                }

            utils.c('FilePanel')
        } // Constructor

} // FilePanel

// ####################################################################################################
class NodeExtension { // # Defines extra properties to a node to further identify it. 
// #################################################################################################### 

    // ====================================================================================================
    // = Constants 
    // ==================================================================================================== 
        private final String ICON_FOLDER_CONTENT = 'PalmIcons/dFileMan/Trees/Blue-tree'

    // ====================================================================================================
    // = Variables 
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        // - Private variables
        // ---------------------------------------------------------------------------------------------------- 
            private utils = new Utilities()

            private String iconsText = null

            private String coreText = null
            private String linkText = null

            private boolean hasCoreText = false
            private boolean hasLinkText = false // Node has text in its link object

            private boolean hasChildren = false

            private boolean isProject = false
            private boolean isFolderContent = false

            private Link link = null

    // ====================================================================================================
    // = Properties
    // ==================================================================================================== 
        boolean getHasCoreText() { return hasCoreText }
        boolean getHasLinkText() { return hasLinkText }

        boolean getIsProject() { return isProject }
        void setIsProject(value) { isProject = value }

        boolean getIsFolderContent() { return isFolderContent }

        Link getLink() { return link }

    // ====================================================================================================
    // = Public functions
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        public NodeExtension(pNode) { // - Constructor
        // ---------------------------------------------------------------------------------------------------- 
            utils.C('NodeExtension')

            String linkTextTmp = null

            // ----------------------------------------------------------------------------------------------------
            // - Get the list of icons
            // ---------------------------------------------------------------------------------------------------- 
                if (pNode.icons.size() == 0)
                    iconsText = ''
                else
                    iconsText = pNode.icons.collect{it.toString()}.join(';')
                utils.d('iconsText = ' + iconsText)

            // ····································································································
            // · Define some properties
            // ···································································································· 
                    
                // Check if text
                    coreText = pNode.plainText
                    utils.d("pNode.text = $pNode.plainText")
                    hasCoreText = false
                    if (!coreText.equals(null)) {
                        hasCoreText = true
                        utils.d("coreText = $coreText")
                    }

                // Check if link
                    linkText = pNode.link.text
                    utils.d("pNode.link.text = $pNode.link.text")
                    hasLinkText = false
                    if (!linkText.equals(null)) {
                        hasLinkText = true
                        utils.d("linkText = $linkText")
                    }

                // Check if children
                    if (pNode.children.size() > 0)
                        hasChildren = true

                // Get link to check (from core text or link text)
                    linkTextTmp = null
                    // Link text
                        if (hasLinkText)
                            linkTextTmp = linkText
                    // Core text
                        else if (!hasLinkText) // No link text then try get link from text
                            if (hasCoreText)
                                linkTextTmp = coreText

                // Select the type of node using the link type
                    if (!linkTextTmp.equals(null)) {
                        link = new Link(linkTextTmp)

                        // Project node 
                            if (
                                    (
                                        (!link?.getPath()?.getIsLikePath()) // Doesn't look like a path
                                        || (link.getPath()?.getIsDirectory() && !(iconsText =~ ICON_FOLDER_CONTENT)) // Is a directory but has not the FolderContent icon 
                                    )
                                    && (hasChildren)
                                )
                                isProject = true

                        // Folder content node
                            if (link.getPath()?.getIsDirectory() && iconsText =~ ICON_FOLDER_CONTENT)
                                isFolderContent = true
                    }

            utils.d(toString())
            utils.C('NodeExtension')
        } // Constructor

        // ----------------------------------------------------------------------------------------------------
        public String toString() {  // -
        // ---------------------------------------------------------------------------------------------------- 
            utils.F('NodeExtension.toString')

            String s = ''
            s += 'hasCoreText = ' + hasCoreText.toString() + '\n'
            s += 'hasLinkText = ' + hasLinkText.toString() + '\n'
            s += 'iconsText = ' + iconsText.toString() + '\n'

            s += 'hasCoreText = ' + hasCoreText.toString() + '\n'
            s += 'hasLinkText = ' + hasLinkText.toString() + '\n'
            s += 'isProject = ' + isProject.toString() + '\n'
            s += 'isFolderContent = ' + isFolderContent.toString() + '\n'

            utils.f('NodeExtension.toString')
            return s
        } 

} // NodeExtension

// ####################################################################################################
class Link { // #
// #################################################################################################### 

    // ====================================================================================================
    // = Variables 
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        // - Private variables 
        // ---------------------------------------------------------------------------------------------------- 
            private utils = new Utilities()

            private boolean isLink = false
            private boolean isNotSet = false
            private boolean isUrl = false
            private boolean isLinkToFpNode = false
            private boolean isPath = false
            private boolean isUnknown = false

            Path path = null

    // ====================================================================================================
    // = Properties
    // ==================================================================================================== 
        boolean getIsLink() { return isLink }

        boolean getIsNotSet() { return isNotSet }
        boolean getIsUrl() { return isUrl }
        boolean getIsLinkToFpNode() { return isLinkToFpNode }
        boolean getIsPath() { return isPath }
        boolean getIsUnknown () { return isUnknown }

        Path getPath() { return path }

    // ===================================================================================================
    // = Public functions
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        public Link(linkText) {  // - Constructor
        // ---------------------------------------------------------------------------------------------------- 
            utils.C('Link')
            utils.d("linkText = $linkText")

            if (linkText.equals(null))
                isNotSet = true
            else { // If linkText contains something, define what it is
               // Is URL
                    if (linkText =~ '^http')
                        isUrl = true
                // Is Freeplane link
                    else if (linkText =~ 'mm#ID_')
                        isLinkToFpNode = true
                    else {
                        path = new Path(linkText)
                        // Is path 
                            if (path.getIsPath())
                                isPath = true
                        // Is unknown (could be for example that a coreText was passed to check)
                            else
                                isUnknown = true
                    }
                if (isUrl || isLinkToFpNode || isPath)
                    isLink = true
            }

            utils.d(toString())
            utils.c('Link')
        } // Constructor

    // ----------------------------------------------------------------------------------------------------
    public String toString() { // -
    // ----------------------------------------------------------------------------------------------------
        utils.F('Link.toString')
        String s = ''

        s += 'isLink = ' + isLink.toString() + '\n'
        s += 'isNotSet = ' + isNotSet.toString() + '\n'
        s += 'isUrl = ' + isUrl.toString() + '\n'
        s += 'isLinkToFpNode = ' + isLinkToFpNode.toString() + '\n'
        s += 'isPath = ' + isPath.toString() + '\n'
        s += 'isUnknown = ' + isUnknown.toString() + '\n'

        utils.f('Link.toString')
        return s
    }

} // Link

// ####################################################################################################
class Path { // # Class to work with paths and files strings.
// #################################################################################################### 

     // ====================================================================================================
    // = Variables 
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        // - Private variables 
        // ---------------------------------------------------------------------------------------------------- 

            private utils = new Utilities()

            private boolean isPath = false
            private boolean isFpPath = false
            private boolean isNormPath = false
            private boolean isFile = false
            private boolean isDirectory = false

            private boolean isLikePath = false
            private boolean isLikeFpPath = false
            private boolean isLikeNormPath = false

            private boolean pathExists = false

            // ····································································································
            // · Path parts
            // ···································································································· 
                private String volume = null
                private String directory = null
                private String fullPath = null
                private String name = null
                private String baseName = null
                private String extension = null

            private String fpPath = null

            // More file properties
                private File file = null
                private String dateModified = null
                private Integer fSize = 0 // size is a reserved word

    // ====================================================================================================
    // = Properties
    // ==================================================================================================== 
        boolean getIsPath() { return isPath }
        boolean getIsLikePath() { return isLikePath }

        boolean getIsFpPath() { return isFpPath }
        boolean getIsNormPath() { return isNormPath }

        boolean getIsFile() { return isFile }
        boolean getIsDirectory() { return isDirectory }

        // ----------------------------------------------------------------------------------------------------
        // - Path parts
        // ---------------------------------------------------------------------------------------------------- 
            String getVolume() { return volume }
            String getDirectory() { return directory }
            String getFullPath() { return fullPath }
            String getName() { return name }
            String getBaseName() { return baseName }
            String getExtension() { return extension }

            String getFpPath() { return fpPath }

        // More file properties
            String getDateModified() { return dateModified }
            Integer getSize() { return fSize }
            String getSizeKB() { return (fSize.toInteger().div(1000)).toFloat().round(2) + ' KB' }

    // ===================================================================================================
    // = Private functions
    // ==================================================================================================== 

        // ====================================================================================================
        private String FpPathToNormPath(linkText) { // = Freeplane path to norm path
        // ==================================================================================================== 
            def pathTmp = linkText.replace('file:/', '')
            pathTmp = pathTmp.replace('/', '\\')
            // https://www.obkb.com/dcljr/chars.html
            pathTmp = pathTmp.replace('%20', ' ')
            pathTmp = pathTmp.replace('%28', '(')
            pathTmp = pathTmp.replace('%29', ')')
            pathTmp = pathTmp.replace('%7B', '{')
            pathTmp = pathTmp.replace('%2D', '}')
            return pathTmp
        }

        // ====================================================================================================
         private String NormPathToFpPath(linkText) { // = Norm path to Freeplane path
        // ==================================================================================================== 
            def pathTmp = 'file:/' + linkText
            pathTmp = pathTmp.replace('\\', '/')
            // https://www.obkb.com/dcljr/chars.html
            pathTmp = pathTmp.replace(' ', '%20')
            pathTmp = pathTmp.replace('(', '%28')
            pathTmp = pathTmp.replace(')', '%29')
            pathTmp = pathTmp.replace('{', '%7B')
            pathTmp = pathTmp.replace('}', '%2D')
            pathTmp = pathTmp.replace('"', '')
            return pathTmp
        }

    // ===================================================================================================
    // = Public functions
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        public Path(path) {  // - Constructor
        // ---------------------------------------------------------------------------------------------------- 
            utils.C('Path')
            utils.d("path = $path")

            String pathTmp = path.replace('"', '')
            String normPath = null

            // Is like a Freeplane path
                if (pathTmp =~ '\\w:/') {
                //if (pathTmp =~ '^file:/\\w:/') {
                    isLikeFpPath = true
                    normPath = FpPathToNormPath(pathTmp)
                }
            // Is like norm path
                //else if (pathTmp =~ '(^)\"?\\w:\\\\') {
                else if (pathTmp =~ '^\\w:\\\\') {
                    isLikeNormPath = true
                    normPath = pathTmp
                }

            // Is path
                if (isLikeFpPath || isLikeNormPath) {
                    isLikePath = true
                    file = new File(normPath)
                    // File exist
                        if (file.exists()) {
                            // Set that the path is really what it seemed 
                                isPath = true
                                if (isLikeFpPath) {
                                    isFpPath = true
                                    fpPath = pathTmp
                                }
                                else if (isLikeNormPath) {
                                    isNormPath = true
                                    fpPath = NormPathToFpPath(normPath)
                                }
                            // ····································································································
                            // · Get path parts
                            // ···································································································· 
                                // Example
                                    // File name: X:\JCG\articles\org.apache.commons.io.FilenameUtils Example\notes.txt
                                    // Volume: X:\
                                    // Directory: X:\JCG\articles\org.apache.commons.io.FilenameUtils Example\
                                    // Name: notes.txt
                                    // Full path: X:\JCG\articles\org.apache.commons.io.FilenameUtils Example\notes.txt
                                    // Base name: notes
                                    // Extension: txt
                                volume = FilenameUtils.getPrefix(normPath);
                                name = FilenameUtils.getName(normPath);
                                fullPath = FilenameUtils.getFullPath(normPath) + name;
                                baseName = FilenameUtils.getBaseName(normPath);
                                extension = FilenameUtils.getExtension(normPath);
                            // More path properties
                                dateModified = new Date(file.lastModified()).format('yyyy-MM-dd hh:mm:ss')
                            // Is file
                                if (file.isFile()) {
                                    directory = volume + FilenameUtils.getPath(normPath);
                                    isFile = true
                                    fSize = file.size()
                                }
                            // Is directory
                                else if (file.isDirectory()) {
                                    directory = fullPath
                                    isDirectory = true
                                }
                        }
                }

            utils.d(toString())
            utils.c('Path')
        } // Constructor

        // ----------------------------------------------------------------------------------------------------
        public String toString() { // -
        // ---------------------------------------------------------------------------------------------------- 
            utils.F('Path.toString')
            String s = ''

            s += 'isPath = ' + isPath.toString() + '\n'
            s += 'isLikeFpPath = ' + isLikeFpPath.toString() + '\n'
            s += 'isLikeNormPath = ' + isLikeNormPath.toString() + '\n'
            s += 'isFpPath = ' + isFpPath.toString() + '\n'
            s += 'isNormPath = ' + isNormPath.toString() + '\n'
            s += 'isFile = ' + isFile.toString() + '\n'
            s += 'isDirectory = ' + isDirectory.toString() + '\n'
            s += 'volume = ' + volume + '\n'
            s += 'directory = ' + directory + '\n'
            s += 'fullPath = ' + fullPath + '\n'
            s += 'name = ' + name + '\n'
            s += 'baseName = ' + baseName + '\n'
            s += 'extension = ' + extension + '\n'
            s += 'fpPath = ' + fpPath + '\n'

            utils.f('Path.toString')
            return s
        }

} // Path

/* // #################################################################################################### */
/* class Project extends NodeUI { // # Class that defines a project (works with the Panel class). */
/* // #################################################################################################### */ 

/*     // ==================================================================================================== */
/*     // Public functions */
/*     // ==================================================================================================== */ 

/*         // ---------------------------------------------------------------------------------------------------- */
/*         public Project(pNode) { // - */
/*         // ---------------------------------------------------------------------------------------------------- */ 
/*             super(pNode) */
/*             utils.C('Project') */
/*             utils.c('Project') */
/*         } */

/* } // Project */

// ####################################################################################################
class FolderContent extends NodeUI { // # Class to import file data from a folder and subfolders and add them to the current node.
// #################################################################################################### 

    // ====================================================================================================
    // Public functions
    // ==================================================================================================== 

        // ----------------------------------------------------------------------------------------------------
        public FolderContent(pNode, nodeExt) { // -
        // ---------------------------------------------------------------------------------------------------- 
            super(pNode)
            utils.C('FolderContent')
            // ····································································································
            // · Get the folder content // - This will get the list of files and folders recursively in the current directory.
            // ···································································································· 
                String folderContentFilter = /.*/ // Regular expression to specify a filter for files that will be retrieved if there are folders with the ICON_FOLDER_CONTENT icon. By default it is all of them.
                // Define the FolderContentFilter used to select the files
                    folderContentFilter = '.*'
                    // If the node doesn't yet have the FolderContentFilter attribute
                        if (pNode.attributes['FolderContentFilter'] == null) {
                            SetAttribute(pNode, 'FolderContentFilter', '') // Create this attribute if it doesn't exist
                            // Ask the user to enter the filter
                                folderContentFilter = javax.swing.JOptionPane.showInputDialog('Enter a regular expression to filter the files that will be retrieved.\nAll files = .*\nOnly text files = .*\\.txt\nOnly text files and html files = .*\\.(txt|html)', folderContentFilter);
                                if (folderContentFilter != '' && folderContentFilter != null) { // If a folderContentFilter was specified (Not Cancel or no value)
                                        SetAttribute(pNode, 'FolderContentFilter', folderContentFilter) // Create the attribute to the response
                                }
                        }
                    // If the node has the FolderContentFilter attribute already 
                        else 
                            folderContentFilter = pNode.attributes['FolderContentFilter'] // Get the attribute to use it
                if (folderContentFilter != '') { // If a folder content filter was specified
                    // Remove the previous nodes if any
                        if (pNode.children.size() != 0)
                            pNode.children.each { it.delete() }
                    // Loop the content of the folder
                        Path path = null // To create a new path object for the current file being looped
                        new FileNameByRegexFinder().getFileNames(nodeExt?.getLink()?.getPath().getDirectory(), folderContentFilter).each { tmpNormPath ->
                            path = new Path(tmpNormPath) 
                            def tmpNode = pNode.createChild()
                            tmpNode.text = path.getName()
                            tmpNode.link.text = path.getFpPath()
                        } // Loop
                    // Remove the ICON_FOLDER_CONTENT, because otherwise it will not be a Project and will get overwritten if the script runs again. So to refresh the directory the icon has to be added again and the script run.
                        RemoveIcon(pNode, '', ICON_FOLDER_CONTENT)
                        nodeExt.setIsProject(true)
                } // folderContentFilter

            utils.c('FolderContent')
        } // Constructor
} // FolderContent

Utilities utils = new Utilities()
utils.d('######################################################################')

FilePanel fp = new FilePanel(node)
