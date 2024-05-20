package com.example.assignment.Data

import com.example.assignment.Data.model.LoginDetail
import com.example.assignment.Data.model.User
object  DummyData{
    val users =  listOf<User>(
        User(userId="Fininfocom", name = "Venu", place="Nellore", age =26),
        User(userId="DramuKumar", name = "Ramu", place="Vellore", age =12) ,
        User(userId="Sreenivasu", name = "Sreenu", place="Ongole", age =56),
        User(userId="SpremKumar", name = "Prem", place="Guntur", age =36),
        User(userId="DshivKumar", name = "Shiva", place="Nellore", age =26),
        User(userId="Venkateswar", name = "Venkateswarlu", place="Nalgonda", age = 46) ,
        User(userId="DevaraKondaa", name = "Vijay", place="Warangal", age =33),
        User(userId="AbcSukumar", name = "Sukumar", place="Eluru", age =39),
        User(userId="Hyderabaad", name = "Vineeth", place="Hyderabad", age =32),
        User(userId="AnjaniPutr", name = "Anjani", place="Nellore", age =12) ,
        User(userId="ParadesiPr", name = "Pradeep", place="Chennai", age =56),
        User(userId="ChirayuGupt", name = "Chirayu", place="Mumbai", age =10),
    )

    val loginDetails = listOf<LoginDetail>(
        LoginDetail("Fininfocom","Fin@123"),
        LoginDetail("DRamuKumar","Fin@123"),
        LoginDetail("Sreenivasu","Fin@123"),
        LoginDetail("SPremKumar","Fin@123"),
        LoginDetail("DShivKumar","Fin@123"),
        LoginDetail("Venkateswar","Fin@123"),
        LoginDetail("DevaraKondaa","Fin@123"),
        LoginDetail("ABCSukumar","Fin@123"),
        LoginDetail("Hyderabaad","Fin@123"),
        LoginDetail("AnjaniPutr","Fin@123"),
        LoginDetail("ChirayuGupt","Fin@123"),
        LoginDetail("ParadesiPr","Fin@123")

    )
}
