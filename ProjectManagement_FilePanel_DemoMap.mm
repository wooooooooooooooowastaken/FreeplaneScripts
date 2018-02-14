<map version="freeplane 1.6.0">
<!--To view this file, download free mind mapping software Freeplane from http://freeplane.sourceforge.net -->
<attribute_registry SHOW_ATTRIBUTES="hide"/>
<node TEXT="FilePanel&#xa;Demo Map" FOLDED="false" ID="ID_1776567510" CREATED="1518640859958" MODIFIED="1518650900234" STYLE="oval">
<font SIZE="18"/>
<hook NAME="MapStyle">
    <properties show_icon_for_attributes="true" fit_to_viewport="false" show_note_icons="true" edgeColorConfiguration="#808080ff,#ff0000ff,#0000ffff,#00ff00ff,#ff00ffff,#00ffffff,#7c0000ff,#00007cff,#007c00ff,#7c007cff,#007c7cff,#7c7c00ff"/>

<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node" STYLE="oval" UNIFORM_SHAPE="true" VGAP_QUANTITY="24.0 pt">
<font SIZE="24"/>
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right" STYLE="bubble">
<stylenode LOCALIZED_TEXT="default" ICON_SIZE="12.0 pt" COLOR="#000000" STYLE="fork">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details"/>
<stylenode LOCALIZED_TEXT="defaultstyle.attributes">
<font SIZE="9"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.note" COLOR="#000000" BACKGROUND_COLOR="#ffffff" TEXT_ALIGN="LEFT"/>
<stylenode LOCALIZED_TEXT="defaultstyle.floating">
<edge STYLE="hide_edge"/>
<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right" STYLE="bubble">
<stylenode LOCALIZED_TEXT="styles.topic" COLOR="#18898b" STYLE="fork">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subtopic" COLOR="#cc3300" STYLE="fork">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subsubtopic" COLOR="#669900">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important">
<icon BUILTIN="yes"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right" STYLE="bubble">
<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" COLOR="#000000" STYLE="oval" SHAPE_HORIZONTAL_MARGIN="10.0 pt" SHAPE_VERTICAL_MARGIN="10.0 pt">
<font SIZE="18"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,1" COLOR="#0033ff">
<font SIZE="16"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,2" COLOR="#00b439">
<font SIZE="14"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,3" COLOR="#990000">
<font SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,4" COLOR="#111111">
<font SIZE="10"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,5"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,6"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,7"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,8"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,9"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,10"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,11"/>
</stylenode>
</stylenode>
</map_styles>
</hook>
<hook NAME="AutomaticEdgeColor" COUNTER="4" RULE="ON_BRANCH_CREATION"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Demo map to show the FileManagement_FilePanel.groovy script.
    </p>
    <p>
      Put the map in Outline view for better viewing.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Demo map to show the <b><font color="#ff0000">FileManagement_FilePanel.groovy</font></b>&#160;script.
    </p>
    <p>
      
    </p>
    <p>
      Put the map in <b>Outline view</b>&#160;for better viewing (View &gt; View settings &gt; Outline view).
    </p>
    <p>
      
    </p>
    <p>
      You may choose to <b>hide the attributes</b>&#160;also if you don't want to see the DateModified and the Size of the files and folders attributes (View &gt; Node attributes &gt; Hide all attributes).
    </p>
    <p>
      
    </p>
    <p>
      Version: 2018-02-15_01.28.19
    </p>
  </body>
</html>

</richcontent>
<node TEXT="Example #1&#xa;List of paths" POSITION="right" ID="ID_1152312626" CREATED="1518640958072" MODIFIED="1518643004823">
<edge COLOR="#ff0000"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p style="margin-top: 0">
      Run the script on this node,
    </p>
    <p style="margin-top: 0">
      the <b>paths</b>&#160;should <b>become links</b>
    </p>
    <p style="margin-top: 0">
      with proper icons and the files not
    </p>
    <p style="margin-top: 0">
      found will have a red X. This node will basically become a project node as in Example #2 below.
    </p>
  </body>
</html>
</richcontent>
<font BOLD="false"/>
<node TEXT="&quot;C:\Windows\win.ini&quot;" ID="ID_49796410" CREATED="1518641830222" MODIFIED="1518641830222"/>
<node TEXT="C:\Windows\write.exe" ID="ID_263448071" CREATED="1518641830222" MODIFIED="1518641830222"/>
<node TEXT="&quot;C:\Program Files\Windows NT\Accessories\wordpad.exe&quot;" ID="ID_406456775" CREATED="1518641830223" MODIFIED="1518641830223"/>
<node TEXT="C:\FileThatDoesntExist.txt" ID="ID_392382234" CREATED="1518641838266" MODIFIED="1518641855628"/>
<node TEXT="file:/c:/Windows" ID="ID_1195231798" CREATED="1518642290251" MODIFIED="1518642299765"/>
</node>
<node TEXT="Example #2&#xa;Project node (5)" POSITION="right" ID="ID_1815462215" CREATED="1518640958072" MODIFIED="1518642989962" HGAP_QUANTITY="31.99999946355821 pt" VSHIFT_QUANTITY="71.99999785423284 pt">
<icon BUILTIN="mindmap"/>
<edge COLOR="#ff0000"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p style="margin-top: 0">
      Run the script on this node,
    </p>
    <p style="margin-top: 0">
      a <b>dialog</b>&#160;menu should open to select a <b>sorting</b>&#160;order.
    </p>
    <p style="margin-top: 0">
      If some files are remove from the file system, they will
    </p>
    <p style="margin-top: 0">
      get a red X now, and if some paths are added, they will
    </p>
    <p style="margin-top: 0">
      become links with their icons and DateModified and Size
    </p>
    <p style="margin-top: 0">
      attributes.
    </p>
  </body>
