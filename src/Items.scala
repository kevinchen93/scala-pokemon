/**
 * Created by kevinchen on 5/23/15.
 */

sealed trait Item

case class UsableItem(name: String,
                      description: String,
                      effect: Int,
                      itemType: Int,
                      usableInBattle: Boolean) extends Item

object Items {
  val Potion = UsableItem("Potion", "Heal a Pokemon for 20HP.",                   1, 1, true)
  val Pokeball = UsableItem("PokeBall", "Throw a Wild Pokemon to capture it!",    2, 2, true)
  val Berry = UsableItem("Berry", "A Pokemon may Hold this item. Restores 10HP.", 1, 1, true)
  val Bicycle = UsableItem("Bicycle", "A super-fast Bike that you can ride!",     3, 3, false)
}