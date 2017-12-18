// Opens embedded images in map or links to images in XnView

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

def xnViewPath = 'D:\\Projects\\Tools\\XnView\\xnview.exe'

// ====================================================================================================
// = Main
// ==================================================================================================== 
    def imagePath = ''

    if (externalObject.uri != null)
        imagePath = externalObject.uri.toString()
    else if (link.text != null)  
        imagePath = link.text

    if (imagePath != '') {
        imagePath = imagePath.replace('file:/', '')
        if (os == 'windows')
            imagePath = imagePath.replace('/', '\\')
        imagePath = imagePath.replace('%20', ' ')
        cmd = xnViewPath + ' "' + imagePath + '"'
        cmd.execute()
    }
