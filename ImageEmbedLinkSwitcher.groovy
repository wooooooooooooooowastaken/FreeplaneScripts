// This script simply do a switch between an image that is embedded and a image as a link.
// If the image is a link, it will become an image embedded in the map with no link.
// If the image is embedded, it will become a link to an image.

if (link.text == null && externalObject.uri != null) {
    link.text = externalObject.uri
    externalObject.uri = null
}
else if (externalObject.uri == null && link != null) {
    externalObject.uri = link.text
    link.text = null
}