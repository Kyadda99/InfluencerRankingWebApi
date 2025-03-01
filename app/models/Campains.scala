package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

case class Campaign(id: Int, influencerId: Int, amountOfOpens: Int, amountOfInteractions: Int)

class Campaigns(tag: Tag) extends Table[Campaign](tag, "CAMPAIGN") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def influencerId = column[Int]("influencer_id")
  def amountOfOpens = column[Int]("amount_of_opens")
  def amountOfInteractions = column[Int]("amount_of_interactions")

  def * = (id, influencerId, amountOfOpens, amountOfInteractions) <> (Campaign.tupled, Campaign.unapply)

  // Foreign key to Influencers table
  def influencerFk = foreignKey("fk_influencer", influencerId, TableQuery[Influencers])(_.id, onDelete = ForeignKeyAction.Cascade)
}
