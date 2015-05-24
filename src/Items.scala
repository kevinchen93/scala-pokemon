/**
 * Created by kevinchen on 5/23/15.
 */
class Items(itemNumber: Int) {

  def name = itemNumber match {
    case 0 => ""
    case 1 => "Potion"
    case 2 => "PokeBall"
    case 3 => "Berry"
    case 4 => "Bicycle"
  }

  def description = itemNumber match {
    case 0 => ""
    case 1 => "Heal a Pokemon for 20HP."
    case 2 => "Throw a Wild Pokemon to capture it!"
    case 3 => "A Pokemon may Hold this item. Restores 10HP."
    case 4 => "A super-fast Bike that you can ride!"
  }

  def effect = itemNumber match {
    case 0 => 0
    case 1 => 1
    case 2 => 2
    case 3 => 1
    case 4 => 3
  }

  def itemType = itemNumber match {
    case 0 => 0
    case 1 => 1
    case 2 => 2
    case 3 => 1
    case 4 => 3
  }

  def isUsableInBattle = itemNumber match {
    case 0 => false
    case 1 => true
    case 2 => true
    case 3 => true
    case 4 => false
  }

}
