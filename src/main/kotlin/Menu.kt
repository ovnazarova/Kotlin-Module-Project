class Menu {
    private val menuFormatter = "%d. %s"
    val menuItems = mutableListOf<ArchiveMenuItem>()

    fun render(list: List<File>, fileType: String) {
        menuItems.clear()
        menuItems.add(ArchiveMenuItem("Создать $fileType"))
        menuItems.addAll(list.map { ArchiveMenuItem(it.name) })
        menuItems.add(ArchiveMenuItem("Выход"))

        menuItems.forEachIndexed { index, archiveMenuItem ->
            println(String.format(menuFormatter, index, archiveMenuItem.title))
        }
    }
}