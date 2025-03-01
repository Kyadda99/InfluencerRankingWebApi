package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

case class Category(name: String, influencerId: Int)

class Categories(tag: Tag) extends Table[Category](tag, "CATEGORY") {
  def name = column[String]("name")
  def influencerId = column[Int]("influencer_id")

  def * = (name, influencerId) <> (Category.tupled, Category.unapply)

  // Foreign key to Influencers table
  def influencerFk = foreignKey("fk_influencer_category", influencerId, TableQuery[Influencers])(_.id, onDelete = ForeignKeyAction.Cascade)
}