</html>
</richcontent>
<font BOLD="true"/>
<cloud COLOR="#f0f0f0" SHAPE="RECT"/>
<node TEXT="&quot;C:\Windows\win.ini&quot;" ID="ID_449991648" CREATED="1518641830222" MODIFIED="1518650839615" LINK="file:/C:/Windows/win.ini">
<icon BUILTIN="list"/>
<icon BUILTIN="full-9"/>
<attribute NAME="DateModified" VALUE="1/19/18 9:01 AM" OBJECT="org.freeplane.features.format.FormattedDate|2018-01-19T09:01+0200|datetime"/>
<attribute NAME="Size" VALUE="478" OBJECT="org.freeplane.features.format.FormattedNumber|478"/>
</node>
<node TEXT="C:\Windows\write.exe" ID="ID_1931685955" CREATED="1518641830222" MODIFIED="1518642396624" LINK="file:/C:/Windows/write.exe">
<icon BUILTIN="executable"/>
<attribute NAME="DateModified" VALUE="7/14/09 4:39 AM" OBJECT="org.freeplane.features.format.FormattedDate|2009-07-14T04:39+0300|datetime"/>
<attribute NAME="Size" VALUE="10240" OBJECT="org.freeplane.features.format.FormattedNumber|10240"/>
</node>
<node TEXT="&quot;C:\Program Files\Windows NT\Accessories\wordpad.exe&quot;" ID="ID_1253049571" CREATED="1518641830223" MODIFIED="1518650845427" LINK="file:/C:/Program%20Files/Windows%20NT/Accessories/wordpad.exe">
<icon BUILTIN="executable"/>
<icon BUILTIN="full-3"/>
<attribute NAME="DateModified" VALUE="8/11/17 9:09 AM" OBJECT="org.freeplane.features.format.FormattedDate|2017-08-11T09:09+0300|datetime"/>
<attribute NAME="Size" VALUE="4582912" OBJECT="org.freeplane.features.format.FormattedNumber|4582912"/>
</node>
<node TEXT="C:\FileThatDoesntExist.txt" ID="ID_742774805" CREATED="1518641838266" MODIFIED="1518642396629">
<icon BUILTIN="button_cancel"/>
</node>
<node TEXT="file:/c:/Windows" ID="ID_69037529" CREATED="1518642290251" MODIFIED="1518650851880" LINK="file:/c:/Windows">
<icon BUILTIN="folder"/>
<icon BUILTIN="full-1"/>
<attribute NAME="DateModified" VALUE="2/12/18 12:03 PM" OBJECT="org.freeplane.features.format.FormattedDate|2018-02-12T12:03+0200|datetime"/>
</node>
<node TEXT="file:/c:/Windows/System32" ID="ID_1151336990" CREATED="1518642290251" MODIFIED="1518650857727">
<icon BUILTIN="full-7"/>
</node>
</node>
<node TEXT="Example #3&#xa;Get folder content" POSITION="right" ID="ID_186699498" CREATED="1518640958072" MODIFIED="1518643452631" LINK="file:/C:/Windows/Temp" HGAP_QUANTITY="37.249999307096026 pt" VSHIFT_QUANTITY="52.499998435378124 pt">
<icon BUILTIN="PalmIcons/dFileMan/Trees/Blue-tree"/>
<edge COLOR="#ff0000"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p style="margin-top: 0">
      Run the script on this node,
    </p>
    <p style="margin-top: 0">
      because this node has the <b>'blue-tree' icon</b>&#160;and contains
    </p>
    <p style="margin-top: 0">
      a link to a folder, a <b>dialog</b>&#160;will open have the user
    </p>
    <p style="margin-top: 0">
      enter a <b>regular expression</b>&#160;to <b>select files</b>&#160;from this folder
    </p>
    <p style="margin-top: 0">
      that will be added to here. Then the node will
    </p>
    <p style="margin-top: 0">
      become a project node as in Example #2 and
    </p>
    <p style="margin-top: 0">
      the 'blue-tree' icon will be removed.
    </p>
    <p style="margin-top: 0">
      The regular expression will be put as an attribute
    </p>
    <p style="margin-top: 0">
      of the node so that the user can see what was selected,
    </p>
    <p style="margin-top: 0">
      and the user can modify that regular expression and put back
    </p>
    <p style="margin-top: 0">
      the 'blue-tree' icon and run the script again and new files will
    </p>
    <p style="margin-top: 0">
      be selected.
    </p>
    <p style="margin-top: 0">
      
    </p>
    <p style="margin-top: 0">
      For example here, try enter this regex in the dialog:&#160;&#160;&#160;&#160;.*\.(txt|log)
    </p>
  </body>
</html>
</richcontent>
<font BOLD="true"/>
</node>
</node>
</map>
