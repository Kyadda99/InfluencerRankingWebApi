package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

case class Influencer(id: Int, name: String, followers: Int, location: Option[String], categories: Option[String], size: Option[String])

class Influencers(tag: Tag) extends Table[Influencer](tag, "INFLUENCERS") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def followers = column[Int]("followers")
  def location = column[Option[String]]("location")
  def categories = column[Option[String]]("categories")
  def size = column[Option[String]]("size")

  def * = (id, name, followers, location, categories, size) <> (Influencer.tupled, Influencer.unapply)

}

case class InfluencerWithMetrics(influencer: Influencer, averageLikes: Double, averageShares: Double, averageComments: Double)
case class InfluencerWithEngagment(influencer: Influencer, averageEngagment: Double)
case class InfluencerWithFullData(influencer: Influencer,campaignsCount: Int,averageLikes: Int,averageShares: Int,averageComments: Int,campaignEngagement: Double)