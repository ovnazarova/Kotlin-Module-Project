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
                        var name: String?
                        do {
                            println("Введите название архива")
                            name = scanner.nextLine()
                        } while (name.isNullOrEmpty())
                        archiveList.add(Archive(name, mutableListOf()))
                        menuState = MenuState.ARCHIVE
                        continue
                    }

                    else -> {
                        var name : String?
                        var body : String?
                        do {
                            println("Введите имя записки")
                            name = scanner.nextLine()
                        } while (name.isNullOrEmpty())
                        do {
                            println("Введите текст записки")
                            body =scanner.nextLine()
                        } while (body.isNullOrEmpty())
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
                selectedIndex < menu.menuItems.lastIndex && selectedIndex > 0 -> {
                    when (menuState) {
                        MenuState.ARCHIVE -> {
                            selectedArchiveIndex = selectedIndex - 1
                            menuState = MenuState.NOTES
                        }

                        MenuState.NOTES -> {
                            selectedNoteIndex = selectedIndex - 1
                            menuState = MenuState.VIEW
                        }

                        else -> {/*do nothing*/}
                    }
                }

                selectedIndex == menu.menuItems.lastIndex -> {
                    when(menuState) {
                        MenuState.NOTES -> menuState = MenuState.ARCHIVE
                        else -> return
                    }
                }
                selectedIndex > menu.menuItems.lastIndex || selectedIndex < 0 -> {println("Введено неверное числовое значение. Выберете один из предложенных пунктов.")}
            }
        } catch (e : NumberFormatException) {println("Введено нечисловое или пустое значение. Введите число.")}

    }
}