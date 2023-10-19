import java.util.Scanner

fun main() {
    val archiveList = mutableListOf<Archive>()
    val scanner = Scanner(System.`in`)
    val menu = Menu()
    var menuState = MenuState.ARCHIVE
    var selectedArchiveIndex = -1
    var selectedNoteIndex = -1
    while (true) {
        when (menuState) {
            MenuState.ARCHIVE -> menu.render(archiveList, "Архив")
            MenuState.NOTES -> menu.render(archiveList[selectedArchiveIndex].notes, "Записку")
            MenuState.VIEW -> {
                if (selectedNoteIndex >= 0) {
                    println(archiveList[selectedArchiveIndex].notes[selectedNoteIndex])
                }
                scanner.nextLine()
                menuState = MenuState.NOTES
                continue
            }
            MenuState.CREATE -> {
                when {
                    selectedArchiveIndex < 0 -> {
                        println("Введите название архива")
                        val name : String? = scanner.nextLine()
                        archiveList.add(Archive(name, mutableListOf()))
                        menuState = MenuState.ARCHIVE
                        continue
                    }

                    else -> {
                        println("Введите имя записки")
                        val name : String? = scanner.nextLine()
                        println("Введите текст записки")
                        val body : String? =scanner.nextLine()
                        archiveList[selectedArchiveIndex].notes.add(Note(name, body))
                        menuState = MenuState.NOTES
                        continue
                    }
                }
            }
        }

        try {
            val selectedIndex = scanner.nextLine().toInt()
            when {
                selectedIndex == 0 -> menuState = MenuState.CREATE
                selectedIndex < menu.menuItems.lastIndex-> {
                    when (menuState) {
                        MenuState.ARCHIVE -> {
                            selectedArchiveIndex = selectedIndex - 1
                            menuState = MenuState.NOTES
                        }

                        MenuState.NOTES -> {
                            selectedNoteIndex = selectedIndex - 1
                            menuState = MenuState.VIEW
                        }

                        else -> {/* do nothing */}
                    }
                }

                selectedIndex == menu.menuItems.lastIndex -> {
                    when(menuState) {
                        MenuState.NOTES -> menuState = MenuState.ARCHIVE
                        else -> return
                    }
                }
                selectedIndex > menu.menuItems.lastIndex -> {println("Введено неверное числовое значение. Выберете один из предложенных пунктов.")}
            }
        } catch (e : NumberFormatException) {println("Введено нечисловое значение. Введите число.")}

    }
}