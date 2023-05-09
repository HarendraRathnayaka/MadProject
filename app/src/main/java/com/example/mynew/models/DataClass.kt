package com.example.mynew.models

class DataClass {
    var dataTitle: String? = null
    var dataDesc: String? = null
    var dataPriority: String? = null
    var dataImage: String? = null
    var dataKey: String? = null

    constructor(dataTitle: String?, dataDesc: String?, dataPriority: String?, dataImage: String?, dataKey: String? = null){
        this.dataTitle = dataTitle
        this.dataDesc = dataDesc
        this.dataPriority = dataPriority
        this.dataImage = dataImage
        this.dataKey = dataKey
    }

    constructor()
    {}
}