package com.example.model.netWork

import javax.xml.parsers.SAXParserFactory

object CreateXmlReader {
    val xmlReader = SAXParserFactory
        .newInstance()
        .newSAXParser()
        .xmlReader
}