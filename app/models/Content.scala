package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

case class Content(id: Int, likes: Int, shares: Int, followersGained: Int, publicationDate: String, influencerId: Int, comments: Int)

class Contents(tag: Tag) extends Table[Content](tag, "CONTENT") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def likes = column[Int]("likes")
  def shares = column[Int]("shares")
  def followersGained = column[Int]("followers_gained")
  def publicationDate = column[String]("publication_date")
  def influencerId = column[Int]("influencer_id")
  def comments = column[Int]("comments")

  def * = (id, likes, shares, followersGained, publicationDate, influencerId, comments) <> (Content.tupled, Content.unapply)

  // Foreign key to Influencers table
  def influencerFk = foreignKey("fk_content_influencer", influencerId, TableQuery[Influencers])(_.id, onDelete = ForeignKeyAction.Cascade)
}
