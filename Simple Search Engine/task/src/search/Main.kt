package search

import java.io.File

class SearchSystem {
    private lateinit var text: Array<String>
    private val findList = mutableListOf<String>()

    private fun getString(vText: String): String {
        println(vText)
        return readLine()!!
    }

    fun initSystem(file: String) {
        text = File(file).readLines().toTypedArray()
    }

    fun getMenu() {
        var doIt = true
        while (doIt) {
            when (getString("=== Menu ===\n" +
                                  "1. Find a person\n" +
                                  "2. Print all people\n" +
                                  "0. Exit").toInt()) {
                1 -> find()
                2 -> printAll()
                0 -> doIt = false
                else -> println("Incorrect option! Try again.")
            }
        }
        println("Bye!")
    }

    private fun printAll() {
        println("=== List of people ===")
        text.forEach { println(it) }
    }

    private fun find() {
        val strategy = getString("Select a matching strategy: ALL, ANY, NONE")
        val find = getString("Enter a name or email to search all suitable people.")
        val findArr = find.split(" ")
        findList.clear()
        var cont: Boolean
        text.forEach {
            when (strategy) {
                "ALL" ->  it.split(" ").forEach { its ->
                            if (its.equals(find, true)) {
                                findList.add(it)
                            }
                        }
                "ANY" -> {
                    cont = false
                    for (f in findArr) {
                        if (it.contains(f, true)) {
                            findList.add(it)
                            break
                        }
                    }
                }
                "NONE" -> {
                    cont = false
                    for (f in findArr)
                        if (it.contains(f, true)) {
                            cont = true
                            break
                        }
                    if (!cont) findList.add(it)
                }
            }
        }

        val s = findList.size
        if (s > 0) {
            println("$s person${if (s > 1) "s" else ""} found:")
            for (fl in findList) println(fl)
        } else
            println("No matching people found.")
    }
}


fun main(args: Array<String>) {
    val ss = SearchSystem()
    if (args.size > 1 && args[0] == "--data")
        ss.initSystem(args[1])
    ss.getMenu()
}
