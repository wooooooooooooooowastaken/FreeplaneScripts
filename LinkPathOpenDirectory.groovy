// s0 Adapt to support relative paths

// (ctrl + alt + z) Browse to the directory of the file or folder in the link or of the embedded image in the map. If the node has both a link and an image embedded, both directories will be opened.

// executeOnShell (from http://www.joergm.com/2010/09/executing-shell-commands-in-groovy/)
    def executeOnShell(String command) {
        return executeOnShell(command, new File(System.properties.'user.dir'))
    }

    def executeOnShell(String command, File workingDir) {
        //println command
        def process = new ProcessBuilder(addShellPrefix(command))
                                    .directory(workingDir)
                                    .redirectErrorStream(false) 
                                    .start()
        //process.inputStream.eachLine {println it}
        process.waitFor();
        return process.exitValue()
    }

    def addShellPrefix(String command) {
        commandArray = new String[3]
        commandArray[0] = "cmd.exe"
        commandArray[1] = "/c " // The trailing space is necessary
        commandArray[2] = command
        return commandArray
    }

    def m(message) {
        javax.swing.JOptionPane.showMessageDialog(null, message); 
    }

    def openDir(path) {
        // Convert the path
            path = path.replaceAll('file:/+', ' ')
            path = path.replace('/', '\\\\')
            path = path.replace('%20', ' ')

        // Get the directory only (there seems to be no method to return the directory directly)
            file = new File(path)
            directory = file.getPath().replace(file.getName(), '')
            //ui.informationMessage(directory + '') // + '' to convert numeric types to string
            //m(directory)
            //println directory
            //javax.swing.JOptionPane.showMessageDialog(null, directory); 

        //m(directory)
        command = 'explorer.exe "' + directory + '"'
        executeOnShell(command)
    }

// If there is a link to a file
    if (link.text != null)
        openDir(node.link.text)

// Open also link to embedded image if any
    if (externalObject.uri != null)
        openDir(externalObject.uri)
